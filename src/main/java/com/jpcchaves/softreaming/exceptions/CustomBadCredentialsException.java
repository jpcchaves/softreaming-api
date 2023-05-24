package com.jpcchaves.softreaming.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomBadCredentialsException extends BadCredentialsException {
    public CustomBadCredentialsException(String msg) {
        super(msg);
    }
}
