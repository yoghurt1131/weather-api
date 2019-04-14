package com.yoghurt1131.weatherapi.application.service;

import com.yoghurt1131.weatherapi.application.exception.ApiCallException;
import com.yoghurt1131.weatherapi.domain.CurrentWeather;

public interface WeatherApiService {

    CurrentWeather getCurrentWeather(String cityName) throws ApiCallException;

    /**
     * 指定された都市の今日の天気を返す
     * @param cityName
     * @throws ApiCallException
     */
    void getTodaysWeather(String cityName) throws ApiCallException;
}
