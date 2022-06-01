package com.softarex.domas.questionnaire_portal.entity.user;


import com.softarex.domas.questionnaire_portal.entity.GeneralEntity;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Role extends GeneralEntity implements GrantedAuthority {
    private String roleName;

    public Role() {
    }

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public Role(long id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    @Override
    public String getAuthority() {
        return roleName;
    }

    public void setRoleName(String role) {
        this.roleName = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Role role = (Role) o;

        return id != null && id.equals(role.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "roleName = " + roleName + ")";
    }
}
