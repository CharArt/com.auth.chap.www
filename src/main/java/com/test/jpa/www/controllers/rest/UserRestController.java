package com.test.jpa.www.controllers.rest;

import com.test.jpa.www.dto.UserDTO;
import com.test.jpa.www.entity.Users;
import com.test.jpa.www.service.UserService;
import com.test.jpa.www.tools.InverterTypes;
import com.test.jpa.www.validation.CustomUsersValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/user")
public class UserRestController {
    private final UserService userService;
    private final CustomUsersValidator validator;

    @Autowired
    public UserRestController(UserService userService,
                              CustomUsersValidator validator) {
        this.userService = userService;
        this.validator = validator;
    }

    @InitBinder
    protected void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(validator);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable Long id) {
        UserDTO userDTO = InverterTypes.getUsers(userService.findUserById(id));
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAllUserOrByLogin(@RequestParam(required = false) String login) {
        List<Users> list = new ArrayList<>();
        List<UserDTO> userDTOList = new ArrayList<>();
        if (login != null) {
            UserDTO userDTO = InverterTypes.getUsers(userService.findUserByLogin(login));
            userDTOList.add(userDTO);
        } else {
            list = userService.findAll();
            for (Users user : list) {
                userDTOList.add(InverterTypes.getUsers(user));
            }
        }
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    @GetMapping("/NSP")
    public ResponseEntity<List<UserDTO>> findUsersByNameAndSurnameAndPatronymic(@RequestParam String name, @RequestParam String surname, @RequestParam String patronymic) {
        List<Users> list = new ArrayList<>();
        List<UserDTO> userDTOList = new ArrayList<>();
        if (name.isEmpty() || surname.isEmpty() || patronymic.isEmpty()) {
            return new ResponseEntity<>(userDTOList, HttpStatus.BAD_REQUEST);
        }
        list = userService.findUserByNameAndSurnameAndPatronymic(name, surname, patronymic);
        for (Users user : list) {
            UserDTO userDTO = InverterTypes.getUsers(user);
            userDTOList.add(userDTO);
        }
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    @GetMapping("/Email")
    public ResponseEntity<UserDTO> findUserByEmail(@RequestParam String email) {
        String pattern = "\\b[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b";
        if (Pattern.matches(email, pattern) || email.isEmpty()) {
            return new ResponseEntity<>(new UserDTO(), HttpStatus.BAD_REQUEST);
        }
        UserDTO userDTO = InverterTypes.getUsers(userService.findUserByMail(email));
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/Gender")
    public ResponseEntity<List<UserDTO>> findUsersByGender(@RequestParam String gender) {
        List<Users> list = new ArrayList<>();
        List<UserDTO> userDTOList = new ArrayList<>();
        String pattern = "(fe)?male";
        if (Pattern.matches(gender, pattern)) {
            return new ResponseEntity<List<UserDTO>>(userDTOList, HttpStatus.BAD_REQUEST);
        }
        list = userService.findUserByGender(gender);
        for (Users user : list) {
            UserDTO userDTO = InverterTypes.getUsers(user);
            userDTOList.add(userDTO);
        }
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    @GetMapping("/Phone")
    public ResponseEntity<UserDTO> findUsersByPhone(@RequestParam String phone) {
        String pattern = "^\\d{10}$";
        if (Pattern.matches(phone, pattern)) {
            return new ResponseEntity<>(new UserDTO(), HttpStatus.BAD_REQUEST);
        }
        UserDTO userDTO = InverterTypes.getUsers(userService.findUserByPhone(phone));
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/Age")
    public ResponseEntity<List<UserDTO>> findUsersByAge(@RequestParam Integer age) {
        List<Users> list = new ArrayList<>();
        List<UserDTO> userDTOList = new ArrayList<>();
        if (age == 0) {
            return new ResponseEntity<>(userDTOList, HttpStatus.BAD_REQUEST);
        }
        list = userService.findUserByAge(age);
        for (Users user : list) {
            UserDTO userDTO = InverterTypes.getUsers(user);
            userDTOList.add(userDTO);
        }
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    @GetMapping("/Enable")
    public ResponseEntity<List<UserDTO>> findUsersByEnable(@RequestParam String enable) {
        List<Users> list = new ArrayList<>();
        List<UserDTO> userDTOList = new ArrayList<>();
        boolean flag = enable.equalsIgnoreCase("true");
        list = userService.findUserByEnable(flag);
        for (Users user : list) {
            UserDTO userDTO = InverterTypes.getUsers(user);
            userDTOList.add(userDTO);
        }
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    @PostMapping("/Save")
    public HttpStatus create(@RequestBody UserDTO userDTO, BindingResult result) {
        validator.validate(userDTO, result);
        if (result.hasErrors()) {
            return HttpStatus.BAD_REQUEST;
        }
        Users user = InverterTypes.getUsers(userDTO);
        userService.saveNewUser(user);
        userService.saveRoleForUser(user);

        return HttpStatus.OK;
    }

    @PutMapping("/Update")
    public HttpStatus update(@RequestBody UserDTO userDTO) {
        Users user = InverterTypes.getUsers(userDTO);
        user.setId(userDTO.getId());
        userService.updateUser(user);
        return HttpStatus.OK;
    }

    @DeleteMapping("/Delete")
    public HttpStatus deleteUserByIDAndLogin(@RequestParam Long id, @RequestParam String login) {
        if (id == null || login.isEmpty()) {
            return HttpStatus.BAD_REQUEST;
        }
        if (userService.findUserByLogin(login).isEmpty()) {
            return HttpStatus.BAD_REQUEST;
        }
        if (userService.findUserById(id).isEmpty()) {
            return HttpStatus.BAD_REQUEST;
        }
        userService.deleteUserByIdAndLogin(id, login);
        return HttpStatus.OK;
    }
}