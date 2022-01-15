package de.othr.sw.yetra.entity;

import de.othr.sw.yetra.entity.util.SingleIdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class UserPrivilege extends SingleIdEntity<String> {
    @Id
    @Column(length = 64)
    @Pattern(regexp = "^(?!ROLE_).+")
    @Size(max=64)
    private String name;

    public UserPrivilege() {

    }

    public UserPrivilege(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    protected String getId() {
        return this.name;
    }
}
