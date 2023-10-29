package com.project.euquero.execptions;

import com.project.euquero.utils.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmailNotFoundException extends UsernameNotFoundException {

    public EmailNotFoundException(String email){
        super(String.format(ErrorMessages.EMAIL_NOT_FOUND, email));
    }
}
