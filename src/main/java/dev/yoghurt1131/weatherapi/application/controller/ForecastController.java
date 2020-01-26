package dev.yoghurt1131.weatherapi.application.controller;

import dev.yoghurt1131.weatherapi.application.exception.ApiCallException;
import dev.yoghurt1131.weatherapi.application.service.WeatherApiService;
import dev.yoghurt1131.weatherapi.domain.output.valueobject.Forecast;
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
    public Forecast dailyForecast() throws ApiCallException {
        return weatherApiService.getTodaysWeather("Tokyo");

    }
}
