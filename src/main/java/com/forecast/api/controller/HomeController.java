package com.forecast.api.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.websocket.server.PathParam;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.forecast.api.model.Weather;
import com.forecast.api.model.WeatherView;
import com.forecast.api.owm.OpenWeatherMapClient;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class HomeController {

    private final OpenWeatherMapClient openWeatherMapClient;

    public HomeController(OpenWeatherMapClient openWeatherMapClient) {
        this.openWeatherMapClient = openWeatherMapClient;
    }

    @GetMapping(value = "/weatherbyname")
    public String weather(@PathParam("cityName") String cityName, Model model) {
        Weather weather = this.openWeatherMapClient.fetchWeatherByCityName(cityName);
        model.addAttribute("weatherView", buildFromWeather(weather));
        return "forecast";
    }
    
    @GetMapping(value = "/weatherbylatlon")
    public String weather(@RequestParam ("lat") String lat, @RequestParam("lon") String lon, Model model) {
        Weather weather = this.openWeatherMapClient.fetchWeatherByLatAndLon(lat, lon);
        model.addAttribute("weatherView", buildFromWeather(weather));
        return "forecast";
    }

    private WeatherView buildFromWeather(Weather weather) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm (z)");
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a (z)");
        timeFormat.setTimeZone(TimeZone.getTimeZone(weather.getCountryCode()));
        return WeatherView.WeatherViewBuilder.aWeatherView()
                .withDate(dateFormat.format(new Date(weather.getDate() * 1000L)))
                .withCityName(weather.getCityName())
                .withOverallDescription(weather.getDescription())
                .withTempC(Double.toString(weather.getTempCelsius()))
                .withTempF(Double.toString(weather.getTempFahrenheit()))
                .withSunrise(timeFormat.format(new Date(weather.getSunrise() * 1000L)))
                .withSunset(timeFormat.format(new Date(weather.getSunset() * 1000L)))
                .build();
    }
}
