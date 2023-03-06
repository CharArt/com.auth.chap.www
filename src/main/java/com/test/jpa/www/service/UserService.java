package com.test.jpa.www.service;

import com.test.jpa.www.entity.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public interface UserService extends UserDetailsService {

    Users findUserById(Long id);

    Users findUserByLogin(String login);

    List<Users> findUserByNameAndSurnameAndPatronymic(String name, String surname, String patronymic);

    Users findUserByMail(String email);

    Users findUserByActivated(String activatedCode);

    List<Users> findUserByGender(String gender);

    Users findUserByPhone(String phone);

    List<Users> findUserByBirthday(Date birthday);

    List<Users> findUserByAge(int age);

    List<Users> findUserByEnable(boolean enable);

    List<Users> findUserByCreated(Timestamp created);

    Users getLastPerson();

    List<Users> findAll();

    boolean deleteUserById(Long id);

    boolean deleteUserByIdAndLogin(Long id, String login);

    boolean updateUser(Users user);

    boolean saveNewUser(Users user);

    boolean saveRoleForUser(Users user);

}
