package com.forecast.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Weather data object for the Forecast Application.
 * Contains a JSON mapping to generate a view representation used by a UI
 */
public class Weather {

    private static final double KELVIN_ZERO = -273.15D;
    private static final double NINE_FIFTH = 9/5D;
    private static final double FAHRENHEIT_DELTA = - 459.67D;

    private long date;
    private String cityName;
    private String countryCode;
    private String description;
    private double tempKelvin;
    private double tempCelsius;
    private double tempFahrenheit;
    private long sunrise;
    private long sunset;

    /**
     * Creates a Weather instance taking a temperature value expressed in Kelvin degrees.
     * The temperature is used to calculate Celsius and Fahrenheit values.
     *
     * @param date date at which the weather data was reported. Expressed as a Unix Epoch value
     * @param cityName the city name to which the weather data belongs
     * @param description overall weather description (i.e.: "Light rain" or "Sunny")
     * @param tempKelvin current temperature measured in Kelvin degrees
     * @param sunrise sunrise time expressed in Unix Epoch value
     * @param sunset sunset time expressed in Unix Epoch value
     */
    public Weather(long date, String cityName, String countryCode, String description, double tempKelvin,
                   long sunrise, long sunset) {
        this.date = date;
        this.cityName = cityName;
        this.countryCode = countryCode;
        this.description = description;
        this.tempKelvin = tempKelvin;
        this.tempFahrenheit = fahrenheitFromKelvin(tempKelvin);
        this.tempCelsius = celsiusFromKelvin(tempKelvin);
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    private double celsiusFromKelvin(double kelvinTemp) {
        return roundForAtMost2Decimals(kelvinTemp + KELVIN_ZERO);
    }

    private double fahrenheitFromKelvin(double kelvinTemp) {
        return roundForAtMost2Decimals(kelvinTemp * NINE_FIFTH + FAHRENHEIT_DELTA);
    }

    /**
     * Simple method to round the temperature values to up to two decimals.
     */
    private double roundForAtMost2Decimals(double value) {
        return Math.round(value * 100D) / 100D;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "date=" + date +
                ", cityName='" + cityName + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", description='" + description + '\'' +
                ", tempKelvin=" + tempKelvin +
                ", tempCelsius=" + tempCelsius +
                ", tempFahrenheit=" + tempFahrenheit +
                ", sunrise=" + sunrise +
                ", sunset=" + sunset +
                '}';
    }

    /*
    @JsonIgnore is used to skip a getter and not include it into the auto generated JSON.
    @JsonProperty is used here to explicitly define a name for the attribute when is set into the JSON output.
    By default the JSON object will pick up the property if there is no Ignore or Property annotation and will
    use the name of the getter (without the "get" prefix) as the attribute name. Check i.e.: getCityName
    It is recommended to define the JsonProperty to avoid breaking an eventual contract on the generated JSON.
    */

    @JsonProperty("date")
    public long getDate() {
        return date;
    }

    // by default cityName will be used as the JSON attribute name
    public String getCityName() {
        return cityName;
    }

    @JsonProperty("countryCode")
    public String getCountryCode() {
        return countryCode;
    }

    @JsonProperty("overallDescription")
    public String getDescription() {
        return description;
    }

    @JsonProperty("sunrise")
    public long getSunrise() {
        return sunrise;
    }

    @JsonProperty("sunset")
    public long getSunset() {
        return sunset;
    }
    @JsonProperty("tempC")
    public double getTempCelsius() {
        return tempCelsius;
    }

    @JsonProperty("tempF")
    public double getTempFahrenheit() {
        return tempFahrenheit;
    }

    @JsonIgnore
    public double getTempKelvin() {
        return tempKelvin;
    }
}
