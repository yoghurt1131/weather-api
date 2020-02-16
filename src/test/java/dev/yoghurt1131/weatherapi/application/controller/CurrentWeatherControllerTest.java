package dev.yoghurt1131.weatherapi.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.yoghurt1131.weatherapi.application.exception.ApiCallException;
import dev.yoghurt1131.weatherapi.application.service.WeatherApiServiceImpl;
import dev.yoghurt1131.weatherapi.controller.CurrentWeatherController;
import dev.yoghurt1131.weatherapi.domain.CurrentWeather;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CurrentWeatherControllerTest {

    @Mock
    WeatherApiServiceImpl weatherApiService;

    @InjectMocks
    CurrentWeatherController target;

    MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(target).build();
    }

    @Test
    public void testCityWithCityName() throws Exception {
        // setup
        CurrentWeather expected = new CurrentWeather("Tokyo", "sunny", 283.15);
        expected.setWeatherIconUrl("test://weathericonurl");
        doReturn(expected).when(weatherApiService).getCurrentWeather("Tokyo");

        // when
        MvcResult result= mockMvc.perform(get("/current/city")
                .param("cityName", "Tokyo")
        ).andExpect(status().isOk()).andReturn();

        // then
        CurrentWeather actual = new ObjectMapper().readValue(result.getResponse().getContentAsString(), CurrentWeather.class);
        assertThat(actual.getCityName(), is("Tokyo"));
        assertThat(actual.getStatus(), is("sunny"));
        assertThat(actual.getTemperature(), is(10.0));
        assertThat(actual.getWeatherIconUrl(), is("test://weathericonurl"));

        verify(weatherApiService, times(1)).getCurrentWeather("Tokyo");
    }

    @Test(expected = ApiCallException.class)
    public void testCityWhenApiExceptionHasThrowed() throws Throwable {
        // setup
        Mockito.doThrow(new ApiCallException("Dummy Exception.", new IOException("Dummy IOException"))).when(weatherApiService).getCurrentWeather("Tokyo");

        try {
            // when
            MvcResult result = mockMvc.perform(get("/current/city")
                    .param("cityName", "Tokyo")
            ).andExpect(status().isOk()).andReturn();
        } catch(Exception exception) {
            throw exception.getCause();
        }
    }
}
