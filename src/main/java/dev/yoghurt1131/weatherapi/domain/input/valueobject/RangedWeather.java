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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getUtcDatetime() {
        return utcDatetime;
    }

    public void setUtcDatetime(String utcDatetime) {
        this.utcDatetime = utcDatetime;
    }

    public RangedWeatherProperty getProperty() {
        return property;
    }

    public void setProperty(RangedWeatherProperty property) {
        this.property = property;
    }

    public List<Weather> getWeathers() {
        return weathers;
    }

    public void setWeathers(List<Weather> weathers) {
        this.weathers = weathers;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }
}
