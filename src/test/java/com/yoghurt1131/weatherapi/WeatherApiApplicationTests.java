package com.yoghurt1131.weatherapi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@ActiveProfiles("local")
@SpringBootTest(classes = WeatherApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class WeatherApiApplicationTests {

	@Test
	public void testApplicationBoot() {
		assertThat(true, is(true));
	}

}
