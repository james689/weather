package core;

// interface for WeatherObservers that want to register with the WeatherModel
// to be notified when the model's state changes.
public interface WeatherObserver {
    public void updateWeather();
}
