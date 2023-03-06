package com.test.jpa.www.tools;

import com.test.jpa.www.defaultEntity.DefaultRole;
import com.test.jpa.www.dto.GoogleUserDTO;
import com.test.jpa.www.entity.GoogleUser;
import com.test.jpa.www.entity.Roles;
import com.test.jpa.www.entity.Users;
import com.test.jpa.www.service.GoogleUserServiceImpl;
import com.test.jpa.www.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Component
@Transactional
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final GoogleUserServiceImpl googleService;
    private final UserService userService;

    private final Logger logger = LoggerFactory.getLogger(OAuth2LoginSuccessHandler.class);

    @Autowired
    public OAuth2LoginSuccessHandler(GoogleUserServiceImpl googleService,
                                     UserService userService) {
        this.googleService = googleService;
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        GoogleUserDTO googleUserDTO = (GoogleUserDTO) authentication.getPrincipal();
        Users users = userService.findUserByMail(googleUserDTO.getEmail());
        GoogleUser googleUser = googleService.findUserBySub(googleUserDTO.getSub());

        if (!users.isEmpty() && !googleUser.isEmpty()) {
            logger.info("user already exists!");

            System.out.println("user already exists!");
        } else if (!users.isEmpty() && googleUser.isEmpty()) {
            logger.info("User first time entered with google email");

            GoogleUser userGoogle = InverterTypes.getGoogleUser(googleUserDTO);
            userGoogle.setUser_id(users.getId());
            userGoogle.setUser(users);
            userGoogle.setRoles(users.getRoles());
            googleService.saveNewGoogleUser(userGoogle);
            userGoogle = googleService.findUserBySub(googleUserDTO.getSub());
            for (Roles role : users.getRoles()) {
                googleService.saveRolesForGoogleUsers(userGoogle);
            }

        } else if (users.isEmpty() && googleUser.isEmpty()) {
            logger.info("User first time become used our site");

            Users user = new Users();
            user.setLogin(googleUserDTO.getGiven_name() + googleUserDTO.getFamily_name() + googleUserDTO.getSub().substring(0, 2));
            user.setMail(googleUserDTO.getEmail());
            user.setName(googleUserDTO.getGiven_name());
            user.setSurname(googleUserDTO.getFamily_name());
            user.setRoles(new DefaultRole().getRoleListUser());
            userService.saveNewUser(users);
            googleService.saveNewGoogleUser(googleUser);
            googleUser = googleService.findUserBySub(googleUser.getSub());
            for (Roles role : users.getRoles()) {
                googleService.saveRolesForGoogleUsers(googleUser);
            }
        }

        new DefaultRedirectStrategy().sendRedirect(request, response, "/hello");
        super.onAuthenticationSuccess(request, response, authentication);
    }


}
