package com.project.euquero.execptionsHandler;

import com.project.euquero.utils.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidJwtAuthenticationException extends AuthenticationException {

    public InvalidJwtAuthenticationException() {
        super(ErrorMessages.INVALID_TOKEN);
    }

    public InvalidJwtAuthenticationException(String msg) {
        super(msg);
    }
}
