package gui;

import core.WeatherModel;
import core.WeatherObserver;
import core.weatherdata.WeatherData;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

// The View class in the MVC (model-view-controller) pattern that is used
// in this application. 
// Note: The View never alters the model's state directly. For example, the View will 
// never call setLocation() on the model. Altering the model's state is the job
// of the controller. However, the view does access/use the model's state.
// The View registers itself as an observer of the model so that when the model's
// state changes, the View is notified of the change by the model. The View then requests the
// model's updated state and displays it.
public class WeatherView implements WeatherObserver {

    private WeatherModel model;
    private ControllerInterface controller;

    // Swing components
    private JFrame viewFrame;
    private JPanel viewPanel;
    private JComboBox searchBox;
    private JButton submitLocationButton;
    private JLabel cityIDLabel, cityNameLabel, longitudeLabel, latitudeLabel, 
            countryLabel, sunriseTimeLabel, sunsetTimeLabel, temperatureLabel,
            temperatureFeelsLikeLabel, humidityLabel, pressureLabel, windSpeedLabel, 
            windSpeedNameLabel, windSpeedDirectionLabel, weatherConditionLabel,
            lastUpdateTimeLabel;

    public WeatherView(ControllerInterface controller, WeatherModel model) {
        this.controller = controller;
        this.model = model;
        model.registerObserver(this); // The View registers itself as an observer of the
        // model so that it can update the display when the model's state changes
    }

    public void createView() {
        viewPanel = new JPanel();
        viewPanel.setPreferredSize(new Dimension(300, 300));
        viewPanel.setLayout(new GridLayout(18, 1));

        //create the combobox
        searchBox = new JComboBox();
        //we need it to be editable!!
        searchBox.setEditable(true);
        //create the search box model, which is populated with data obtained from the
        // weatherModel.
        SearchBoxModel sbm = new SearchBoxModel(searchBox, model.getValidCityLocations());
        //set the model on the combobox
        searchBox.setModel(sbm);
        //set the model as the item listener also
        searchBox.addItemListener(sbm);
        searchBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    WeatherModel.City selectedItem = (WeatherModel.City) searchBox.getSelectedItem();
                    //System.out.println("combo box selection made: user selected: " + selectedItem);
                }
            }
        });
        
        submitLocationButton = new JButton("Submit");
        submitLocationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //System.out.println("submit button pressed");
                WeatherModel.City selectedItem = (WeatherModel.City) searchBox.getSelectedItem();
                if (selectedItem != null) {
                    controller.setLocation(selectedItem.name + "," + selectedItem.countryCode);
                }
            }
        });

        createLabels();
        viewPanel.add(searchBox);
        viewPanel.add(submitLocationButton);
        addLabelsToViewPanel();

        viewFrame = new JFrame("View");
        viewFrame.setContentPane(viewPanel);
        viewFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        viewFrame.pack();
        viewFrame.setVisible(true);
    }
    
    private void createLabels() {
        cityIDLabel = new JLabel("City ID: ");
        cityNameLabel = new JLabel("City Name: ");
        longitudeLabel = new JLabel("Longitude: ");
        latitudeLabel = new JLabel("Latitude: ");
        countryLabel = new JLabel("Country: ");
        sunriseTimeLabel = new JLabel("Sunrise Time: ");
        sunsetTimeLabel = new JLabel("Sunset Time: ");
        temperatureLabel = new JLabel("Temperature (celsius): ");
        temperatureFeelsLikeLabel = new JLabel("Temperature feels like (celsisu): ");
        humidityLabel = new JLabel("Humidity (%): ");
        pressureLabel = new JLabel("Pressure (hpa): ");
        windSpeedLabel = new JLabel("Wind Speed (m/s): ");
        windSpeedNameLabel = new JLabel("Wind speed name: ");
        windSpeedDirectionLabel = new JLabel("Wind speed direction: ");
        weatherConditionLabel = new JLabel("Current conditions: ");
        lastUpdateTimeLabel = new JLabel("Last update time: ");
    }
    
    private void addLabelsToViewPanel() {
        viewPanel.add(cityIDLabel); 
        viewPanel.add(cityNameLabel); 
        viewPanel.add(longitudeLabel);
        viewPanel.add(latitudeLabel);
        viewPanel.add(countryLabel); 
        viewPanel.add(sunriseTimeLabel); 
        viewPanel.add(sunsetTimeLabel); 
        viewPanel.add(temperatureLabel); 
        viewPanel.add(temperatureFeelsLikeLabel); 
        viewPanel.add(humidityLabel); 
        viewPanel.add(pressureLabel); 
        viewPanel.add(windSpeedLabel); 
        viewPanel.add(windSpeedNameLabel); 
        viewPanel.add(windSpeedDirectionLabel); 
        viewPanel.add(weatherConditionLabel); 
        viewPanel.add(lastUpdateTimeLabel); 
    }

    // this method is called by the model when the model's state changes
    public void updateWeather() {
        System.out.println("model state has changed");
        WeatherData weatherData = model.getWeatherData(); // get the model's updated state
        displayWeatherData(weatherData); // display the model's state
    }

    private void displayWeatherData(WeatherData weatherData) {
        cityIDLabel.setText("City ID: " + weatherData.getCityID());
        cityNameLabel.setText("City Name: " + weatherData.getCityName());
        longitudeLabel.setText("Longitude: " + weatherData.getLongitude());
        latitudeLabel.setText("Latitude: " + weatherData.getLatitude());
        countryLabel.setText("Country: " + weatherData.getCountry());
        sunriseTimeLabel.setText("Sunrise Time: " + weatherData.getSunriseTime());
        sunsetTimeLabel.setText("Sunset Time: " + weatherData.getSunsetTime());
        temperatureLabel.setText("Temperature (celsius): " + weatherData.getTemperature());
        temperatureFeelsLikeLabel.setText("Temperature feels like (celsius): " + weatherData.getTemperatureFeelsLike());
        humidityLabel.setText("Humidity (%): " + weatherData.getHumidity());
        pressureLabel.setText("Pressure (hpa): " + weatherData.getPressure());
        windSpeedLabel.setText("Wind Speed (m/s): " + weatherData.getWindSpeed());
        windSpeedNameLabel.setText("Wind speed name: " + weatherData.getWindSpeedName());
        windSpeedDirectionLabel.setText("Wind speed direction: " + weatherData.getWindSpeedDirection());
        weatherConditionLabel.setText("Current conditions: " + weatherData.getWeatherConditionName());
        lastUpdateTimeLabel.setText("Last update time: " + weatherData.getLastUpdateTime());
    }

    // called by the controller to tell the GUI to display an error message
    // to the user
    public void showErrorMessage(String message) {
        // show alert dialog box to alert user of invalid location
        JOptionPane.showMessageDialog(null, message);
    }
}
