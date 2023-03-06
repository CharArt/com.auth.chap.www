package com.test.jpa.www.controllers;

import com.test.jpa.www.dto.UserDTO;
import com.test.jpa.www.entity.Users;
import com.test.jpa.www.service.UserService;
import com.test.jpa.www.tools.InverterTypes;
import com.test.jpa.www.validation.CustomUsersValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class RegistrationController {

    private final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    private final UserService userService;
    private final CustomUsersValidator myCustomUserValidation;


    @Autowired
    public RegistrationController(UserService userService, CustomUsersValidator myCustomUserValidation) {
        this.userService = userService;
        this.myCustomUserValidation = myCustomUserValidation;
    }

    @GetMapping("/registration")
    public String FrontPage(ModelMap modelMap) {
        UserDTO newUser = new UserDTO();
        modelMap.addAttribute("user", newUser);
        return "registration";
    }

    @PostMapping("/registration")
    public String checkUserInfo(@ModelAttribute("user") UserDTO user, BindingResult bindingResult, ModelMap modelMap) {
        logger.info("-Start_Method_checkUserInfo");
        user.setActivated(UUID.randomUUID().toString());
        myCustomUserValidation.validate(user, bindingResult);
        System.out.println(user.getActivated());
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.saveNewUser(InverterTypes.getUsers(user));
        return String.format("redirect:/login/%s", user.getActivated());
    }

    @GetMapping("activated/{code}")
    public String activate(ModelMap modelMap, @PathVariable String code) {
        Users users = userService.findUserByActivated(code);
        UserDTO userDTO = InverterTypes.getUsers(users);
        modelMap.addAttribute("user", userDTO);
        users.setEnable(true);
        userService.updateUser(users);
        return "activated";
    }
}
