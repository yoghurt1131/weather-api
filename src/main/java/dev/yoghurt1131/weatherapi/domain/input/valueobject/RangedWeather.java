package dev.yoghurt1131.weatherapi.domain.input.valueobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class RangedWeather {

    // unix timestamp
    @JsonProperty("dt")
    private long time;

    @JsonProperty("dt_txt")
    private String utcDatetime;

    @JsonProperty("main")
    private RangedWeatherProperty property;

    @JsonProperty("weather")
    private List<Weather> weathers;

    @JsonProperty("clouds")
    private Clouds clouds;

    @JsonProperty("wind")
    private Wind wind;

}
