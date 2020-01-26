package dev.yoghurt1131.weatherapi.application.exception;

public class ApiCallException extends Exception {

    public ApiCallException(String message, Throwable cause) {
        super(message, cause);
    }
}
