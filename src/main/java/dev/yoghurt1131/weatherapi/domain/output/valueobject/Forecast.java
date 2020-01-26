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
}
