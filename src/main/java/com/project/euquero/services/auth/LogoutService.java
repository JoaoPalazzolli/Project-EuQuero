package com.project.euquero.services.auth;

import com.project.euquero.execptionsHandler.InvalidJwtAuthenticationException;
import com.project.euquero.execptionsHandler.ResourceNotFoundException;
import com.project.euquero.repositories.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
public class LogoutService implements LogoutHandler {

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {

        String authHeader =  request.getHeader("Authorization");
        String token = authHeader.substring("Bearer ".length());

        if(!authHeader.startsWith("Bearer ")){
            throw new InvalidJwtAuthenticationException();
        }

        var storedToken = tokenRepository.findByAccessToken(token)
                .orElseThrow(ResourceNotFoundException::new);

        if(storedToken != null){
            storedToken.setRevoked(true);
            storedToken.setExpired(true);
            tokenRepository.save(storedToken);
        }
    }
}
