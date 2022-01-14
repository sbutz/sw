package de.othr.sw.yetra.entity;

import de.othr.sw.yetra.entity.util.SingleIdEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;

@Entity
public class UserPrivilege extends SingleIdEntity<String> {
    @Id
    @Pattern(regexp = "^(?!ROLE_).+")
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
