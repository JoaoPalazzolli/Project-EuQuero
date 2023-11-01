package com.project.euquero.security.jwt.service;

import com.project.euquero.models.User;
import com.project.euquero.repositories.PermissionRepository;
import com.project.euquero.repositories.UserPermissionRepository;
import com.project.euquero.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PacotePremiumService {

    // verificar se n expirou

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPermissionRepository userPermissionRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Transactional
    public Boolean isPlanValid(UserDetails userDetails) {

        var user = (User) userDetails;

        if(!userPermissionRepository.existsUserPermissionPremiumAtivado(user.getId()))
            return true;

        var userPermission = userPermissionRepository.findUserPermissionPremiumAtivado(user.getId())
                .orElseThrow();

        if (userPermission.getExpireAt().isBefore(LocalDateTime.now())) {
            userPermissionRepository.deleteUserPermission(
                    userPermission.getId().getPermission().getId(),
                    userPermission.getId().getPermission().getId());

            return false;
        } else {
            return true;
        }
    }
}
