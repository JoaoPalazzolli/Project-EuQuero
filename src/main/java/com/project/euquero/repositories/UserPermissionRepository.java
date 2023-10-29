package com.project.euquero.repositories;

import com.project.euquero.models.UserPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserPermissionRepository extends JpaRepository<UserPermission, Long> {

    // void deleteUserPermissionByIdUserIdAndIdPermissionId(Long userId, Long permissionId);

}
