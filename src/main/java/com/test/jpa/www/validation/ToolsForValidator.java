package com.test.jpa.www.validation;

import com.test.jpa.www.service.UserService;
import org.springframework.validation.Errors;

public interface ToolsForValidator {
    boolean checkLogin(String login, Errors errors, UserService service);

    boolean checkEmail(String email, Errors errors, UserService service);

    boolean checkPhone(String phone, Errors errors, UserService service);
}
