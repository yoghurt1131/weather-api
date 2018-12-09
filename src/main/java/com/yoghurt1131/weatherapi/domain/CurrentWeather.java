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

    public CurrentWeather(String cityName, String status, double kelvin) {
        this.cityName = cityName;
        this.status = status;
        this.kelvin = kelvin;
        BigDecimal decimal = new BigDecimal(kelvin + ABSOLUTE_TEMPERATURE);
        this.temperature =  decimal.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
