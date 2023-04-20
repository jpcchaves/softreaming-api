package com.jpcchaves.softreaming.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SqlBadRequestException extends DataIntegrityViolationException {
    public SqlBadRequestException(String msg) {
        super(msg);
    }
}
