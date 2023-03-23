package com.test.jpa.www.service;

import com.test.jpa.www.defaultEntity.DefaultGoogleUser;
import com.test.jpa.www.entity.GoogleUser;
import com.test.jpa.www.entity.Roles;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class GoogleUserServiceImplTest {

    private GoogleUserService service;

    @Autowired
    public GoogleUserServiceImplTest(GoogleUserService service) {
        this.service = service;
    }

    @Test
    void findGoogleUserByIdTest() {
        System.out.println(service.findGoogleUserById(20L).toString());
    }

    @Test
    void findUserByNameAndSurnameAndEmailTest() {
        System.out.println(service.findUserByNameAndSurnameAndEmail("Artoym", "Charykov", "charartpav@gmail.com"));
    }

    @Test
    void findAllTest() {
        for (GoogleUser user : service.findAll()) {
            System.out.println(user.toString());
        }
    }

    @Test
    void deleteGoogleUserById() {
        System.out.println(service.findAll().size());
        service.deleteGoogleUserById(1L);
        System.out.println(service.findAll().size());
    }

    @Test
    void saveNewGoogleUserAndSaveRolesForGoogleUsersTest() {
        System.out.println(service.findAll().size());
        GoogleUser user = new DefaultGoogleUser().getDefaultGoogleUserForUserTest2();
        service.saveNewGoogleUser(user);
        service.saveRolesForGoogleUsers(user);
        for (Roles r : user.getRoles()) {
            System.out.println(r.getRole());
        }
        System.out.println(service.findAll().size());
    }
}