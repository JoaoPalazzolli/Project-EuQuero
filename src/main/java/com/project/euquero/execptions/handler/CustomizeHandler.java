package com.project.euquero.execptions.handler;

import com.project.euquero.execptions.EmailNotFoundException;
import com.project.euquero.execptions.InvalidJwtAuthenticationException;
import com.project.euquero.execptions.ResourceNotFoundException;
import com.project.euquero.execptions.ResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CustomizeHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseException> allExceptionHandler(WebRequest request, Exception ex){

        var responseException = new ResponseException(ex.getMessage(), LocalDateTime.now(),
                request.getDescription(false));

        return new ResponseEntity<>(responseException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ResponseException> emailNotFoundExceptionHandler(WebRequest request, Exception ex){

        var responseException = new ResponseException(ex.getMessage(), LocalDateTime.now(),
                request.getDescription(false));

        return new ResponseEntity<>(responseException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidJwtAuthenticationException.class)
    public ResponseEntity<ResponseException> InvalidJwtAuthenticationExceptionHandler(WebRequest request, Exception ex){

        var responseException = new ResponseException(ex.getMessage(), LocalDateTime.now(),
                request.getDescription(false));

        return new ResponseEntity<>(responseException, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseException> ResourceNotFoundExceptionHandler(WebRequest request, Exception ex){

        var responseException = new ResponseException(ex.getMessage(), LocalDateTime.now(),
                request.getDescription(false));

        return new ResponseEntity<>(responseException, HttpStatus.NOT_FOUND);
    }
}
