package dev.yoghurt1131.weatherapi.application.service;

import dev.yoghurt1131.weatherapi.application.exception.ApiCallException;
import dev.yoghurt1131.weatherapi.domain.CurrentWeather;
import dev.yoghurt1131.weatherapi.domain.output.valueobject.Forecast;

public interface WeatherApiService {

    CurrentWeather getCurrentWeather(String cityName) throws ApiCallException;

    /**
     * 指定された都市の今日の天気を返す
     * @param cityName
     * @throws ApiCallException
     */
    Forecast getTodaysWeather(String cityName) throws ApiCallException;
}
