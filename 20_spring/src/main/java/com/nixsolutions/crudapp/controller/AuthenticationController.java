package com.nixsolutions.crudapp.controller;

import com.nixsolutions.crudapp.entity.Role;
import com.nixsolutions.crudapp.entity.User;
import com.nixsolutions.crudapp.exception.FormProcessingException;
import com.nixsolutions.crudapp.exception.UserPasswordEqualsException;
import com.nixsolutions.crudapp.exception.UserWithEmailExistsException;
import com.nixsolutions.crudapp.exception.UserWithLoginExistsException;
import com.nixsolutions.crudapp.exception.UserBirthdayException;
import com.nixsolutions.crudapp.service.AuthenticationService;
import com.nixsolutions.crudapp.service.CaptchaService;
import com.nixsolutions.crudapp.service.RoleService;
import com.nixsolutions.crudapp.service.UserService;
import com.nixsolutions.crudapp.util.UserFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Controller
public class AuthenticationController {

    private AuthenticationService authenticationService;
    private final CaptchaService captchaService;
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService,
            CaptchaService captchaService, UserService userService,
            RoleService roleService) {
        this.authenticationService = authenticationService;
        this.captchaService = captchaService;
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/registration")
    public String getSignUp(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String postSignUp(@ModelAttribute("user") @Valid User user,
            BindingResult bindingResult, Model model, HttpServletRequest req) {

        String gRecaptchaResponse = req.getParameter("g-recaptcha-response");
        try {
            boolean verify = captchaService.verify(gRecaptchaResponse);
            if (!verify) {
                model.addAttribute("captchaError", "Please enter the captcha!");
                throw new FormProcessingException("captcha does not match!");
            }
            if (bindingResult.hasErrors()) {
                model.addAttribute("user", user);
                List<Role> roles = roleService.findAll();
                model.addAttribute("roles", roles);
                return "registration";
            }
            try {
                user.setRole(roleService.findByName("USER"));
                authenticationService.register(user);
            } catch (UserWithEmailExistsException | UserWithLoginExistsException
                    | UserBirthdayException | UserPasswordEqualsException e) {
                UserFormValidator.validate(model, e);
            }
        } catch (FormProcessingException e) {
            model.addAttribute("user", user);
            return "registration";
        }
        return "redirect: /home";
    }

    @GetMapping("/login")
    public String getSingIn(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model, HttpServletRequest req, HttpServletResponse resp) {
        String errorMessage = null;
        if (error != null) {
            errorMessage = "Username or Password is incorrect!";
        }
        if (logout != null) {
            errorMessage = "You have been successfully logged out!";
            Authentication auth = SecurityContextHolder.getContext()
                    .getAuthentication();
            if (auth != null) {
                new SecurityContextLogoutHandler().logout(req, resp, auth);
            }
        }
        model.addAttribute("errorMessage", errorMessage);
        return "login";
    }

    @PostMapping("/login")
    public String postSingIn() {
        return "login";
    }
}