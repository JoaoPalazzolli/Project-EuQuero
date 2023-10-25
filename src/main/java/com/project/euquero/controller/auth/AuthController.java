package com.project.euquero.controller.auth;

import com.project.euquero.dtos.auth.LoginRequestDTO;
import com.project.euquero.dtos.auth.RegisterRequestDTO;
import com.project.euquero.dtos.auth.TokenDTO;
import com.project.euquero.services.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(value = "/register")
    public ResponseEntity<TokenDTO> register(@RequestBody RegisterRequestDTO request){
        return authService.register(request);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginRequestDTO request){
        return authService.login(request);
    }

    @PutMapping(value = "/refresh")
    public ResponseEntity<TokenDTO> refreshToken(@RequestHeader("Authorization") String refreshToken){
        return authService.refreshToken(refreshToken);
    }
}
