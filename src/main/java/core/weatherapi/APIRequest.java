package core.weatherapi;

import core.weatherdata.WeatherData;
import core.weatherdata.WeatherDataXML;
import java.net.URLConnection;
import java.net.URL;
import java.io.InputStream;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

// handles requesting data from the external weather API
public class APIRequest {

    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";
    private static final String APPID = "20089586381a3afe833ab3e6b7999ea0";
    private static final String UNITS = "metric";
    
    public static WeatherData getWeatherData(String location) throws IOException,
            ParserConfigurationException, SAXException {
        
        URL url = new URL(BASE_URL + "?" + "q=" + location + "&mode=xml" + "&units=" + UNITS + "&APPID=" + APPID);
        System.out.println(url);
        URLConnection connection = url.openConnection(); // send query to external API by opening a connection to it
        InputStream is = connection.getInputStream(); // get response from API
        WeatherData weatherData = new WeatherDataXML(is); // construct a WeatherData object from the input stream
        
        return weatherData;
    }
}
