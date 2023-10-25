package com.project.euquero.services.auth.authenticated;

import com.project.euquero.models.User;
import com.project.euquero.utils.ErrorMessages;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AuthenticatedUser {

    public static User getAuthenticatedUser(){
        try {
            return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e){
            throw new UsernameNotFoundException(ErrorMessages.USER_NOT_FOUND);
        }
    }
}
