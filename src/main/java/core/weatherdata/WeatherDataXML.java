package core.weatherdata;

import java.io.IOException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import java.io.InputStream;
import java.time.LocalDateTime;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

// A WeatherData object, based on an underlying XML data representation
public class WeatherDataXML implements WeatherData {

    private Document xmlDoc; // the underlying XML document containing the weather data
    private Element rootElement; // the root element of the XML document

    // constructs a WeatherData object from an inputstream containing XML data
    // throws an exception if the data in the input stream cannot be parsed into
    // an XML document, and hence the WeatherDataXML object cannot be constructed.
    public WeatherDataXML(InputStream is) throws ParserConfigurationException, 
            SAXException, IOException {
        DocumentBuilder docReader = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document xmlDoc = docReader.parse(is); // will throw an exception if cannot parse input
        this.xmlDoc = xmlDoc;
        this.rootElement = xmlDoc.getDocumentElement();
    }

    // helper method used to access elements from the document
    private Element getElement(String tagName) {
        NodeList nodeList = rootElement.getElementsByTagName(tagName); // returns
        // a NodeList containing all descendents of the rootElement with the given tag name. 
        Element element = (Element) nodeList.item(0); // access just the first element
        // from the list. There should only be one element anyway since each
        // element in this particular XML document has a unique tag name.
        return element;
    }

    // helper method for converting fahrenheit to celsius
    private static double fahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }

    // helper method for converting kelvin to celsius
    private static double kelvinToCelsius(double kelvin) {
        return kelvin - 273.15;
    }
    
    // helper method for converting miles per hour speed to metres per second 
    private static double mphToMs(double mph) {
        return mph / 2.237;
    }

    // location/city data
    public String getCityID() {
        return getElement("city").getAttribute("id");
    }

    public String getCityName() {
        return getElement("city").getAttribute("name");
    }

    public double getLongitude() {
        String longitude = getElement("coord").getAttribute("lon");
        return Double.parseDouble(longitude);
    }

    public double getLatitude() {
        String latitude = getElement("coord").getAttribute("lat");
        return Double.parseDouble(latitude);
    }

    public String getCountry() {
        return getElement("country").getTextContent();
    }
    
    public LocalDateTime getSunriseTime() {
        String sunriseTime = getElement("sun").getAttribute("rise");
        return LocalDateTime.parse(sunriseTime);
    }
    
    public LocalDateTime getSunsetTime() {
        String sunsetTime = getElement("sun").getAttribute("set");
        return LocalDateTime.parse(sunsetTime);
    }

    // weather data
    // returns the current temperature in degrees celsius
    public double getTemperature() {
        Element temperatureElement = getElement("temperature");
        double currentTemp = Double.parseDouble(temperatureElement.getAttribute("value"));
        String temperatureUnit = temperatureElement.getAttribute("unit");
        if (temperatureUnit.equals("celsius")) {
            currentTemp = currentTemp;
        } else if (temperatureUnit.equals("fahrenheit")) {
            // convert fahrenheit to celsius
            currentTemp = fahrenheitToCelsius(currentTemp);
        } else if (temperatureUnit.equals("kelvin")) {
            // convert kelvin to celsius
            currentTemp = kelvinToCelsius(currentTemp);
        }
        return currentTemp;
    }

    // returns the temperatue feels like in degrees celsius
    public double getTemperatureFeelsLike() {
        Element temperatureFeelsLikeElement = getElement("feels_like");
        double tempFeelsLike = Double.parseDouble(temperatureFeelsLikeElement.getAttribute("value"));
        String tempFeelsLikeUnit = temperatureFeelsLikeElement.getAttribute("unit");
        if (tempFeelsLikeUnit.equals("celsius")) {
            tempFeelsLike = tempFeelsLike;
        } else if (tempFeelsLikeUnit.equals("fahrenheit")) {
            // convert fahrenheit to celsius
            tempFeelsLike = fahrenheitToCelsius(tempFeelsLike);
        } else if (tempFeelsLikeUnit.equals("kelvin")) {
            // convert kelvin to celsius
            tempFeelsLike = kelvinToCelsius(tempFeelsLike);
        }
        return tempFeelsLike;
    }

    // returns current humidity, unit = %
    public int getHumidity() {
        String humidity = getElement("humidity").getAttribute("value");
        return Integer.parseInt(humidity);
    }

    // returns current pressure, unit = hpa
    public int getPressure() {
        String pressure = getElement("pressure").getAttribute("value");
        return Integer.parseInt(pressure);
    }

    // returns wind speed, unit = metres/second
    public double getWindSpeed() {
        Element windSpeedElement = getElement("speed");
        double windSpeed = Double.parseDouble(windSpeedElement.getAttribute("value"));
        String windSpeedUnit = windSpeedElement.getAttribute("unit");
        if (windSpeedUnit.equals("m/s")) {
            windSpeed = windSpeed;
        } else if (windSpeedUnit.equals("mph")) {
            // convert mph to m/s
            windSpeed = mphToMs(windSpeed);
        }
        return windSpeed;
    }

    public String getWindSpeedName() {
        return getElement("speed").getAttribute("name");
    }
    
    public String getWindSpeedDirection() {
        return getElement("direction").getAttribute("name");
    }

    public String getWeatherConditionName() {
        return getElement("weather").getAttribute("value");
    }

    public String getWeatherConditionIcon() {
        return getElement("weather").getAttribute("icon");
    }

    public LocalDateTime getLastUpdateTime() {
        String lastUpdateTime = getElement("lastupdate").getAttribute("value");
        return LocalDateTime.parse(lastUpdateTime);
    }
}
