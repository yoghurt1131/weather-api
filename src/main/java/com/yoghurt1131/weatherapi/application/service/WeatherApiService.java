package com.yoghurt1131.weatherapi.application.service;

import com.yoghurt1131.weatherapi.domain.City;
import com.yoghurt1131.weatherapi.domain.CurrentWeather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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

    public CurrentWeather getCurrentWeather(String cityName) {

        City city = redisTemplate.opsForValue().get(cityName);
        if( city != null) {
            logger.info("Cache Hit.{0}.".format(cityName));
            return new CurrentWeather(city.getName(), city.extractWeather(), city.extractKelvin());
        }
        logger.info("Cache: " + city);
        String currentWeathrUrl = openWeatherApiUrl  + CURRENT_WEATHER;
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(currentWeathrUrl)
                .queryParam("APPID", apiKey)
                .queryParam("q", cityName);

        City response = restTemplate.getForObject(builder.toUriString(), City.class);
        logger.info("Api Respone:" + response.toString());
        redisTemplate.opsForValue().set(cityName, response);

        CurrentWeather currentWeather  = new CurrentWeather(response.getName(), response.extractWeather(), response.extractKelvin());
        return currentWeather;
    }
}
