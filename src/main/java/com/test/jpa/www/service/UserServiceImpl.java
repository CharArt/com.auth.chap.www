package com.test.jpa.www.service;

import com.test.jpa.www.defaultEntity.DefaultTime;
import com.test.jpa.www.entity.Roles;
import com.test.jpa.www.entity.Users;
import com.test.jpa.www.repository.UserRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Users findUserById(Long id) {
        logger.info("Start_method_findUsersById(" + id + ")");
        return repository.findUserById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Users findUserByLogin(String login) {
        logger.info("Start_Method_findUsersByLogin(" + login + ")");
        return repository.findUserByLogin(login).orElse(new Users());
    }

    @Override
    public List<Users> findUserByNameAndSurnameAndPatronymic(String name, String surname, String patronymic) {
        logger.info("Start_Method_findUserByNameAndSurnameAndPatronymic(" + name + "," + surname + "," + patronymic + ")");
        List<Users> usersList = repository.findUserByNameAndSurnameAndPatronymic(name, surname, patronymic).stream().toList();
        if (usersList.isEmpty()) {
            logger.error("Exception_user_with_this_parameters_(" + name + "," + surname + "," + patronymic + ")_not_exist!");
            throw new EntityNotFoundException();
        }
        return usersList;
    }

    @Override
    public Users findUserByMail(String email) {
        logger.info("Start_Method_findUserByMail(" + email + ")");
        return repository.findUserByMail(email).orElse(new Users());
    }

    @Override
    public Users findUserByActivated(String activatedCode) {
        Users users = repository.findUserByActivated(activatedCode).orElse(new Users());
        return users;
    }

    @Override
    public List<Users> findUserByGender(String gender) {
        logger.info("Start_Method_findUserByGender(" + gender + ")");
        List<Users> usersList = repository.findUserByGender(gender);
        if (usersList.isEmpty()) {
            logger.error("Exception_user_with_this_parameters(" + gender + ")_not_exist!");
            throw new EntityNotFoundException();
        }
        return usersList;
    }

    @Override
    public Users findUserByPhone(String phone) {
        logger.info("Start_Method_findUserByPhone(" + phone + ")");
        return repository.findUserByPhone(phone).orElse(new Users());
    }

    @Override
    public List<Users> findUserByBirthday(Date birthday) {
        logger.info("Start_Method_findUserByBirthday(" + birthday + ")");
        List<Users> usersList = repository.findUserByBirthday(birthday).stream().toList();
        if (usersList.isEmpty()) {
            logger.error("Exception_user_with_this_parameters(" + birthday + ")_not_exist!");
            throw new EntityNotFoundException();
        }
        return usersList;
    }

    @Override
    public List<Users> findUserByAge(int age) {
        logger.info("Start_Method_findUserByAge(" + age + ")");
        List<Users> usersList = repository.findUserByAge(age).stream().toList();
        if (usersList.isEmpty()) {
            logger.error("Exception!_User_with_this_parameter(" + age + ")_not_exist!");
            throw new EntityNotFoundException();
        }
        return usersList;
    }

    @Override
    public List<Users> findUserByEnable(boolean enable) {
        logger.info("Start_Method_findUserByEnable(" + enable + ")");
        List<Users> usersList = repository.findUserByEnable(enable).stream().toList();
        if (usersList.isEmpty()) {
            logger.error("Exception!_User_with_this_parameter(" + enable + ")_not_exist!");
            throw new EntityNotFoundException();
        }
        return usersList;
    }

    @Override
    public List<Users> findUserByCreated(Timestamp created) {
        logger.info("Start_Method_findUserByCreated(" + created + ")");
        List<Users> usersList = repository.findUserByCreated(created).stream().toList();
        if (usersList.isEmpty()) {
            logger.error("Exception!_User_with_this_parameter_not_exist!");
            throw new EntityNotFoundException();
        }
        return usersList;
    }

    @Override
    public Users getLastPerson() {
        return repository.lastUser();
    }

    @Override
    public List<Users> findAll() {
        logger.info("Start_Method_findAll()");
        List<Users> usersList = repository.findAll();
        if (usersList.isEmpty()) {
            logger.error("Exception!_Database_is_empty!");
            throw new EntityNotFoundException();
        }
        return usersList;
    }

    @Override
    public boolean deleteUserById(Long id) {
        logger.info("Start_Method_deleteUserByIdAndLogin(" + id + ")");
        if (repository.findUserById(id).isPresent()) {
            repository.deleteUserById(id);
            return true;
        }
        logger.error("Exception!_User_with_this_parameter(" + id + ")_not_exist!");
        return false;
    }

    @Override
    public boolean deleteUserByIdAndLogin(Long id, String login) {
        logger.info("Start_Method_deleteUserByIdAndLogin(" + id + ", " + login + ")");
        if (repository.findUserById(id).isPresent()) {
            if (repository.findUserByLogin(login).isPresent()) {
                repository.deleteUserByIdAndLogin(id, login);
                logger.info("User_with_user_id:" + id + "_was_deleted");
                return true;
            }
            logger.error("Exception!_User_with_this_parameter(" + login + ")_not_exist!");
            return false;
        }
        return false;
    }

    @Override
    public boolean updateUser(Users user) {
        logger.info("Start_Method_deleteUserByIdAndLogin(user)");
        repository.updateUserData(user.getLogin(),
                user.getName(),
                user.getSurname(),
                user.getPatronymic(),
                user.getMail(),
                user.getActivated(),
                user.getPassword(),
                user.getGender(),
                user.getPhone(),
                user.getBirthday(),
                user.getAge(),
                user.isEnable(),
                user.getCreatedDate(),
                user.getId());
        Users users = repository.findUserByLogin(user.getLogin()).orElseThrow(EntityNotFoundException::new);
        return users.equals(user);
    }

    @Override
    public boolean saveNewUser(Users user) {
        logger.info("-Start_Method_deleteUserByIdAndLogin");

        LocalDate ld = new DefaultTime().getLocalDateInMyFormatNow();
        LocalDateTime ldt = new DefaultTime().getLocalDateTimeInMyFormatNow();

        user.setCreatedDate(Timestamp.valueOf(ldt));
        user.setAge(ld.getYear() - user.getBirthday().toLocalDate().getYear());
        if (repository.findUserByLogin(user.getLogin()).isEmpty()) {
            repository.saveUser(user.getLogin(),
                    user.getName(),
                    user.getSurname(),
                    user.getPatronymic(),
                    user.getMail(),
                    user.getActivated(),
                    user.getPassword(),
                    user.getGender(),
                    user.getPhone(),
                    user.getBirthday(),
                    user.getAge(),
                    user.isEnable(),
                    user.getCreatedDate());
        } else {
            logger.error("Standard_user_is_exist");
            throw new EntityExistsException();

        }
        Users users = repository.findUserByLogin(user.getLogin()).orElseThrow(EntityNotFoundException::new);
        return users.equals(user);
    }

    @Override
    public boolean saveRoleForUser(Users user) {
        logger.info("Start_Method_saveRoleForUser");
        if (!user.getRoles().isEmpty()) {
            for (Roles role : user.getRoles()) {
                repository.saveRoleForUser(user.getId(), role.getId());
            }
            return true;
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Users user = repository.findUserByLogin(username).orElseThrow(EntityNotFoundException::new);
        user.toString().length();/*Only for initialization inside proxy session*/
        return user;
    }
}
