package com.test.jpa.www.tools;

import com.test.jpa.www.dto.GoogleUserDTO;
import com.test.jpa.www.dto.RoleDTO;
import com.test.jpa.www.dto.UserDTO;
import com.test.jpa.www.entity.GoogleUser;
import com.test.jpa.www.entity.Roles;
import com.test.jpa.www.entity.Users;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InverterTypes {

    public static Users getUsers(UserDTO userDTO) {
        Users user = new Users();
        user.setLogin(userDTO.getLogin());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setPatronymic(userDTO.getPatronymic());
        user.setMail(userDTO.getMail());
        user.setActivated(userDTO.getActivated());
        user.setPassword(userDTO.getPassword());
        user.setGender(userDTO.getGender());
        user.setPhone(userDTO.getPhone());
        user.setBirthday(userDTO.getBirthday());
        user.setAge(userDTO.getAge());
        user.setEnable(userDTO.isEnable());
        user.setCreatedDate(userDTO.getCreatedDate());
        user.setRoles(getRoleList(userDTO.getRoleDTOList()));
        return user;
    }

    public static UserDTO getUsers(Users user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setLogin(user.getLogin());
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setPatronymic(user.getPatronymic());
        userDTO.setMail(user.getMail());
        userDTO.setActivated(user.getActivated());
        userDTO.setPassword(user.getPassword());
        userDTO.setGender(user.getGender());
        userDTO.setPhone(user.getPhone());
        userDTO.setBirthday(user.getBirthday());
        userDTO.setAge(user.getAge());
        userDTO.setEnable(user.isEnable());
        userDTO.setCreatedDate(user.getCreatedDate());
        userDTO.setRoleDTOList(getRoleDTOList(user.getRoles()));
        return userDTO;
    }

    public static RoleDTO getRole(Roles roles) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(roles.getId());
        roleDTO.setRole(roles.getRole());
        return roleDTO;
    }

    public static Roles getRole(RoleDTO roleDTO) {
        Roles roles = new Roles();
        roles.setId(roleDTO.getId());
        roles.setRole(roleDTO.getRole());
        return roles;
    }

    public static List<RoleDTO> getRoleDTOList(List<Roles> rolesList) {
        List<RoleDTO> roleDTOList = new ArrayList<>();
        if (!rolesList.isEmpty()) {
            for (Roles roles : rolesList) {
                roleDTOList.add(getRole(roles));
            }
        }
        return roleDTOList;
    }

    public static List<Roles> getRoleList(List<RoleDTO> rolesList) {
        List<Roles> roleList = new ArrayList<>();
        if (!rolesList.isEmpty()) {
            for (RoleDTO roleDTO : rolesList) {
                roleList.add(getRole(roleDTO));
            }
        }
        return roleList;
    }

    public static GoogleUser getGoogleUser(OAuth2User oAuth2User) {
        GoogleUser googleUser = new GoogleUser();
        if (!Objects.equals(oAuth2User, null)) {
            googleUser.setSub(oAuth2User.getAttribute("sub"));
            googleUser.setGiven_name(oAuth2User.getAttribute("given_name"));
            googleUser.setFamily_name(oAuth2User.getAttribute("family_name"));
            googleUser.setEmail(oAuth2User.getAttribute("email"));
            googleUser.setEmail_verified(Boolean.TRUE.equals(oAuth2User.getAttribute("email_verified")));
            googleUser.setLocale(oAuth2User.getAttribute("locale"));
        }
        return googleUser;
    }

    public static GoogleUser getGoogleUser(GoogleUserDTO googleUserDTO) {
        GoogleUser googleUser = new GoogleUser();
        if (!Objects.equals(googleUserDTO, null)) {
            googleUser.setSub(googleUserDTO.getAttribute("sub"));
            googleUser.setGiven_name(googleUserDTO.getAttribute("given_name"));
            googleUser.setFamily_name(googleUserDTO.getAttribute("family_name"));
            googleUser.setEmail(googleUserDTO.getAttribute("email"));
            googleUser.setEmail_verified(Boolean.TRUE.equals(googleUserDTO.getAttribute("email_verified")));
            googleUser.setLocale(googleUserDTO.getAttribute("locale"));
        }
        return googleUser;
    }
}
