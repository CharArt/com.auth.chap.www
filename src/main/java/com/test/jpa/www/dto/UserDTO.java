package com.test.jpa.www.dto;

import com.test.jpa.www.defaultEntity.DefaultTime;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class UserDTO {

    private Long id;

    private String login;

    private String name;

    private String surname;

    private String patronymic;

    private String password;

    private String gender;

    private String phone;

    private String mail;

    private String activated;

    private Date birthday;

    private int age;

    private boolean enable;

    private Timestamp createdDate;

    private List<RoleDTO> roleDTOList;

    public UserDTO(String login, String name, String surname, String patronymic,
                   String password, String gender, String phone,
                   String mail, String activated, Date birthday, boolean enable) {

        DefaultTime defaultTime = new DefaultTime();
        LocalDate ld = defaultTime.getLocalDateInMyFormatNow();
        LocalDateTime ldt = defaultTime.getLocalDateTimeInMyFormatNow();

        Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder("secret", 64, 400000, Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA512);
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.password = encoder.encode(password);
        this.gender = gender;
        this.phone = phone;
        this.mail = mail;
        this.activated = activated;
        this.birthday = birthday;
        this.age = ld.getYear() - this.birthday.toLocalDate().getYear();
        this.enable = enable;
        this.createdDate = Timestamp.valueOf(ldt);
    }

    public UserDTO() {
    }


    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (patronymic != null ? patronymic.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO)) return false;

        UserDTO user = (UserDTO) o;

        if (!Objects.equals(login, user.login)) return false;
        if (!Objects.equals(name, user.name)) return false;
        if (!Objects.equals(surname, user.surname)) return false;
        if (!Objects.equals(patronymic, user.patronymic)) return false;
        return this.hashCode() == user.hashCode();
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "{", "}");
        joiner.add(this.name);
        joiner.add(this.surname);
        joiner.add(this.patronymic);
        joiner.add(this.mail);
        joiner.add(this.gender);
        joiner.add(this.phone);
        joiner.add(Integer.toString(this.age));
        joiner.add(this.activated);
        if (!this.roleDTOList.isEmpty()) {
            for (RoleDTO roleDTO : this.roleDTOList) {
                joiner.add(roleDTO.getId().toString());
                joiner.add(roleDTO.getRole());
            }
        }
        return joiner.toString();
    }

    public boolean isEmpty() {
        return this.getLogin() == null || this.getName() == null || this.getSurname() == null || this.getMail() == null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getActivated() {
        return activated;
    }

    public void setActivated(String activated) {
        this.activated = activated;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public List<RoleDTO> getRoleDTOList() {
        return roleDTOList;
    }

    public void setRoleDTOList(List<RoleDTO> roleDTOList) {
        this.roleDTOList = roleDTOList;
    }
}
