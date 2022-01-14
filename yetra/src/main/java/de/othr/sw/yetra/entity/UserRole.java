package de.othr.sw.yetra.entity;

import de.othr.sw.yetra.entity.util.SingleIdEntity;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

//TODO: add to diagram
@Entity
public class UserRole extends SingleIdEntity<String> {
    @Id
    @Pattern(regexp = "^ROLE_.+")
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
