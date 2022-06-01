package com.softarex.domas.questionnaire_portal.entity.user;


import com.softarex.domas.questionnaire_portal.entity.GeneralEntity;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "users")
public class User extends GeneralEntity {
    @Column(unique = true)
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String phoneNumber;
    @ManyToMany
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    public User() {
    }

    public User(String email, String firstName, String lastName, String password, String phoneNumber, Collection<Role> roles) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
    }

    public User(long id, String email, String firstName, String lastName, String password, String phoneNumber, Collection<Role> roles) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String secondName) {
        this.lastName = secondName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String mobilePhone) {
        this.phoneNumber = mobilePhone;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;

        return id != null && id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "email = " + email + ", " +
                "firstName = " + firstName + ", " +
                "lastName = " + lastName + ", " +
                "password = " + password + ", " +
                "phoneNumber = " + phoneNumber + ")";
    }
}
