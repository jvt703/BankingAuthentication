package com.authentication.authentication.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Table(name = "user")
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Role role;
    @Column(nullable = false, name = "firstName")
    private String firstName;
    @Column(nullable = false, name = "password")
    private String password;

    public User() {
    }

    public User(Integer id, Role role, String firstName) {
        this.id = id;
        this.role = role;
        this.firstName = firstName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //should return a list of roles
        return List.of(new SimpleGrantedAuthority(role.getRoleName()));
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return firstName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
