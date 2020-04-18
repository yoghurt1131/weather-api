package dev.yoghurt1131.weatherapi.extentions

import org.springframework.http.ResponseEntity

fun ResponseEntity<*>.loggingResposne() {
    val logger = logger()
    logger.info("Response Status Code: $statusCode")
    logger.info("Response Body: ${body ?: "null"}")
}