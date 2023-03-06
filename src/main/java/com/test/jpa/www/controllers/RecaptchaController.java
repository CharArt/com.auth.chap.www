package com.test.jpa.www.controllers;

import com.test.jpa.www.dto.CaptchaResponseDTO;
import com.test.jpa.www.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Controller
public class RecaptchaController {
    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response";

    private final RestTemplate restTemplate;
    private final UserService userService;

    @Value("${recaptcha.secret.key}")
    private String secret;

    @Autowired
    public RecaptchaController(RestTemplate restTemplate, UserService userService) {
        this.restTemplate = restTemplate;
        this.userService = userService;
    }

    @PostMapping("/login")
    public String loginPage(ModelMap model, @RequestParam("g-recaptcha-response") String captchaResponse) {
        String url = String.format(CAPTCHA_URL, secret, captchaResponse);
        CaptchaResponseDTO response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDTO.class);
        if (!response.isSuccess()) {
            model.addAttribute("captchaError, Fill captcha");
        }
        return "login";
    }

    @PostMapping("/reg")
    public String regRecapPage(ModelMap model, @RequestParam("g-recaptcha-response") String captchaResponse) {
        String url = String.format(CAPTCHA_URL, secret, captchaResponse);
        CaptchaResponseDTO response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDTO.class);
        if (!response.isSuccess()) {
            model.addAttribute("captchaError, Fill captcha");
        }
        return "registration";
    }
}
