package com.yoghurt1131.weatherapi.application.controller;

import com.yoghurt1131.weatherapi.application.Exception.ApiCallException;
import com.yoghurt1131.weatherapi.application.service.WeatherApiService;
import com.yoghurt1131.weatherapi.domain.CurrentWeather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/current")
@RestController
public class CurrentWeatherController {

    @Autowired
    WeatherApiService weatherApiService;

    @GetMapping("/city")
    public CurrentWeather city(@RequestParam(required = true) String cityName) {
        try {
            CurrentWeather weather = weatherApiService.getCurrentWeather(cityName);
            return weather;
        } catch (ApiCallException e) {
            return null;
        }
    }
}
