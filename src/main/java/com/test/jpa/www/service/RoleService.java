package com.test.jpa.www.service;

import com.test.jpa.www.entity.Roles;

public interface RoleService {

    public Roles getRoleByRoleName(String roleName);

    public Roles getRoleById(Long id);
}
