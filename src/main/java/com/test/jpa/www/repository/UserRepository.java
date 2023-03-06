package com.test.jpa.www.repository;

import com.test.jpa.www.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    @Query(value = "SELECT * FROM users " +
            "LEFT JOIN users_roles ON users.u_id = users_roles.users_id " +
            "LEFT JOIN roles  ON roles.r_id = users_roles.roles_id " +
            "WHERE users.u_id = :id", nativeQuery = true)
    Optional<Users> findUserById(@Param("id") Long user_id);

    @Query(value = "SELECT * FROM users " +
            "LEFT JOIN users_roles ON users.u_id = users_roles.users_id " +
            "LEFT JOIN roles ON roles.r_id = users_roles.roles_id " +
            "WHERE users.login = :login", nativeQuery = true)
    Optional<Users> findUserByLogin(@Param("login") String login);

    @Query(value = "SELECT * FROM users " +
            "LEFT JOIN users_roles  ON users.u_id = users_roles.users_id " +
            "LEFT JOIN roles  ON roles.r_id = users_roles.roles_id " +
            "WHERE users.name = :name AND users.surname = :surname AND users.Patronymic = :patronymic", nativeQuery = true)
    List<Users> findUserByNameAndSurnameAndPatronymic(@Param("name") String name, @Param("surname") String surname, @Param("patronymic") String patronymic);

    @Query(value = "SELECT * FROM users  " +
            "LEFT JOIN users_roles ON users.u_id = users_roles.users_id " +
            "LEFT JOIN roles ON roles.r_id = users_roles.roles_id " +
            "WHERE users.mail = :mail", nativeQuery = true)
    Optional<Users> findUserByMail(@Param("mail") String mail);

    @Query(value = "SELECT * FROM users " +
            "LEFT JOIN users_roles ON users.u_id = users_roles.users_id " +
            "LEFT JOIN roles ON roles.r_id = users_roles.roles_id " +
            "WHERE users.activated = :activated", nativeQuery = true)
    Optional<Users> findUserByActivated(@Param("activated") String activated);

    @Query(value = "SELECT * FROM users " +
            "LEFT JOIN users_roles ON users.u_id = users_roles.users_id " +
            "LEFT JOIN roles ON roles.r_id = users_roles.roles_id " +
            "WHERE users.gender = :gender", nativeQuery = true)
    List<Users> findUserByGender(@Param("gender") String gender);

    @Query(value = "SELECT * FROM users " +
            "LEFT JOIN users_roles ON users.u_id = users_roles.users_id " +
            "LEFT JOIN roles ON roles.r_id = users_roles.roles_id " +
            "WHERE users.phone = :phone", nativeQuery = true)
    Optional<Users> findUserByPhone(@Param("phone") String phone);

    @Query(value = "SELECT * FROM users " +
            "LEFT JOIN users_roles ON users.u_id = users_roles.users_id " +
            "LEFT JOIN roles ON roles.r_id = users_roles.roles_id " +
            "WHERE users.birthday = :birthday", nativeQuery = true)
    List<Users> findUserByBirthday(@Param("birthday") Date birthday);

    @Query(value = "SELECT * FROM users " +
            "LEFT JOIN users_roles ON users.u_id = users_roles.users_id " +
            "LEFT JOIN roles ON roles.r_id = users_roles.roles_id " +
            "WHERE users.age = :age", nativeQuery = true)
    List<Users> findUserByAge(@Param("age") int age);

    @Query(value = "SELECT * FROM users " +
            "LEFT JOIN users_roles ON users.u_id = users_roles.users_id " +
            "LEFT JOIN roles ON roles.r_id = users_roles.roles_id " +
            "WHERE users.enable = :enable", nativeQuery = true)
    List<Users> findUserByEnable(@Param("enable") boolean enable);

    @Query(value = "SELECT * FROM users " +
            "LEFT JOIN users_roles ON users.u_id = users_roles.users_id " +
            "LEFT JOIN roles ON roles.r_id = users_roles.roles_id " +
            "WHERE users.created = :created", nativeQuery = true)
    List<Users> findUserByCreated(@Param("created") Timestamp created);

    @Query(value = "SELECT TOP 1 * FROM users ORDER BY users.u_id DESC", nativeQuery = true)
    Users lastUser();

    @Query(value = "SELECT * FROM users " +
            "LEFT JOIN users_roles ON users.u_id = users_roles.users_id " +
            "LEFT JOIN roles ON roles.r_id = users_roles.roles_id ", nativeQuery = true)
    List<Users> findAll();

    @Modifying
    @Query(value = "DELETE FROM users WHERE users.u_id = :id", nativeQuery = true)
    void deleteUserById(@Param("id") Long id);

    @Modifying
    @Query(value = "DELETE FROM users WHERE users.u_id = :id AND users.login = :login", nativeQuery = true)
    void deleteUserByIdAndLogin(@Param("id") Long id, @Param("login") String login);

    @Modifying
    @Query(value = "UPDATE users SET users.login = :login, users.name = :name, users.surname = :surname, users.patronymic = :patronymic, " +
            " users.mail = :mail, users.activated = :activated, users.password = :password, users.gender = :gender, users.phone = :phone, " +
            " users.birthday = :birthday, users.age = :age, users.enable = :enable, users.created = :createdDate WHERE users.u_id = :id ", nativeQuery = true)
    void updateUserData(@Param("login") String login,
                        @Param("name") String name,
                        @Param("surname") String surname,
                        @Param("patronymic") String patronymic,
                        @Param("mail") String mail,
                        @Param("activated") String activated,
                        @Param("password") String password,
                        @Param("gender") String gender,
                        @Param("phone") String phone,
                        @Param("birthday") Date birthday,
                        @Param("age") int age,
                        @Param("enable") boolean enable,
                        @Param("createdDate") Timestamp createdDate,
                        @Param("id") Long id);

    @Modifying
    @Query(value = "INSERT INTO users (users.login, users.name, users.surname, users.patronymic, users.mail, users.activated, users.password, " +
            "users.gender, users.phone, users.birthday, users.age, users.enable, users.created) " +
            "VALUES (:login, :name, :surname, :patronymic, :mail, :activated, :password, :gender, :phone, :birthday, :age, :enable, " +
            ":createdDate) ;", nativeQuery = true)
    void saveUser(@Param("login") String login,
                  @Param("name") String name,
                  @Param("surname") String surname,
                  @Param("patronymic") String patronymic,
                  @Param("mail") String mail,
                  @Param("activated") String activated,
                  @Param("password") String password,
                  @Param("gender") String gender,
                  @Param("phone") String phone,
                  @Param("birthday") Date birthday,
                  @Param("age") int age,
                  @Param("enable") boolean enable,
                  @Param("createdDate") Timestamp createdDate);
    @Modifying
    @Query(value = "INSERT INTO users_roles (users_roles.users_id, users_roles.roles_id) VALUES(:user_id, :role_id) ;", nativeQuery = true)
    void saveRoleForUser(@Param("user_id") Long user_id, @Param("role_id") Long role_id);
}
