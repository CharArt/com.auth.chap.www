package com.test.jpa.www.service;

import com.test.jpa.www.entity.GoogleUser;

import java.util.List;

public interface GoogleUserService {

    public GoogleUser findGoogleUserById(Long id);

    public GoogleUser findUserBySub(String sub);

    public GoogleUser findUserByNameAndSurnameAndEmail(String name, String surname, String email);

    public List<GoogleUser> findAll();

    boolean deleteGoogleUserById(Long id);

    boolean saveNewGoogleUser(GoogleUser googleUser);

    boolean saveRolesForGoogleUsers(GoogleUser googleUser);
}
