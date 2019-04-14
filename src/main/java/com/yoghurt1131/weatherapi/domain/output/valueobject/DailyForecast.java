package com.yoghurt1131.weatherapi.domain.output.valueobject;

import lombok.Data;

/**
 * 一日の天気予報
 */
@Data
public class DailyForecast {

    private String cityName;

    // 天気
    private String status;

    // 天気画像
    private String weatherIconUrl;

    // 最高気温
    private long temperatureMax;

    // 最低気温
    private long temperatureMin;
}
