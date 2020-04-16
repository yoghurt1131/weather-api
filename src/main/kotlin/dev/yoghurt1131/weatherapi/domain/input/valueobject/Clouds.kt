package dev.yoghurt1131.weatherapi.domain.input.valueobject

import com.fasterxml.jackson.annotation.JsonProperty

data class Clouds(@JsonProperty("all") private val cloudness: Int)
