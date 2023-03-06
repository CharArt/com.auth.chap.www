package com.test.jpa.www.controllers;

import com.test.jpa.www.dto.UserDTO;
import com.test.jpa.www.service.UserService;
import com.test.jpa.www.tools.InverterTypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.Map;


@Controller
public class LoginController {

    private final UserService service;

    @Autowired
    public LoginController(UserService service) {
        this.service = service;
    }

    @GetMapping("/login")
    public String loginGoogle(ModelMap model) {
        Map<String, String> clientUrls = new HashMap<>();
        clientUrls.put("Google", "http://localhost:8080/www/oauth2/authorization/google");
        model.addAttribute("clientUrls", clientUrls);
        return "/login";
    }

    @GetMapping("loginPage/{code}")
    public String afterRegistration(ModelMap modelMap, @PathVariable String code) {
        UserDTO userDTO = InverterTypes.getUsers(service.findUserByActivated(code));
        modelMap.addAttribute("message",
                userDTO.getName() + " " + userDTO.getPatronymic() + " Activate your mail, the link has been sent to the specified mail");
        return "loginPage";
    }
}
