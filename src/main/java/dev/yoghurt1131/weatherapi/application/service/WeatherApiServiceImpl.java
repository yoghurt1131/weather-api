package dev.yoghurt1131.weatherapi.application.service;

import dev.yoghurt1131.weatherapi.application.exception.ApiCallException;
import dev.yoghurt1131.weatherapi.domain.City;
import dev.yoghurt1131.weatherapi.domain.CurrentWeather;
import dev.yoghurt1131.weatherapi.domain.input.valueobject.FiveDaysForecast;
import dev.yoghurt1131.weatherapi.domain.output.valueobject.Forecast;
import dev.yoghurt1131.weatherapi.service.WeatherInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.concurrent.TimeUnit;

@Service
public class WeatherApiServiceImpl implements WeatherApiService {
    private static final Logger logger = LoggerFactory.getLogger(WeatherApiServiceImpl.class);

    private final RestTemplate restTemplate;

    private final RedisTemplate <String, City> redisTemplate;

    private final WeatherInterpreter interpreter;

    @Value("${openweatherapi.url}")
    protected String openWeatherApiUrl;
    @Value("${openweatherapi.key}")
    protected String apiKey;

    private static String CURRENT_WEATHER = "/weather";

    private static String FORECAST_PATH = "/forecast";

    public WeatherApiServiceImpl(RestTemplate restTemplate, RedisTemplate<String, City> redisTemplate, WeatherInterpreter interpreter) {
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
        this.interpreter =interpreter;
    }

    public CurrentWeather getCurrentWeather(String cityName) throws ApiCallException {
        try {
            City city = redisTemplate.opsForValue().get(cityName);
            if (city != null) {
                logger.info(String.format("Cache Hit.(%s)", cityName));
                return CurrentWeather.getInstanceFromCity(city);
            }
            logger.info(String.format("Not in cache.(%s)", cityName));
        } catch (RedisConnectionFailureException exception) {
            logger.warn("Failed to Connect Redis." + exception.getMessage());
        }
        City response;
        try {
            String currentWeathrUrl = openWeatherApiUrl  + CURRENT_WEATHER;
            logger.info("Start calling API:" + currentWeathrUrl);
            String requestUrl = buildRequestUrlWithCityName(currentWeathrUrl, cityName);
            ResponseEntity<City> entity = restTemplate.getForEntity(requestUrl, City.class);
            response = entity.getBody();
            logger.info("Finish calling API:" + currentWeathrUrl);
            logger.info(String.format("Response Status Code: %s, Response Body:", entity.getStatusCode(), entity.getBody()));
        } catch (RestClientException exception) {
            logger.error("Error has occurred when calling weather api.");
            logger.info("Error Message:" + exception.getMessage());
            throw new ApiCallException(exception.getMessage(), exception.getCause());
        }
        try {
            redisTemplate.opsForValue().set(cityName, response);
            redisTemplate.expire(cityName, 30, TimeUnit.MINUTES);
        } catch (RedisConnectionFailureException exception) {
            logger.warn("Failed to Connect Redis." + exception.getMessage());
        }
        return  CurrentWeather.getInstanceFromCity(response);
    }

    @Override
    public Forecast getTodaysWeather(String cityName) throws ApiCallException {

        FiveDaysForecast apiResponse = null;
        // Todo Use Cache
        logger.info(String.format("%s's forecast is not in cache.", cityName));
        try {
            String forecastUrl = openWeatherApiUrl  + FORECAST_PATH;
            logger.info("Start calling API:" + forecastUrl);
            String requestUrl = buildRequestUrlWithCityName(forecastUrl, cityName);
            ResponseEntity<FiveDaysForecast> entity = restTemplate.getForEntity(requestUrl, FiveDaysForecast.class);
            apiResponse = entity.getBody();
            logger.info("Finish calling API:" + forecastUrl);
            HttpStatus reponseStatus = entity.getStatusCode();
            logger.info("Response Status Code: " + reponseStatus);
            logger.info("Response Body:" + entity.getBody());
        } catch (RestClientException exception) {
            logger.error("Error has occurred when calling weather api.");
            logger.info("Error Message:" + exception.getMessage());
            throw new ApiCallException(exception.getMessage(), exception.getCause());
        }

        Forecast forecast = interpreter.interpret(apiResponse.getForecasts());
        forecast.setCityName(cityName);

        return forecast;
    }

    private String buildRequestUrlWithCityName(String url, String cityName) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParam("q", cityName)
                .queryParam("APPID", apiKey);
        return builder.toUriString();
    }

}
