package com.yoghurt1131.weatherapi.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoghurt1131.weatherapi.application.service.WeatherApiService;
import com.yoghurt1131.weatherapi.domain.CurrentWeather;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CurrentWeatherControllerTest {

    @Mock
    WeatherApiService weatherApiService;

    @InjectMocks
    CurrentWeatherController target;

    MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(target).build();
    }

    @Test
    public void testCurrentWeatherControllerWithCityName() throws Exception {
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

    }
}
