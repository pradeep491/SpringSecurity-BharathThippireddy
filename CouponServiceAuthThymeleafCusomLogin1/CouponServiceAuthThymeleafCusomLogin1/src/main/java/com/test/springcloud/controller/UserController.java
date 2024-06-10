package com.test.springcloud.controller;

import com.test.springcloud.model.Role;
import com.test.springcloud.model.User;
import com.test.springcloud.repo.UserRepo;
import com.test.springcloud.security.SecurityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;

@Controller
@Slf4j
public class UserController {

    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserRepo repo;
    @Autowired
    private PasswordEncoder encoder;

    @GetMapping("/showReg")
    public String showRegistrationPage() {
        return "registerUser";
    }

    @PostMapping("/registerUser")
    public String register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);
        return "login";
    }

    @GetMapping("/")
    public String showLoginPage() {
        log.info("control came for absolute path...!");
        return "login";
    }

    @PostMapping("/login")
    public String login(String email, String password, HttpServletRequest request, HttpServletResponse response) {
        boolean loginResponse = securityService.login(email, password, request, response);
        log.info("login response--->" + loginResponse);
        if (loginResponse) {
            return "index";
        }
        return "login";
    }
}
