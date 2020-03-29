package dev.yoghurt1131.weatherapi.application.service

import dev.yoghurt1131.weatherapi.application.exception.ApiCallException
import dev.yoghurt1131.weatherapi.domain.City
import dev.yoghurt1131.weatherapi.domain.CurrentWeather
import dev.yoghurt1131.weatherapi.domain.input.valueobject.FiveDaysForecast
import dev.yoghurt1131.weatherapi.domain.output.valueobject.Forecast
import dev.yoghurt1131.weatherapi.infrastructure.RedisTemplateBuilder
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
        private val redisTemplateBuilder: RedisTemplateBuilder,
        private val weatherInterpreter: WeatherInterpreter
) : WeatherApiService{

    private val logger = LoggerFactory.getLogger(this.javaClass)
    private val redisTemplate = redisTemplateBuilder.build(City::class.java)

    @Value("\${openweatherapi.url}")
    protected var openWeatherApiUrl: String? = null
    @Value("\${openweatherapi.key}")
    protected var apiKey: String? = null

    private val CURRENT_WEATHER = "/weather"
    private val FORECAST_PATH = "/forecast"

    override fun getCurrentWeather(cityName: String): CurrentWeather {
        // read redis value
        val city = redisTemplate.read(cityName)
        if (city != null) {
            return city.buildWeather();
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

        // write redis value
        redisTemplate.write(cityName, response, 30, TimeUnit.MINUTES)
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