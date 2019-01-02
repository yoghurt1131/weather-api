package com.yoghurt1131.weatherapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CurrentWeather {
    private static final double ABSOLUTE_TEMPERATURE = -273.15;

    private String cityName;
    private String status;
    @JsonIgnore
    private double kelvin;
    private double temperature;
    private String weatherIconUrl;

    public CurrentWeather(String cityName, String status, double kelvin) {
        this.cityName = cityName;
        this.status = status;
        this.kelvin = kelvin;
        this.temperature =  convertTemparature(kelvin);
    }

    public static CurrentWeather getInstanceFromCity(City city) {
        CurrentWeather instance = new CurrentWeather();
        instance.cityName = city.getName();
        instance.status = city.extractWeather();
        instance.kelvin = city.extractKelvin();
        instance.temperature =  convertTemparature(instance.kelvin);
        instance.weatherIconUrl = city.getWeathers().get(0).getWeatherIconUrl();
        return instance;
    }

    private static double convertTemparature(double kelvin) {
        BigDecimal decimal = new BigDecimal(kelvin + ABSOLUTE_TEMPERATURE);
        return decimal.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
