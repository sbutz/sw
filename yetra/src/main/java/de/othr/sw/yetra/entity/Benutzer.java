package de.othr.sw.yetra.entity;

import de.othr.sw.yetra.entity.util.SingleIdEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Benutzer extends SingleIdEntity<Long> implements UserDetails {
    @Id
    @GeneratedValue
    protected long nr;

    @NotBlank
    @Column(unique=true)
    protected String nutzername;

    @NotBlank
    protected String passwort;

    public Benutzer() {
    }

    public Benutzer(String nutzername, String passwort) {
        this.nutzername = nutzername;
        this.passwort = passwort;
    }

    public String getNutzername() {
        return nutzername;
    }

    public void setNutzername(String nutzername) {
        this.nutzername = nutzername;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    @Override
    protected Long getID() {
        return this.getNr();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //TODO: implement
        return null;
    }

    public Long getNr() {
        return this.nr;
    }

    @Override
    public String getPassword() {
        return this.getPasswort();
    }

    @Override
    public String getUsername() {
        return this.getNutzername();
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
