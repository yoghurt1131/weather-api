package dev.yoghurt1131.weatherapi

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableAutoConfiguration
class WeatherApiApplication

fun main(args: Array<String>) {
    runApplication<WeatherApiApplication>(*args)
}
