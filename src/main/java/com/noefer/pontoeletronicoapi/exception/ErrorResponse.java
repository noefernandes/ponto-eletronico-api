package com.noefer.pontoeletronicoapi.exception;

import lombok.Builder;

@Builder
public class ErrorResponse {
    public String message;
    public int code;
}
