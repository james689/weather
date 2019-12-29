
package gui;

import core.WeatherModel;

// The Controller class in the MVC (model-view-controller) pattern that is used
// in this application. 
// Note: The Controller is responsible for taking user actions that happen in the view,
// such as the user setting a location, and altering the model accordingly. 
// As well as altering the model's state, the controller may need to make changes
// to the view, such as enabling/disabling controls. The controller is basically
// the "logic" or "strategy" for the View. The View is dumb, it just displays
// data and passes all of its user events onto the controller to handle.
// In this application, the controller also creates the View object it will be
// the controller for.
public class WeatherController implements ControllerInterface {
    private WeatherModel model;
    private WeatherView view;
    
    public WeatherController(WeatherModel model) {
        this.model = model;
        view = new WeatherView(this, model);
        view.createView();
    }
    
    public void setLocation(String location) {
        // trying to set the model's location can result in an exception if
        // the model cannot get weather data for that location
        try {
            model.setLocation(location);
        } catch (Exception e) {
            // couldn't set the model's location so display message in View
            view.showErrorMessage("could not get weather data for location " + location);
        }
    }
}
