package com.project.euquero.security.jwt.service;

import com.project.euquero.models.User;
import com.project.euquero.repositories.UserPermissionRepository;
import com.project.euquero.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class PacotePremiumService {

    // verificar se n expirou

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPermissionRepository userPermissionRepository;

    public void verificarPlano(UserDetails userDetails) {

        var user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();

        // teste
        user.getPermissions().forEach(x -> {
            if (x.getId().getPermission().getDescricao().equals("OURO") && x.getExpireAt().isBefore(LocalDateTime.now())){

            }
        });
    }
}
