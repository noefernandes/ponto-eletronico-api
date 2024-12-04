package com.noefer.pontoeletronicoapi.exception;

public class WorkDayNotFoundException extends RuntimeException {
    public WorkDayNotFoundException(String message) {
        super(message);
    }
}
