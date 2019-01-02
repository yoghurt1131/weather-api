package com.yoghurt1131.weatherapi.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Weather implements Serializable {

    public static final String WEATHER_ICON_URL = "http://openweathermap.org/img/w/";

    private int id;
    @JsonProperty("main")
    private String status;
    private String description;
    private String icon;

    /**
     * Open Weather Mapが提供するアイコンのURL
     * @return icon url
     */
    public String getWeatherIconUrl() {
        return WEATHER_ICON_URL + icon + ".png";
    }
}
