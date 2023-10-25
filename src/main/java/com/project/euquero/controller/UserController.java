package com.project.euquero.controller;

import com.project.euquero.dtos.UserDTO;
import com.project.euquero.dtos.security.RegisterRequestDTO;
import com.project.euquero.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserServices userServices;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserDTO> create(@RequestBody RegisterRequestDTO registerRequestDTO){
        return userServices.create(registerRequestDTO);
    }
}
