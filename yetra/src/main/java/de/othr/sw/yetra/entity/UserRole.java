package de.othr.sw.yetra.entity;

import de.othr.sw.yetra.entity.util.SingleIdEntity;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.thymeleaf.expression.Strings;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Entity
public class UserRole extends SingleIdEntity<String> {

    @Id
    @Column(length = 64)
    @Pattern(regexp = "^ROLE_.+")
    @Size(min = 6, max = 64)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade(CascadeType.PERSIST)
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

    public void setName(String name) {
        this.name = name;
    }

    public Set<UserPrivilege> getPrivileges() {
        return this.privileges;
    }

    public void setPrivileges(Set<UserPrivilege> privileges) {
        this.privileges.clear();
        this.privileges.addAll(privileges);
    }

    @Override
    protected String getId() {
        return this.name;
    }

    public String getHumanReadableName() {
        Strings util = new Strings(Locale.ENGLISH);
        String s = this.getName()
                .substring(5)
                .replace('_', ' ')
                .toLowerCase();
        return util.capitalizeWords(s);
    }
}
