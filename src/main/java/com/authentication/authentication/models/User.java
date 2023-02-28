package com.authentication.authentication.models;

import com.authentication.authentication.repositories.RoleRepository;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "users")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "role", referencedColumnName = "id", nullable = false)
    private Role roleId;
    @Column(nullable = false, name = "firstName")
    private String firstName;
    @Column(nullable = false, name = "lastName")
    private String lastName;
    @Column(nullable = false, name = "password")
    private String password;
    @Column(nullable = false, name = "email", unique = true)
    private String email;
    @Column( name = "emailValidated")
    private boolean emailValidated;
    @Column(nullable = false, name = "active")
    private boolean active;
    @Column(nullable = false, name = "address")
    private Integer addressId;







    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //should return a list of roles
        return List.of(new SimpleGrantedAuthority(roleId.getRoleName()));
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
