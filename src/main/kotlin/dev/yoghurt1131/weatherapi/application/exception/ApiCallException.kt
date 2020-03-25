package dev.yoghurt1131.weatherapi.application.exception

import java.lang.Exception

class ApiCallException(override val message: String?, val cuause: Throwable?) : Exception(message) {
}