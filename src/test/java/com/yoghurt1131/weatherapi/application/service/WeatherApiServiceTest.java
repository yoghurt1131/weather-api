package com.yoghurt1131.weatherapi.application.service;

import com.yoghurt1131.weatherapi.application.Exception.ApiCallException;
import com.yoghurt1131.weatherapi.domain.City;
import com.yoghurt1131.weatherapi.domain.CurrentWeather;
import com.yoghurt1131.weatherapi.domain.Temperature;
import com.yoghurt1131.weatherapi.domain.Weather;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

public class WeatherApiServiceTest {

    @Mock
    ValueOperations<String, City> operations;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private RestTemplate restTemplate;

    @Mock
    private RedisTemplate redisTemplate;

    @InjectMocks
    WeatherApiService target = getTarget();

    private City sampleCity;

    protected WeatherApiService getTarget() {
        MockitoAnnotations.initMocks(this);
        WeatherApiService target = new WeatherApiService(restTemplate, redisTemplate);
        target.openWeatherApiUrl = "http://weatherapitest";
        target.apiKey = "key";
        return target;
    }

    @Before
    public void setup() {
        Weather weather = new Weather() {
            {
                this.setId(10000);
                this.setStatus("Clouds");
                this.setDescription("scattered clouds");
                this.setIcon("03n");
            }
        };
        Temperature temperature = new Temperature() {
            {
                this.setTemp(300.15);
            }
        };
        ArrayList<Weather> weathers = new ArrayList<>();
        weathers.add(weather);
        sampleCity = new City("Tokyo", weathers, temperature);
    }

    @Test
    public void testGetCurrentWeatherWithNoCache() throws ApiCallException {
        doReturn(operations).when(redisTemplate).opsForValue();
        doReturn(null).when(operations).get(any(String.class));

        ResponseEntity<City> sampleResponse = new ResponseEntity<City>(sampleCity, HttpStatus.OK);
        when(restTemplate.getForEntity(any(String.class), eq(City.class))).thenReturn(sampleResponse);

        CurrentWeather actual = target.getCurrentWeather("Tokyo");
        assertThat(actual.getCityName(), is("Tokyo"));
        assertThat(actual.getStatus(), is("Clouds"));
        assertThat(actual.getTemperature(), is(27.0));
    }
}
