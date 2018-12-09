package com.yoghurt1131.weatherapi.application.controller;

import com.yoghurt1131.weatherapi.application.Exception.ApiCallException;
import com.yoghurt1131.weatherapi.application.service.WeatherApiService;
import com.yoghurt1131.weatherapi.domain.CurrentWeather;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/current")
@RestController
public class CurrentWeatherController {

    @Autowired
    WeatherApiService weatherApiService;

    @GetMapping("/city")
    public CurrentWeather city(@RequestParam String cityName) {
        try {
            CurrentWeather weather = weatherApiService.getCurrentWeather(cityName);
            return weather;
        } catch (ApiCallException e) {
            return null;
        }
    }
}
