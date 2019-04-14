package com.yoghurt1131.weatherapi.domain.input.valueobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * ValueObject for response of OpenWeatherAPI
 * weather forecast for 5 days with data every 3 hours by city name
 */
@Data
public class FiveDaysForecast {

    // Return code from API
    @JsonProperty("cod")
    private String code;

    //  Number of lines returned by API
    @JsonProperty("cnt")
    private int count;

    @JsonProperty("list")
    private List<RangedWeather> forecasts;
}
