package com.project.euquero.repositories;

import com.project.euquero.models.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Optional<Permission> findByDescricao(String descricao);
}
