package com.project.euquero.services;

import com.project.euquero.execptions.ConflictException;
import com.project.euquero.models.UserPermission;
import com.project.euquero.models.UserPermissionPK;
import com.project.euquero.repositories.PermissionRepository;
import com.project.euquero.repositories.UserPermissionRepository;
import com.project.euquero.services.auth.authenticated.AuthenticatedUser;
import com.project.euquero.utils.ErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@Service
public class PlanoService {

    private static final Logger LOGGER = Logger.getLogger(PlanoService.class.getName());

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private UserPermissionRepository userPermissionRepository;

    @Transactional
    public ResponseEntity<?> comprarPlano(Long permissionId) throws Exception{

        // não permitir se o permissionId for de "USER" ou "ADMIN" --

        LOGGER.info("Comprando Plano");

        var user = AuthenticatedUser.getAuthenticatedUser();

        var permission = permissionRepository.findById(permissionId)
                .orElseThrow();

        if (userPermissionRepository.existsByIdUserIdAndIdPermissionId(user.getId(), permissionId))
            throw new ConflictException(ErrorMessages.PERMISSION_CONFLICT);

        if (userPermissionRepository.existsUserPermissionPremiumAtivado(user.getId()))
            throw new Exception(ErrorMessages.ALREADY_HAS_A_PLAN);

        var userPermission = UserPermission.builder()
                .id(UserPermissionPK.builder()
                        .user(user)
                        .permission(permission)
                        .build())
                .planoAtivado(true)
                .build();

        if (permission.getDescricao().equalsIgnoreCase("prata")){
            userPermission.setExpireAt(LocalDateTime.now().plusMonths(1));
        } else if (permission.getDescricao().equalsIgnoreCase("ouro")) {
            userPermission.setExpireAt(LocalDateTime.now().plusMonths(3));
        } else {
            userPermission.setExpireAt(LocalDateTime.now().plusMonths(12));
        }

        userPermissionRepository.save(userPermission);

        LOGGER.info("Compra de Plano Efetuada com Sucesso");

        return ResponseEntity.noContent().build();
    }
}
