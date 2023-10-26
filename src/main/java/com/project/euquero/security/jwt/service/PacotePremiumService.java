package com.project.euquero.security.jwt.service;

import com.project.euquero.models.User;
import com.project.euquero.repositories.PermissionRepository;
import com.project.euquero.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PacotePremiumService {

    // verificar se n expirou

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    public void verificarPacote(UserDetails userDetails){



    }
}
