package dev.yoghurt1131.weatherapi.application.service;

import dev.yoghurt1131.weatherapi.domain.input.valueobject.RangedWeather;
import dev.yoghurt1131.weatherapi.domain.input.valueobject.RangedWeatherProperty;
import dev.yoghurt1131.weatherapi.domain.input.valueobject.Weather;
import dev.yoghurt1131.weatherapi.domain.output.valueobject.Forecast;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@Component
public class TodayForecastInterpreter implements WeatherInterpreter {
    @Override
    public Forecast interpret(List<RangedWeather> weatherData) {
        Forecast forecast = new Forecast();

        // 多数決で天気を決める
        Map<String, Long> statusCountMap = getTodaysWeather(weatherData).flatMap(w -> w.getWeathers().stream())
                .collect(groupingBy(Weather::getStatus, counting()));
        Optional<String> status = statusCountMap.entrySet().stream()
                .sorted((s, t) -> (int)(s.getValue() - t.getValue()))
                .map(Map.Entry::getKey).findFirst();
        forecast.setStatus(status.get());

        // アイコンはステータスから決定
        String icon = getTodaysWeather(weatherData).flatMap(w -> w.getWeathers().stream())
                .filter(w -> w.getStatus().equals(status.get()))
                .map(Weather::getIcon).findFirst().get();

        forecast.setStatus(icon);

        // 最高気温
        OptionalDouble maxTemperature = getTodaysWeather(weatherData).map(RangedWeather::getProperty)
                .mapToDouble(RangedWeatherProperty::getTempratureMax).max();
        forecast.setMaxTemperature(maxTemperature.orElse(0.0));

        // 最低気温
        OptionalDouble minTemperature = getTodaysWeather(weatherData).map(RangedWeather::getProperty)
                .mapToDouble(RangedWeatherProperty::getTempratureMin).min();
        forecast.setMinTemperature(minTemperature.orElse(0.0));


        return forecast;
    }

    private LocalDateTime toDateTime(String dateTimeText) {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        //"2019-04-14 15:00:00"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(dateTimeText, formatter);
    }

    /**
     * 今日の天気だけ取得
     * @param weatherData
     * @return
     */
    private Stream<RangedWeather> getTodaysWeather(List<RangedWeather> weatherData) {
        LocalDateTime endOfToday = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
        return weatherData.stream().filter(w -> toDateTime(w.getUtcDatetime()).isBefore(endOfToday));
    }
}
