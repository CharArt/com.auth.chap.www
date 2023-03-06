package com.test.jpa.www.service;

import com.test.jpa.www.entity.Roles;
import com.test.jpa.www.repository.RolesRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RolesRepository repository;

    @Autowired
    public RoleServiceImpl(RolesRepository repository) {
        this.repository = repository;
    }

    @Override
    public Roles getRoleByRoleName(String roleName) {
        return repository.getRoleByName(roleName);
    }

    @Override
    public Roles getRoleById(Long id) {
        return repository.getRoleById(id);
    }
}
