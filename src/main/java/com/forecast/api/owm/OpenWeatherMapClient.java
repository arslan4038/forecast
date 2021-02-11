package com.forecast.api.owm;

import com.forecast.api.model.Weather;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * OpenWeatherMap API client
 */
@Component
public class OpenWeatherMapClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenWeatherMapClient.class);

    private final OpenWeatherMapConfig config;
    private final RestTemplate restTemplate;

    public OpenWeatherMapClient(OpenWeatherMapConfig config, RestTemplateBuilder builder) {
        this.config = config;
        this.restTemplate = builder.build();
    }

    @Cacheable("weatherByCityName")
    public Weather fetchWeatherByCityName(String name) {
        ResponseEntity<String> weatherData = this.restTemplate
                .getForEntity(this.config.getWeatherUrl(), String.class, name, this.config.getAppkey());
        String jsonString = weatherData.getBody();
        LOGGER.info("Call to OWM API for cityName: {} returned: {}", name, jsonString);
        return createFromJsonString(jsonString, true);
    }
    
    @Cacheable("weatherByLatLon")
    public Weather fetchWeatherByLatAndLon(String lat, String lon) {
        ResponseEntity<String> weatherData = this.restTemplate
                .getForEntity(this.config.getWeatherUrlLatLon(), String.class, lat, lon, this.config.getAppkey());
        String jsonString = weatherData.getBody();
        LOGGER.info("Call to OWM API for lat: {} and lon: {} returned: {}", lat, lon, jsonString);
        return createFromJsonString(jsonString, false);
    }

    /**
     * Parses the String json and uses the JsonPath library to extract specific values from the JSON document.
     *
     * The "string" paths can be extracted to the properties file for OWM.
     *
     * @param jsonString a string representation of a JSON
     * @param isCountry
     * @return an instance of Weather populated with data parsed from the given json string
     */
    private Weather createFromJsonString(String jsonString, boolean isCountry) {
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(jsonString);
        Integer date = JsonPath.read(document, "$.dt");
        String cityName = JsonPath.read(document, "$.name");
        String countryCode = "";
        if (isCountry) {
        	countryCode = JsonPath.read(document, "$.sys.country");
		}
        String description = JsonPath.read(document, "$.weather[0].description");
        Double temperature = JsonPath.read(document, "$.main.temp");
        Integer sunrise = JsonPath.read(document, "$.sys.sunrise");
        Integer sunset = JsonPath.read(document, "$.sys.sunset");
        return new Weather((long) date, cityName, countryCode, description, temperature, (long) sunrise, (long) sunset);
    }
}
