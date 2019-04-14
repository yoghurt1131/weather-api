package com.yoghurt1131.weatherapi.application.controller;

import com.yoghurt1131.weatherapi.application.exception.ApiCallException;
import com.yoghurt1131.weatherapi.application.service.WeatherApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/forecast")
public class ForecastController {

    private final WeatherApiService weatherApiService;

    @Autowired
    public ForecastController(WeatherApiService weatherApiService) {
        this.weatherApiService = weatherApiService;
    }


    @GetMapping("/daily")
    public String dailyForecast() throws ApiCallException {
        weatherApiService.getTodaysWeather("Tokyo");

        return "OK";
    }
}
