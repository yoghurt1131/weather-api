package com.yoghurt1131.weatherapi.domain.input.valueobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RangedWeatherProperty {

    @JsonProperty("temp")
    private float temperature;

    @JsonProperty("temp_min")
    private float tempratureMin;

    @JsonProperty("temp_max")
    private float tempratureMax;

    @JsonProperty("pressure")
    private float pressure;

    @JsonProperty("sea_level")
    private float pressureOfSeaLevel;

    @JsonProperty("gnd_level")
    private float pressureOfGroundLevel;

    @JsonProperty("humidity")
    private int humidity;

}
