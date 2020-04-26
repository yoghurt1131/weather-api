package dev.yoghurt1131.weatherapi.application.service

import dev.yoghurt1131.weatherapi.domain.City
import dev.yoghurt1131.weatherapi.infrastructure.weather.response.CityWeather
import dev.yoghurt1131.weatherapi.domain.CurrentWeather
import dev.yoghurt1131.weatherapi.infrastructure.weather.response.FiveDaysForecastResponse
import dev.yoghurt1131.weatherapi.domain.Forecast
import dev.yoghurt1131.weatherapi.infrastructure.weather.CurrentWeatherWrapper
import dev.yoghurt1131.weatherapi.infrastructure.weather.FiveDayForecastWrapper
import dev.yoghurt1131.weatherapi.infrastructure.redis.RedisTemplateBuilder
import java.util.concurrent.TimeUnit
import org.springframework.stereotype.Service

@Service
class WeatherApiServiceImpl(
        private val currentWeatherWrapper: CurrentWeatherWrapper,
        private val fiveDayForecastWrapper: FiveDayForecastWrapper,
        private val redisTemplateBuilder: RedisTemplateBuilder,
        private val weatherInterpreter: WeatherInterpreter
) : WeatherApiService {

    private val redisTemplate = redisTemplateBuilder.build(CityWeather::class.java)

    override fun getCurrentWeather(cityName: String): CurrentWeather {
        // read redis value
        val city = redisTemplate.read(cityName)
        if (city != null) {
            return city.buildWeather()
        }

        // api call
        val response = currentWeatherWrapper.execute(City.Tokyo)

        // write redis value
        redisTemplate.write(cityName, response, 30, TimeUnit.MINUTES)
        return response.buildWeather()
    }

    override fun getTodayWeather(cityName: String): Forecast {
        var apiResponse: FiveDaysForecastResponse?
        // TODO use redis cache
        val response = fiveDayForecastWrapper.execute(City.Tokyo)
        return weatherInterpreter.interpret(cityName, response.forecasts)
    }
}
