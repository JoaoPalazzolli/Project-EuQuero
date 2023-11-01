package com.project.euquero.repositories;

import com.project.euquero.models.UserPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserPermissionRepository extends JpaRepository<UserPermission, Long> {


    @Modifying
    @Query("""
            delete from UserPermission us where us.id.user.id = :userId and us.id.permission.id = :permissionId
            """)
    void deleteUserPermission(@Param("userId") Long userId, @Param("permissionId") Long permissionId);

    Boolean existsByIdUserIdAndIdPermissionId(Long userId, Long premissionId);

    @Query("""
            select case when (count(us) > 0) then true else false end
            from UserPermission us where us.id.user.id = :userId
            and (us.planoAtivado = true)
            """)
    Boolean existsUserPermissionPremiumAtivado(@Param("userId") Long userId);

    @Query("""
            select us from UserPermission us where us.id.user.id = :userId and (us.planoAtivado = true)
            """)
    Optional<UserPermission> findUserPermissionPremiumAtivado(@Param("userId") Long userId);

}
