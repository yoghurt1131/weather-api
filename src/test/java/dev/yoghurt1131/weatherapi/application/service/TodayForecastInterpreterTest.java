package dev.yoghurt1131.weatherapi.application.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.yoghurt1131.weatherapi.domain.input.valueobject.RangedWeather;
import dev.yoghurt1131.weatherapi.domain.output.valueobject.Forecast;
import dev.yoghurt1131.weatherapi.service.TodayForecastInterpreter;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class TodayForecastInterpreterTest {

    private List<RangedWeather> forecastResponse;

    private TodayForecastInterpreter target;

    @Before
    public void setup() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("data/forecast.json").getFile());
        forecastResponse = mapper.readValue(file, new TypeReference<List<RangedWeather>>() {});

        target = new TodayForecastInterpreter();
    }

    @Test
    public void testInterpretSuccess() {
        // execute
        Forecast forecast = target.interpret(forecastResponse);
    }
}
