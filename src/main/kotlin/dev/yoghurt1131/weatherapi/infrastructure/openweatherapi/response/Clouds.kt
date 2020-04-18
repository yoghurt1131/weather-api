package dev.yoghurt1131.weatherapi.infrastructure.openweatherapi.response

import com.fasterxml.jackson.annotation.JsonProperty

data class Clouds(@JsonProperty("all") private val cloudness: Int)
