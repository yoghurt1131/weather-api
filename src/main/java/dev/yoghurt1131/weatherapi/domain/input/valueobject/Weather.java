package dev.yoghurt1131.weatherapi.domain.input.valueobject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Weather implements Serializable {

    public static final String WEATHER_ICON_URL = "http://openweathermap.org/img/w/";

    @JsonProperty("id")
    private int id;
    // Weather Status
    @JsonProperty("main")
    private String status;

    // description of weather
    @JsonProperty("description")
    private String description;

    // icon id of open weather map's weather icons
    @JsonProperty("icon")
    private String icon;

    @JsonProperty("weatherIconUrl")
    private String weatherIconUrl;

    /**
     * Open Weather Mapが提供するアイコンのURL
     * @return icon url
     */
    public String getWeatherIconUrl() {
        return WEATHER_ICON_URL + icon + ".png";
    }
}
