package com.armoury.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class WeaponNotAvailableException extends RuntimeException {
    public WeaponNotAvailableException(String message) {
        super(message);
    }
}
