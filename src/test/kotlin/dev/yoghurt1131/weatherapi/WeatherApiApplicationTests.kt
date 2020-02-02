package dev.yoghurt1131.weatherapi;

import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

import org.hamcrest.core.Is.`is` as assertIs

@RunWith(SpringRunner::class)
@ActiveProfiles("local")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class WeatherApiApplicationTests {

	@Test
	fun testApplicationBoot() {
		assertThat(true, assertIs(true))
	}

}
