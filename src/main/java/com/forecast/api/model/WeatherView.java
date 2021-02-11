package com.forecast.api.model;

/**
 * Simple String based object for a Weather View representation.
 * This is basically mapped to a JSON and sent back to clients for presenting this in a UI.
 *
 * Class has a Builder to help create a new instance with the correct values since all are of type String.
 */
public class WeatherView {

    private final String date;
    private final String cityName;
    private final String overallDescription;
    private final String tempF;
    private final String tempC;
    private final String sunrise;
    private final String sunset;

    private WeatherView(String date, String cityName, String overallDescription, String tempF, String tempC,
                        String sunrise, String sunset) {
        this.date = date;
        this.cityName = cityName;
        this.overallDescription = overallDescription;
        this.tempF = tempF;
        this.tempC = tempC;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    public String getDate() {
        return date;
    }

    public String getCityName() {
        return cityName;
    }

    public String getOverallDescription() {
        return overallDescription;
    }

    public String getTempF() {
        return tempF;
    }

    public String getTempC() {
        return tempC;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public static final class WeatherViewBuilder {
        private String date;
        private String cityName;
        private String overallDescription;
        private String tempF;
        private String tempC;
        private String sunrise;
        private String sunset;

        private WeatherViewBuilder() {
        }

        public static WeatherViewBuilder aWeatherView() {
            return new WeatherViewBuilder();
        }

        public WeatherViewBuilder withDate(String todayDate) {
            this.date = todayDate;
            return this;
        }

        public WeatherViewBuilder withCityName(String cityName) {
            this.cityName = cityName;
            return this;
        }

        public WeatherViewBuilder withOverallDescription(String overallDescription) {
            this.overallDescription = overallDescription;
            return this;
        }

        public WeatherViewBuilder withTempF(String tempF) {
            this.tempF = tempF;
            return this;
        }

        public WeatherViewBuilder withTempC(String tempC) {
            this.tempC = tempC;
            return this;
        }

        public WeatherViewBuilder withSunrise(String sunrise) {
            this.sunrise = sunrise;
            return this;
        }

        public WeatherViewBuilder withSunset(String sunset) {
            this.sunset = sunset;
            return this;
        }

        public WeatherView build() {
            return new WeatherView(
                    this.date,
                    this.cityName,
                    this.overallDescription,
                    this.tempF,
                    this.tempC,
                    this.sunrise,
                    this.sunset
            );
        }
    }
}
