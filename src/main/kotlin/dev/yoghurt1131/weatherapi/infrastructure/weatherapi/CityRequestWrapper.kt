package dev.yoghurt1131.weatherapi.infrastructure.weatherapi

import dev.yoghurt1131.weatherapi.application.exception.ApiCallException
import dev.yoghurt1131.weatherapi.domain.entity.City
import dev.yoghurt1131.weatherapi.extentions.logger
import dev.yoghurt1131.weatherapi.extentions.loggingResposne
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

/**
 * API Request Wrapper
 * T: Response Type
 */
abstract class CityRequestWrapper<T>(
        private val apiUrl: String,
        private val apiKey: String,
        private val restTemplate: RestTemplate,
        private val type: Class<T>
) {

    private val logger = logger()

    fun execute(city: City): T {
        try {
            logger.info("Start Calling API:$apiUrl")
            val responseEntity = restTemplate.getForEntity(buildRequestUrl(city), type)
            logger.info("End Calling API:$apiUrl")
            responseEntity.loggingResposne()
            return responseEntity.body
        } catch (exception: RestClientException) {
            logger.info("Error Calling API. Message:" + exception.message)
            throw ApiCallException(exception.message, exception.cause)
        }
    }

    private fun buildRequestUrl(city: City) = UriComponentsBuilder
            .fromUriString(apiUrl)
            .queryParam("q", city.name)
            .queryParam("APPID", apiKey)
            .build().toUriString()
}