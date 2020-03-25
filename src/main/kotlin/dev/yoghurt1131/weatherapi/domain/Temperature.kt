package dev.yoghurt1131.weatherapi.domain

import com.fasterxml.jackson.annotation.JsonTypeInfo

data class Temperature(
        val temp: Double,
        val pressure: Int,
        val humidity: Int,
        val tempMax: Double,
        val tempMin: Double
)
