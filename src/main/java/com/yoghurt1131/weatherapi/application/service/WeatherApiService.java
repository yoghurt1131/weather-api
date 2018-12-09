package com.yoghurt1131.weatherapi.application.service;

import com.yoghurt1131.weatherapi.application.Exception.ApiCallException;
import com.yoghurt1131.weatherapi.domain.City;
import com.yoghurt1131.weatherapi.domain.CurrentWeather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.concurrent.TimeUnit;

@Service
public class WeatherApiService {
    private static final Logger logger = LoggerFactory.getLogger(WeatherApiService.class);

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    RedisTemplate <String, City> redisTemplate;

    @Value("${openweatherapi.url}")
    String openWeatherApiUrl;
    @Value("${openweatherapi.key}")
    String apiKey;

    private static String CURRENT_WEATHER = "/weather";

    public CurrentWeather getCurrentWeather(String cityName) throws ApiCallException {

        City city = redisTemplate.opsForValue().get(cityName);
        if( city != null) {
            logger.info(String.format("Cache Hit.(%s)", cityName));
            return new CurrentWeather(city.getName(), city.extractWeather(), city.extractKelvin());
        }
        logger.info(String.format("Not in cache.(%s)", cityName));
        String currentWeathrUrl = openWeatherApiUrl  + CURRENT_WEATHER;
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(currentWeathrUrl)
                .queryParam("APPID", apiKey)
                .queryParam("q", cityName);

        try {
            logger.info("Start calling API:" + currentWeathrUrl);
            ResponseEntity<City> entity = restTemplate.getForEntity(builder.toUriString(), City.class);
            City response = entity.getBody();
            logger.info("Finish calling API:" + currentWeathrUrl);
            HttpStatus reponseStatus = entity.getStatusCode();
            logger.info("Response Status Code: " + reponseStatus);
            logger.warn("Response Body:" + entity.getBody());
            redisTemplate.opsForValue().set(cityName, response);
            redisTemplate.expire(cityName, 60, TimeUnit.MINUTES);

            CurrentWeather currentWeather = new CurrentWeather(response.getName(), response.extractWeather(), response.extractKelvin());
            return currentWeather;
        } catch (RestClientException exception) {
            logger.error("Error has occurred when calling weather api.");
            logger.info("Error Message:" + exception.getMessage());
            throw new ApiCallException(exception.getMessage(), exception.getCause());
        }
    }
}
