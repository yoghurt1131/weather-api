package dev.yoghurt1131.weatherapi.domain.input.valueobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RangedWeatherProperty {

    @JsonProperty("temp")
    private double temperature;

    @JsonProperty("temp_min")
    private double tempratureMin;

    @JsonProperty("temp_max")
    private double tempratureMax;

    @JsonProperty("pressure")
    private double pressure;

    @JsonProperty("sea_level")
    private double pressureOfSeaLevel;

    @JsonProperty("gnd_level")
    private double pressureOfGroundLevel;

    @JsonProperty("humidity")
    private int humidity;

}
