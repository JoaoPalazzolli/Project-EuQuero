package com.project.euquero.security.jwt;

import com.project.euquero.repositories.TokenRepository;
import com.project.euquero.security.jwt.service.JwtService;
import com.project.euquero.security.jwt.service.PacotePremiumService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PacotePremiumService pacotePremiumService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token;
        String email;

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        token = authHeader.substring("Bearer ".length());
        email = jwtService.extractEmail(token);

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null){

           var user = userDetailsService.loadUserByUsername(email);

           pacotePremiumService.verificarPacote(user); // verificar Expiracao do plano

           var isTokenValid = tokenRepository.findByAccessToken(token)
                   .map(x -> !x.isExpired() && !x.isRevoked())
                   .orElse(false);

           if (!isTokenValid)
               isTokenValid = tokenRepository.findByRefreshToken(token)
                       .map(x -> !x.isExpired() && !x.isRevoked())
                       .orElse(false);

           if (jwtService.isTokenValid(user, token) && isTokenValid){

               UsernamePasswordAuthenticationToken authToken =
                       new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

               authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

               SecurityContextHolder.getContext().setAuthentication(authToken);
           }

           filterChain.doFilter(request, response);
        }

    }
}
