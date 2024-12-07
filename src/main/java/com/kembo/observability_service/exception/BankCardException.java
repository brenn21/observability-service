package com.kembo.observability_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BankCardException extends Throwable {
    public BankCardException(String message) {
    }
}
