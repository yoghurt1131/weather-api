package com.yoghurt1131.weatherapi.domain.valueobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RangedWeather {

    // unix timestamp
    @JsonProperty("dt")
    private long time;

    @JsonProperty("dt_text")
    private String utcDatetime;

    @JsonProperty("main")
    private RangedWeatherProperty property;

    @JsonProperty("weather")
    private Weather weather;

    @JsonProperty("clouds")
    private Clouds clouds;

    @JsonProperty("wind")
    private Wind wind;

}
