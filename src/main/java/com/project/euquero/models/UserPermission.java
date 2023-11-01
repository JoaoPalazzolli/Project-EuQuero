package com.project.euquero.models;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "user_permission")
public class UserPermission implements GrantedAuthority {

    @Serial
    private static final long serialVersionUID = 1L;

    @Autowired
    @EmbeddedId
    private UserPermissionPK id;

    @Column
    private LocalDateTime expireAt;

    @Column(name = "plano_ativado")
    private Boolean planoAtivado;

    @Override
    public String getAuthority() {
        return this.id.getPermission().getDescricao();
    }
}
