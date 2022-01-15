package de.othr.sw.yetra.entity;

import de.othr.sw.yetra.entity.util.SingleIdEntity;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class UserRole extends SingleIdEntity<String> {
    @Id
    @Column(length = 64)
    @Pattern(regexp = "^ROLE_.+")
    @Size(min=6,max=64)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<UserPrivilege> privileges;

    public UserRole() {

    }

    public UserRole(String name) {
        this.name = name;
        this.privileges = new HashSet<>();
    }

    public String getName() {
        return this.name;
    }

    public Set<UserPrivilege> getPrivileges() {
        return this.privileges;
    }

    public void addPrivileges(Collection<UserPrivilege> privileges) {
        this.privileges.addAll(privileges);
    }

    @Override
    protected String getId() {
        return this.name;
    }
}
