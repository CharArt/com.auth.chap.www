package com.test.jpa.www.defaultEntity;

import com.test.jpa.www.entity.GoogleUser;

public class DefaultGoogleUser extends GoogleUser {

    public GoogleUser getDefaultGoogleUserForUserCharArtPav() {
        GoogleUser user = new GoogleUser();
        user.setId(1L);
        user.setUser_id(1L);
        user.setSub("1234567890987654321");
        user.setGiven_name("Artem");
        user.setFamily_name("Charykov");
        user.setEmail("charartpav@gmail.com");
        user.setEmail_verified(true);
        user.setLocale("Russia");
        return user;
    }

    public GoogleUser getDefaultGoogleUserForUserTest1() {
        GoogleUser user = new GoogleUser();
        user.setSub("9876543210123456789");
        user.setGiven_name("Test1");
        user.setFamily_name("Test1");
        user.setEmail("Test1@gmail.com");
        user.setEmail_verified(true);
        user.setLocale("Kazakhstan");
        user.setRoles(new DefaultRole().getRoleListUser());
        return user;
    }

    public GoogleUser getDefaultGoogleUserForUserTest2() {
        GoogleUser user = new GoogleUser();
        user.setUser_id(null);
        user.setSub("5432167890987612345");
        user.setGiven_name("Test2");
        user.setFamily_name("Test2");
        user.setEmail("Test2@gmail.com");
        user.setEmail_verified(true);
        user.setLocale("Spain");
        user.setRoles(new DefaultRole().getRoleListUser());
        return user;
    }

}
