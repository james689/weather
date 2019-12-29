package core;

import core.weatherapi.APIRequest;
import core.weatherdata.WeatherData;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

// The Model class in the MVC (model-view-controller) pattern that is used
// in this application. 
// Note: The Model is responsible for handling all of the application logic, basically
// the "guts" of the application. The Model does not know about the View or Controller
// directly, it only knows about Observers. Hence, the model is decoupled from the View
// and Controller. The Model can still update the View indirectly, since the View
// registers itself as an observer of the model. When the model's state changes,
// it notifies all of its observers (of which the View is one of them).
public class WeatherModel {

    private String location; // the location for the weather e.g. "Paris,fr"
    private WeatherData weatherData; // the weather data for the current location
    private List<WeatherObserver> weatherObservers; // the observers to be notified when the
    // model's state changes
    private List<City> validCityLocations; // list of valid cities a client can use for the location

    public static class City {
        public long id; // id of city
        public String name; // name of city
        public String countryCode; // code of country
        
        public String toString() {
            return name + "," + countryCode;
        }
    }
    
    public WeatherModel(List<WeatherModel.City> validCityLocations) {
        this.validCityLocations = validCityLocations;
	location = "";
        weatherData = null;
        weatherObservers = new ArrayList<WeatherObserver>();
    }
    
    public void setLocation(String location) throws IOException,
            ParserConfigurationException, SAXException {
        
        WeatherData newWeatherData = APIRequest.getWeatherData(location); // throws an exception if the API request fails
        // In such a case the location and weather will not be changed.
       
        this.location = location;
        weatherData = newWeatherData;
        notifyWeatherObservers();
        // start timer for when weather data should next be updated for the current location
        // (to be implemented)
    }

    public WeatherData getWeatherData() {
        return weatherData;
    }
    
    // allows the client (such as the View) to get access to a list of all 
    // the city locations it can get weather for
    public List<WeatherModel.City> getValidCityLocations() {
        return validCityLocations;
    }

    // Observer methods
    public void registerObserver(WeatherObserver o) {
        weatherObservers.add(o);
    }

    public void removeObserver(WeatherObserver o) {
        int i = weatherObservers.indexOf(o);
        if (i >= 0) {
            weatherObservers.remove(i);
        }
    }

    public void notifyWeatherObservers() {
        for (WeatherObserver weatherObserver : weatherObservers) {
            weatherObserver.updateWeather();
        }
    }
}
