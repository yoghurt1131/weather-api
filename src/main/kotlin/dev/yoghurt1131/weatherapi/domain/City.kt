package dev.yoghurt1131.weatherapi.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeId
import com.fasterxml.jackson.annotation.JsonTypeInfo
import dev.yoghurt1131.weatherapi.domain.input.valueobject.Weather


data class City (
        val name: String,
        @JsonProperty("weather")
        val weathers: List<Weather>,
        @JsonProperty("main")
        val temperature: Temperature
) {

        fun extractWeather() = if(weathers.isNotEmpty()) weathers.first().status else "";

        fun extractKelvin() = if(weathers.isNotEmpty()) temperature.temp else 0.0;

        @JsonIgnore
        fun getCurrentWeather(): CurrentWeather {
                var currentWeather: CurrentWeather =
                        CurrentWeather(name, extractWeather(), extractKelvin(), weathers.first().getWeatherIconUrl())
                return currentWeather;
        }

        fun buildWeather() = CurrentWeather(
                        name, extractWeather(), extractKelvin(), weathers.get(0).getWeatherIconUrl()
                );
}