package de.othr.sw.yetra.entity;

import de.othr.sw.yetra.entity.util.SingleIdEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;

@Entity
public class User extends SingleIdEntity<Long> implements UserDetails {
    @Id
    @GeneratedValue
    protected long id;

    @NotBlank
    @Column(unique=true)
    protected String username;

    @NotBlank
    protected String password;

    @ManyToOne
    @NotNull
    private UserRole role;

    @Embedded
    @NotNull
    @Valid
    private BankAccount bankAccount;

    private String notifyChannel;

    public User() {
    }

    public User(String username, String passwort, UserRole role) {
        this.username = username;
        this.password = passwort;
        this.role = role;
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getNotifyChannel() {
        return notifyChannel;
    }

    public void setNotifyChannel(String notifyChannel) {
        this.notifyChannel = notifyChannel;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean hasAuthority(String name) {
        for (GrantedAuthority a : this.getAuthorities())
            if (a.getAuthority().equals(name))
                return true;
        return false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(this.role.getName()));
        for (UserPrivilege p : this.role.getPrivileges())
            authorities.add(new SimpleGrantedAuthority(p.getName()));
        return authorities;
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
