package de.othr.sw.yetra.entity;

import de.othr.sw.yetra.entity.util.SingleIdEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User extends SingleIdEntity<Long> implements UserDetails {
    @Id
    @GeneratedValue
    protected long id;

    @NotBlank
    @Column(unique=true)
    protected String username;

    @NotBlank
    protected String password;

    public User() {
    }

    public User(String username, String passwort) {
        this.username = username;
        this.password = passwort;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String nutzername) {
        this.username = nutzername;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String passwort) {
        this.password = passwort;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //TODO: implement
        return null;
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
