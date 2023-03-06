package com.test.jpa.www.service;

import com.test.jpa.www.defaultEntity.DefaultRole;

import com.test.jpa.www.entity.GoogleUser;
import com.test.jpa.www.entity.Roles;
import com.test.jpa.www.entity.Users;
import com.test.jpa.www.repository.GoogleUserRepository;
import com.test.jpa.www.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
public class GoogleUserServiceImpl implements GoogleUserService {
    private final Logger logger = LoggerFactory.getLogger(GoogleUserServiceImpl.class);
    private final GoogleUserRepository googleUserRepository;
    private final UserRepository userRepository;

    @Autowired
    public GoogleUserServiceImpl(GoogleUserRepository googleUserRepository, UserRepository userRepository) {
        this.googleUserRepository = googleUserRepository;
        this.userRepository = userRepository;
    }

    @Override
    public GoogleUser findGoogleUserById(Long id) {
        logger.info("Start_Method_findUserById(" + id + ")");
        return googleUserRepository.findGoogleUserById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public GoogleUser findUserBySub(String sub) {
        logger.info("Start_Method_findUserBySub(" + sub + ")");
        return googleUserRepository.findGoogleUserBySub(sub).orElse(new GoogleUser());
    }

    @Override
    public GoogleUser findUserByNameAndSurnameAndEmail(String name, String surname, String email) {
        logger.info("Start_Method_findUserByNameAndSurnameAndEmail(" + name + ", " + surname + ", " + email + ")");
        return googleUserRepository.findGoogleUserByNameAndSurnameAndEmail(name, surname, email).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<GoogleUser> findAll() {
        logger.info("Start_Method_findAll()");
        return googleUserRepository.findAll();
    }

    @Override
    public boolean deleteGoogleUserById(Long id) {
        logger.info("Start_Method_deleteGoogleUserById(" + id + ")");
        if (googleUserRepository.findGoogleUserById(id).isPresent()) {
            googleUserRepository.deleteGoogleUserById(id);
            return true;
        } else {
            logger.error("User_with_this_(" + id + ")_not_exist");
            return false;
        }
    }

    @Override
    public boolean saveNewGoogleUser(GoogleUser googleUser) {
        logger.info("Start_Method_saveNewGoogleUser(" + googleUser.getSub() + ")");
        if (googleUserRepository.findGoogleUserBySub(googleUser.getSub()).isPresent()) {
            logger.error("User_with_this_(" + googleUser.getSub() + ")_already_exist");
            return false;
        } else {
            googleUserRepository.saveNewGoogleUsers(googleUser.getUser_id(),
                    googleUser.getSub(),
                    googleUser.getGiven_name(),
                    googleUser.getFamily_name(),
                    googleUser.getEmail(),
                    googleUser.isEmail_verified(),
                    googleUser.getLocale());
            GoogleUser googleUserNew = findUserBySub(googleUser.getSub());
            return googleUser.equals(googleUserNew);
        }
    }

    @Override
    public boolean saveRolesForGoogleUsers(GoogleUser googleUser) {
        logger.info("Start_Method_saveRolesForGoogleUsers(" + googleUser.getId() + ")");
        Users user = userRepository.findUserById(googleUser.getUser_id()).orElse(new Users());
        if (user.isEmpty()) {
            googleUser.getId();/*Only for initialization inside proxy session*/
            googleUserRepository.saveRoleForGoogleUser(googleUser.getId(), new DefaultRole().getRoleUser().getId());
            logger.info("Standard_user_is_not_exist");
            return true;
        } else if (!user.isEmpty()) {
            for (Roles role : user.getRoles()) {
                googleUserRepository.saveRoleForGoogleUser(googleUser.getId(), role.getId());
                logger.info("Standard_user_is_exist");
            }
            return true;
        } else {
            logger.error("Something_went_wrong");
            return false;
        }
    }
}
