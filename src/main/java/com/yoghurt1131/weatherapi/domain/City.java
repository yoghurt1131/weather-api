package com.yoghurt1131.weatherapi.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class City implements Serializable {

    private String name;
    @JsonProperty("weather")
    private ArrayList<Weather> weathers;
    @JsonProperty("main")
    private Temperature temperature;

    public String extractWeather() {
        if (weathers.size() == 0) {
            return null;
        }
        return weathers.get(0).getStatus();
    }

    public double extractKelvin() {
        if (weathers.size() == 0) {
            return 0.0;
        }
        return temperature.getTemp();
    }
}
