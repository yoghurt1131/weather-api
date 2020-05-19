package dev.yoghurt1131.weatherapi.application.service

import dev.yoghurt1131.kledistemplate.core.accessor.KLedisTemplateBuilder
import dev.yoghurt1131.weatherapi.application.service.adapter.WeatherInterpreter
import dev.yoghurt1131.weatherapi.domain.entity.City
import dev.yoghurt1131.weatherapi.infrastructure.weatherapi.response.CityWeather
import dev.yoghurt1131.weatherapi.application.controller.response.CurrentWeather
import dev.yoghurt1131.weatherapi.infrastructure.weatherapi.response.FiveDaysForecastResponse
import dev.yoghurt1131.weatherapi.application.controller.response.Forecast
import dev.yoghurt1131.weatherapi.application.controller.response.RainForecast
import dev.yoghurt1131.weatherapi.infrastructure.weatherapi.CurrentWeatherWrapper
import dev.yoghurt1131.weatherapi.infrastructure.weatherapi.FiveDayForecastWrapper
import dev.yoghurt1131.weatherapi.infrastructure.redis.RedisTemplateBuilder
import java.util.concurrent.TimeUnit
import org.springframework.stereotype.Service

@Service
class WeatherApiServiceImpl(
        private val currentWeatherWrapper: CurrentWeatherWrapper,
        private val fiveDayForecastWrapper: FiveDayForecastWrapper,
        private val redisTemplateBuilder: KLedisTemplateBuilder,
        private val weatherInterpreter: WeatherInterpreter
) : WeatherApiService {

    private val redisTemplate = redisTemplateBuilder.build(CityWeather::class.java)

    override fun getCurrentWeather(cityName: String): CurrentWeather {
        // read redis value
        val cityWeatherCache = redisTemplate.read(cityName)
        if (cityWeatherCache != null) {
            return cityWeatherCache.buildWeather()
        }

        // api call
        val response = currentWeatherWrapper.execute(City.valueOf(cityName))

        // write redis value
        redisTemplate.write(cityName, response, 30, TimeUnit.MINUTES)
        return response.buildWeather()
    }

    override fun getTodayWeather(city: City): Forecast {
        val response = fiveDayForecastWrapper.execute(city)
        return weatherInterpreter.toTodaysForecast(city, response.forecasts)
    }

    override fun getRainForecast(city: City): List<RainForecast> {
        val response = fiveDayForecastWrapper.execute(city)
        return weatherInterpreter.toDailyRainForecast(city, response.forecasts)
    }
}
