package dev.yoghurt1131.weatherapi.extentions

import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun <T: Any> T.logger(): Logger = getLogger(javaClass)

private fun getLogger(forClass: Class<*>): Logger = LoggerFactory.getLogger(forClass)