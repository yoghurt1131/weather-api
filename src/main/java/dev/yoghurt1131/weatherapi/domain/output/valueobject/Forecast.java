package dev.yoghurt1131.weatherapi.domain.output.valueobject;

import lombok.Data;

/**
 * 天気予報
 */
@Data
public class Forecast {

    private String cityName;

    // 天気
    private String status;

    // 天気画像
    private String weatherIconUrl;

    // 最高気温
    private double maxTemperature;

    // 最低気温
    private double minTemperature;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWeatherIconUrl() {
        return weatherIconUrl;
    }

    public void setWeatherIconUrl(String weatherIconUrl) {
        this.weatherIconUrl = weatherIconUrl;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }
}
