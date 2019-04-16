package com.yoghurt1131.weatherapi.application.service;

import com.yoghurt1131.weatherapi.application.exception.ApiCallException;
import com.yoghurt1131.weatherapi.domain.CurrentWeather;
import com.yoghurt1131.weatherapi.domain.output.valueobject.Forecast;

public interface WeatherApiService {

    CurrentWeather getCurrentWeather(String cityName) throws ApiCallException;

    /**
     * 指定された都市の今日の天気を返す
     * @param cityName
     * @throws ApiCallException
     */
    Forecast getTodaysWeather(String cityName) throws ApiCallException;
}
