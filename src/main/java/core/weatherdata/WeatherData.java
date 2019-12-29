package core.weatherdata;

import java.time.LocalDateTime;

public interface WeatherData {

    public String getCityID();
    public String getCityName();
    public double getLongitude();
    public double getLatitude();
    public String getCountry();
    public LocalDateTime getSunriseTime();
    public LocalDateTime getSunsetTime();
    public double getTemperature(); // unit = degrees celsius
    public double getTemperatureFeelsLike(); // unit = degrees celsius
    public int getHumidity(); // unit = %
    public int getPressure(); // unit = hpa
    public double getWindSpeed(); // unit = m/s (metres per second
    public String getWindSpeedName();
    public String getWindSpeedDirection();
    public String getWeatherConditionName();
    public String getWeatherConditionIcon();
    public LocalDateTime getLastUpdateTime();
}
