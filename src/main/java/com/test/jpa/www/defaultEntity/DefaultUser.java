package com.test.jpa.www.defaultEntity;
import com.test.jpa.www.entity.Users;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class DefaultUser extends Users {

    public static DefaultTime defaultTime = new DefaultTime();
    public DefaultUser() {
    }

    public DefaultUser getUserCharArtPav() {
        LocalDate ld = defaultTime.getLocalDateInMyFormat("2023-01-29 20:48:38.1866667");
        LocalDateTime ldt = defaultTime.getLocalDateTimeInMyFormat("2023-01-29 20:48:38.1866667");

        this.setId(1L);
        this.setLogin("CharArtPav");
        this.setName("Artem");
        this.setSurname("Charykov");
        this.setPatronymic("Pavlovich");
        this.setMail("ArtPavChar@gmail.com");
        this.setPassword("b71bc7bb8dd860a6ec7ad8496a0af73c9c78aebc2926ef3adfcc899599b31bd66618709da6cc39e16f0870d94c164e9f67a9ba642e18ab500eed8e3dd9b5c6a87103d6d847ebc9fe1b28137123a37065e5f70532dd7d9ab8561b4cbb4432a70f9d09844245827444f8c0f855e02689894de1a6aac5f55e92f434cb8cf2fc1769");
        this.setGender("male");
        this.setPhone("89015891274");
        this.setBirthday(Date.valueOf("1993-02-26"));
        this.setAge(ld.getYear() - this.getBirthday().toLocalDate().getYear());
        this.setEnable(true);
        this.setCreatedDate(Timestamp.valueOf(ldt));
        this.setRoles(new DefaultRole().getRoleListAdmin());
        return this;
    }


    public DefaultUser getUserTest1() {
        LocalDate ld = defaultTime.getLocalDateInMyFormat("2023-01-29 20:48:38.1866667");
        LocalDateTime ldt = defaultTime.getLocalDateTimeInMyFormat("2023-01-29 20:48:38.1866667");

        this.setLogin("Test1");
        this.setName("Test1");
        this.setSurname("Test1");
        this.setPatronymic("Test1");
        this.setMail("Test1@gmail.com");
        this.setActivated("54634cf5-dc86-34e1-9445-495c11b04741");
        this.setPassword("b71bc7bb8dd860a6ec7ad8496a0af73c9c78aebc2926ef3adfcc899599b31bd66618709da6cc39e16f0870d94c164e9f67a9ba642e18ab500eed8e3dd9b5c6a87103d6d847ebc9fe1b28137123a37065e5f70532dd7d9ab8561b4cbb4432a70f9d09844245827444f8c0f855e02689894de1a6aac5f55e92f434cb8cf2fc1769");
        this.setGender("male");
        this.setPhone("00000000001");
        this.setBirthday(Date.valueOf("2001-01-01"));
        this.setAge(ld.getYear() - this.getBirthday().toLocalDate().getYear());
        this.setEnable(true);
        this.setCreatedDate(Timestamp.valueOf(ldt));
        this.setRoles(new DefaultRole().getRoleListAdmin());
        return this;
    }
}
