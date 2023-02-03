
package com.abdulaziz.HeadHunterFinalProject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "roles", schema = "opa")
public class RoleEntity implements GrantedAuthority {

    @Id
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private RoleType role;
    public RoleEntity(RoleType role) {
        this.role = role;
    }

    @OneToMany(mappedBy = "role")
    private List<UserEntity> userEntity;
    @Override
    public String getAuthority() {
        return role.name();
    }
}

