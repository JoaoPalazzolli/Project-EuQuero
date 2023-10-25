package com.project.euquero.services;

import com.project.euquero.dtos.UserDTO;
import com.project.euquero.dtos.security.RegisterRequestDTO;
import com.project.euquero.mapper.Mapper;
import com.project.euquero.models.Empresa;
import com.project.euquero.models.Permission;
import com.project.euquero.models.User;
import com.project.euquero.repositories.PermissionRepository;
import com.project.euquero.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    public ResponseEntity<UserDTO> create(RegisterRequestDTO registerRequestDTO){
        Permission permission = Permission.builder()
                .descricao("GOLD")
                .build();

        permissionRepository.save(permission);

        User user = User.builder()
                .firstName(registerRequestDTO.getFirstName())
                .lastName(registerRequestDTO.getLastName())
                .email(registerRequestDTO.getEmail())
                .password(registerRequestDTO.getPassword())
                .cpf(registerRequestDTO.getCpf())
                .phone(registerRequestDTO.getPhone())
                .permission(permission)
                .build();

        userRepository.save(user);

        return ResponseEntity.ok(Mapper.parseObject(user, UserDTO.class));
    }
}
