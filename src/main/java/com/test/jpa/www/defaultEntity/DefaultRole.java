package com.test.jpa.www.defaultEntity;

import com.test.jpa.www.entity.Roles;

import java.util.ArrayList;
import java.util.List;

public class DefaultRole extends Roles {
    private List<Roles> rolesList = new ArrayList<>();

    public DefaultRole() {
    }

    public Roles getRoleAdmin() {
        Roles role = new Roles();
        role.setId(1L);
        role.setRole("ADMIN");
        return role;
    }

    public Roles getRoleUser() {
        DefaultRole role = new DefaultRole();
        role.setId(2L);
        role.setRole("USER");
        return role;
    }

    public List<Roles> getRoleListAdmin() {
        this.rolesList.add(getRoleAdmin());
        return this.rolesList;
    }

    public List<Roles> getRoleListUser() {
        this.rolesList.add(getRoleUser());
        return this.rolesList;
    }

    public List<Roles> getRoleListAdminAndUser() {
        this.rolesList.add(getRoleAdmin());
        this.rolesList.add(getRoleUser());
        return this.rolesList;
    }
}
