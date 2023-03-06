package com.test.jpa.www.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class RoleServiceImplTest {

    private RoleService service;

    @Autowired
    public RoleServiceImplTest(RoleService service) {
        this.service = service;
    }

    @Test
    void getRoleByRoleNameTest (){
        System.out.println(service.getRoleByRoleName("ADMIN").getId());
    }

    @Test
    void getRoleByid(){
        System.out.println(service.getRoleById(1L).getRole());
    }
}