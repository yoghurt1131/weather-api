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

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getTempratureMin() {
        return tempratureMin;
    }

    public void setTempratureMin(double tempratureMin) {
        this.tempratureMin = tempratureMin;
    }

    public double getTempratureMax() {
        return tempratureMax;
    }

    public void setTempratureMax(double tempratureMax) {
        this.tempratureMax = tempratureMax;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getPressureOfSeaLevel() {
        return pressureOfSeaLevel;
    }

    public void setPressureOfSeaLevel(double pressureOfSeaLevel) {
        this.pressureOfSeaLevel = pressureOfSeaLevel;
    }

    public double getPressureOfGroundLevel() {
        return pressureOfGroundLevel;
    }

    public void setPressureOfGroundLevel(double pressureOfGroundLevel) {
        this.pressureOfGroundLevel = pressureOfGroundLevel;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
}
