package dev.yoghurt1131.weatherapi.application.service

import dev.yoghurt1131.weatherapi.application.exception.ApiCallException
import dev.yoghurt1131.weatherapi.domain.City
import dev.yoghurt1131.weatherapi.domain.CurrentWeather
import dev.yoghurt1131.weatherapi.domain.input.valueobject.FiveDaysForecast
import dev.yoghurt1131.weatherapi.domain.output.valueobject.Forecast
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.RedisConnectionFailureException
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.util.concurrent.TimeUnit

@Service
class WeatherApiServiceImpl(
        private val restTemplate: RestTemplate,
        private val redisTemplate: RedisTemplate<String, City>,
        private val weatherInterpreter: WeatherInterpreter
) : WeatherApiService{

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Value("\${openweatherapi.url}")
    protected var openWeatherApiUrl: String? = null
    @Value("\${openweatherapi.key}")
    protected var apiKey: String? = null

    private val CURRENT_WEATHER = "/weather"
    private val FORECAST_PATH = "/forecast"

    override fun getCurrentWeather(cityName: String): CurrentWeather {
        try {
            val city = redisTemplate.opsForValue().get(cityName);
            if (city != null) {
                logger.info("Cach Hit.($cityName)");
                return city.buildWeather();
            }
            logger.info("Not in cache.($cityName)");
        } catch (exception: RedisConnectionFailureException) {
            logger.warn("Failed to conect Redis. ${exception.message}")
        }
        var response: City?
        try {
            val currentWeatherUrl = "${openWeatherApiUrl}${CURRENT_WEATHER}";
            logger.info("Start calling API:$currentWeatherUrl");
            val requestUrl = buildRequestUrlWithCityName(currentWeatherUrl, cityName);
            val responseEntity: ResponseEntity<City> = restTemplate.getForEntity(requestUrl, City::class.java)
            response = responseEntity.body;
            logger.info("Finish calling API:$currentWeatherUrl")
            logger.info(String.format("Response Status Code: %s, Response Body:", responseEntity.statusCode, response));
        } catch (exception: RestClientException) {
            logger.error("Error has occurred when calling weather api.")
            logger.info("Error Message:" + exception.message)
            throw ApiCallException(exception.message, exception.cause)
        }
        try {
            redisTemplate.opsForValue().set(cityName, response);
            redisTemplate.expire(cityName, 30, TimeUnit.SECONDS);
        } catch (exception: RedisConnectionFailureException) {
            logger.warn("Failed to conect Redis. ${exception.message}")
        }
        return response.buildWeather();
    }

    override fun getTodayWeather(cityName: String): Forecast {
        var apiResponse: FiveDaysForecast?;
        // TODO use redis cache
        try {
            val forecastUrl = "${openWeatherApiUrl}${FORECAST_PATH}";
            logger.info("Start calling API:$forecastUrl")
            val requestUrl = buildRequestUrlWithCityName(forecastUrl, cityName)
            logger.info("Request URL:$requestUrl")
            val entity = restTemplate.getForEntity(requestUrl, FiveDaysForecast::class.java)
            apiResponse = entity.body
            logger.info("Finish calling API:$forecastUrl")
            val reponseStatus = entity.statusCode
            logger.info("Response Status Code: $reponseStatus")
            logger.info("Response Body:" + entity.body!!)
        } catch (exception: RestClientException) {
            logger.error("Error has occurred when calling weather api.");
            logger.info("Error Message:" + exception.message);
            throw ApiCallException(exception.message, exception.cause);
        }
        return weatherInterpreter.interpret(cityName, apiResponse.forecasts);
    }

    private fun buildRequestUrlWithCityName(url: String, cityName: String): String {
        val builder = UriComponentsBuilder.fromUriString(url)
                .queryParam("q", cityName)
                .queryParam("APPID", apiKey)
        return builder.toUriString()
    }
}