package com.test.jpa.www.service;

import com.test.jpa.www.defaultEntity.DefaultRole;
import com.test.jpa.www.defaultEntity.DefaultUser;
import com.test.jpa.www.entity.Roles;
import com.test.jpa.www.entity.Users;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class UserServiceImplTest {
    @Autowired
    public UserService service;

    @Test
    void findUsersByIdTest() {
        System.out.println(service.findUserById(1L).toString());
    }

    @Test
    void findUserByLoginTest() {
        System.out.println(service.findUserByLogin("CharArtPav").toString());
    }

    @Test
    void findUserByNameAndSurnameAndPatronymicTest() {
        System.out.println(service.findUserByNameAndSurnameAndPatronymic("Artoym", "Charykov", "Pavlovich").toString());
    }

    @Test
    void findUserByMailTest() {
        System.out.println(service.findUserByMail("charartpav@gmail.com").toString());
    }

    @Test
    void findUserByActivatedTest() {
        System.out.println(service.findUserByActivated("$2a$12$eQzSz4d7jN4yr/gZQNlcSOGj9BkmBRd/ZhylbsDjFtQFfP4DIhF8C").toString());
    }

    @Test
    void findUserByGenderTest() {
        List<Users> usersList = service.findUserByGender("male");
        for (Users u : usersList) {
            System.out.println(u.toString());
        }
    }

    @Test
    void findUserByPhoneTest() {
        System.out.println(service.findUserByPhone("89015891274"));
    }

    @Test
    void findUserByBirthdayTest() {
        List<Users> usersList = service.findUserByBirthday(new DefaultUser().getUserCharArtPav().getBirthday());
        for (Users u : usersList) {
            System.out.println(u.getBirthday().toString());
            System.out.println(u.toString());
        }
    }

    @Test
    void findUserByAgeTest() {
        List<Users> usersList = service.findUserByAge(29);
        for (Users u : usersList) {
            System.out.println(u.toString());
        }
    }

    @Test
    void findUserByEnableTest() {
        List<Users> usersList = service.findUserByEnable(false);
        for (Users u : usersList) {
            System.out.println(u.toString());
        }
    }

//    @Test
//    void findUserByCreatedTest() {
//        List<Users> usersList = service.findUserByCreated(new DefaultUser().getUserCharArtPav().getCreatedDate());
//        for (Users u : usersList) {
//            System.out.println(u.toString());
//        }
//    }

    @Test
    void getLastPersonTest() {
        System.out.println(service.getLastPerson().toString());
    }

    @Test
    void deleteUserByIdAndFindAllTest() {
        List<Users> usersList = service.findAll();
        System.out.println(usersList.size());
        service.deleteUserById(1L);
        usersList = service.findAll();
        System.out.println(usersList.size());
    }

    @Test
    void deleteUserByIdAndLoginAndFindAllTest() {
        List<Users> usersList = service.findAll();
        System.out.println(usersList.size());
        service.deleteUserByIdAndLogin(1L, "CharArtPav");
        usersList = service.findAll();
        System.out.println(usersList.size());
    }

    @Test
    void updateUserTest() {
        Users users = service.findUserById(1L);
        System.out.println(users.getLogin());
        users.setLogin("HugeGod");
        service.updateUser(users);
        System.out.println(users.getLogin());
    }

    @Test
    void saveNewUserTestAndSaveRoleForUserTest() {
        DefaultUser defaultUser = new DefaultUser().getUserTest1();
        defaultUser.setRoles(new DefaultRole().getRoleListUser());
        service.saveNewUser(defaultUser);
        service.saveRoleForUser(defaultUser);
        System.out.println(service.getLastPerson().toString());
        for (Roles role : service.getLastPerson().getRoles()) {
            System.out.println(role.getRole());
        }
    }
}