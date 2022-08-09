package com.example.api.services.exceptions;

public class DataIntegretyViolationException extends RuntimeException{
    public DataIntegretyViolationException(String message) {
        super(message);
    }
}
