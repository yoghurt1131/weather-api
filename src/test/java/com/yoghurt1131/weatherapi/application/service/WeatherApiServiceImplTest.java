package com.yoghurt1131.weatherapi.application.service;

import com.yoghurt1131.weatherapi.application.exception.ApiCallException;
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
import org.springframework.data.redis.RedisConnectionFailureException;
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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class WeatherApiServiceImplTest {

    @Mock
    ValueOperations<String, City> operations;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private RestTemplate restTemplate;

    @Mock
    private RedisTemplate redisTemplate;

    @InjectMocks
    WeatherApiServiceImpl target = getTarget();

    private City cityResponse;
    private City cityCache;

    protected WeatherApiServiceImpl getTarget() {
        MockitoAnnotations.initMocks(this);
        WeatherApiServiceImpl target = new WeatherApiServiceImpl(restTemplate, redisTemplate);
        target.openWeatherApiUrl = "http://weatherapitest";
        target.apiKey = "key";
        return target;
    }

    @Before
    public void setup() {
        // Api Response
        Weather weatherResponse = new Weather() {
            {
                this.setId(10000);
                this.setStatus("Clouds");
                this.setDescription("scattered clouds");
                this.setIcon("03n");
            }
        };
        Temperature tempResponse= new Temperature() {
            {
                this.setTemp(300.15);
            }
        };
        ArrayList<Weather> weathersResponse = new ArrayList<>();
        weathersResponse.add(weatherResponse);
        cityResponse = new City("Tokyo", weathersResponse, tempResponse);

        // Redis cache Response
        Weather weatherCache = new Weather() {
            {
                this.setId(10001);
                this.setStatus("Clear");
                this.setDescription("clear sky");
                this.setIcon("01n");
            }
        };
        Temperature tempCache= new Temperature() {
            {
                this.setTemp(295.15);
            }
        };
        ArrayList<Weather> weathersCache = new ArrayList<>();
        weathersCache.add(weatherCache);
        cityCache = new City("Tokyo", weathersCache, tempCache);
    }

    @Test
    public void testGetCurrentWeatherWithNoCache() throws ApiCallException {
        doReturn(operations).when(redisTemplate).opsForValue();
        doReturn(null).when(operations).get(any(String.class));

        ResponseEntity<City> expectedResponse = new ResponseEntity<City>(cityResponse, HttpStatus.OK);
        when(restTemplate.getForEntity(any(String.class), eq(City.class))).thenReturn(expectedResponse);

        CurrentWeather actual = target.getCurrentWeather("Tokyo");
        assertThat(actual.getCityName(), is("Tokyo"));
        assertThat(actual.getStatus(), is("Clouds"));
        assertThat(actual.getTemperature(), is(27.0));
    }

    @Test
    public void testGetCurrentWeatherWithCache() throws ApiCallException {
        doReturn(operations).when(redisTemplate).opsForValue();
        doReturn(cityCache).when(operations).get(any(String.class));

        ResponseEntity<City> expectedResponse = new ResponseEntity<City>(cityResponse, HttpStatus.OK);
        when(restTemplate.getForEntity(any(String.class), eq(City.class))).thenReturn(expectedResponse);

        CurrentWeather actual = target.getCurrentWeather("Tokyo");
        assertThat(actual.getCityName(), is("Tokyo"));
        assertThat(actual.getStatus(), is("Clear"));
        assertThat(actual.getTemperature(), is(22.0));
    }

    @Test
    public void testGetCurrentWeatherWhenCacheHasError() throws ApiCallException {
        doThrow(new RedisConnectionFailureException("Failed to check negative case.")).when(redisTemplate).opsForValue();

        ResponseEntity<City> expectedResponse = new ResponseEntity<City>(cityResponse, HttpStatus.OK);
        when(restTemplate.getForEntity(any(String.class), eq(City.class))).thenReturn(expectedResponse);

        CurrentWeather actual = target.getCurrentWeather("Tokyo");
        assertThat(actual.getCityName(), is("Tokyo"));
        assertThat(actual.getStatus(), is("Clouds"));
        assertThat(actual.getTemperature(), is(27.0));
    }
}
