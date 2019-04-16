package com.yoghurt1131.weatherapi.application.service;

import com.yoghurt1131.weatherapi.domain.input.valueobject.RangedWeather;
import com.yoghurt1131.weatherapi.domain.output.valueobject.Forecast;

import java.util.List;

/**
 * Apiレスポンスをもとに天気予報を作成する
 */
public interface WeatherInterpreter {

    Forecast interpret(List<RangedWeather> weatherData);
}
