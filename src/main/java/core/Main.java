package core;

import gui.ControllerInterface;
import gui.WeatherController;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {

    public static void main(String[] args) {
        List<WeatherModel.City> validCityLocations = getValidCityLocations();
        WeatherModel model = new WeatherModel(validCityLocations);
        ControllerInterface controller = new WeatherController(model); // The controller will create the View
    }
    
    // Note: Cannot get the getRsourceAsStream() version of this method to work for
    // some reason. Always causes a NullPointerException 
    /*private static List<WeatherModel.City> getValidCityLocations() {
        JSONParser parser = new JSONParser();
        List<WeatherModel.City> cities = new ArrayList<WeatherModel.City>();
        
        InputStream is = Main.class.getResourceAsStream("src/main/resources/city.list.json");
        try ( BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            JSONArray jsonArray = (JSONArray) parser.parse(reader);
            for (int i = 0; i < jsonArray.size(); i++) {
                WeatherModel.City city = new WeatherModel.City();
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                city.id = (long) jsonObject.get("id");
                city.name = (String) jsonObject.get("name");
                city.countryCode = (String) jsonObject.get("country");
                cities.add(city);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
           
        return cities;
    }*/
    
    private static List<WeatherModel.City> getValidCityLocations() {
        JSONParser parser = new JSONParser();
        List<WeatherModel.City> cities = new ArrayList<WeatherModel.City>();
        
        File file = new File("src/main/resources/city.list.json");
        try ( BufferedReader reader = new BufferedReader(new FileReader(file))) {

            JSONArray jsonArray = (JSONArray) parser.parse(reader);
            for (int i = 0; i < jsonArray.size(); i++) {
                WeatherModel.City city = new WeatherModel.City();
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                city.id = (long) jsonObject.get("id");
                city.name = (String) jsonObject.get("name");
                city.countryCode = (String) jsonObject.get("country");
                cities.add(city);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
           
        return cities;
    }
}