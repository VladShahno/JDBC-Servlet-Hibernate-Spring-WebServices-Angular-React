package com.nixsolutions.crudapp.controller;

import com.nixsolutions.crudapp.data.UserDto;
import com.nixsolutions.crudapp.entity.Role;
import com.nixsolutions.crudapp.entity.User;
import com.nixsolutions.crudapp.exception.FormProcessingException;
import com.nixsolutions.crudapp.exception.UserBirthdayException;
import com.nixsolutions.crudapp.exception.UserPasswordEqualsException;
import com.nixsolutions.crudapp.exception.UserWithEmailExistsException;
import com.nixsolutions.crudapp.exception.UserWithLoginExistsException;
import com.nixsolutions.crudapp.service.AuthenticationService;
import com.nixsolutions.crudapp.service.RoleService;
import com.nixsolutions.crudapp.service.UpdateUserService;
import com.nixsolutions.crudapp.service.UserService;
import com.nixsolutions.crudapp.util.UserFormValidator;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/users")
public class AdminController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UpdateUserService updateUserService;
    private final AuthenticationService authenticationService;

    @Autowired
    public AdminController(UserService userService,
            PasswordEncoder passwordEncoder, RoleService roleService,
            UpdateUserService updateUserService,
            AuthenticationService authenticationService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.updateUserService = updateUserService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/update")
    public String getUpdate(@RequestParam String login, Model model) {
        User user = userService.findByLogin(login);
        user.setPasswordConfirm(user.getPassword());
        model.addAttribute("user", user);
        List<Role> roles = roleService.findAll();
        model.addAttribute("roles", roles);
        Long roleIdFromDb = user.getRole().getId();
        model.addAttribute("selectedRoleId", roleIdFromDb);
        return "update";
    }

    @PostMapping("/update")
    public String postUpdate(@ModelAttribute("user") @Valid UserDto user,
            BindingResult bindingResult, Model model) {

        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("user", user);
                List<Role> roles = roleService.findAll();
                model.addAttribute("roles", roles);
                return "update";
            }
            try {
                updateUserService.update(user);
            } catch (UserWithEmailExistsException | UserBirthdayException
                    | UserPasswordEqualsException e) {
                UserFormValidator.validate(model, e);
            }
        } catch (FormProcessingException e) {
            model.addAttribute("user", user);
            List<Role> roles = roleService.findAll();
            model.addAttribute("roles", roles);
            return "update";
        }
        return "redirect: /home";
    }

    @GetMapping("/delete")
    public String postDelete(@RequestParam String login) {
        User user = userService.findByLogin(login);
        userService.remove(user);
        return "redirect: /home";
    }

    @GetMapping("/new")
    public String getCreate(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        List<Role> roles = roleService.findAll();
        model.addAttribute("roles", roles);
        return "new";
    }

    @PostMapping("/new")
    public String postCreate(@ModelAttribute("user") @Valid UserDto user,
            BindingResult bindingResult, Model model, @RequestParam Long role) {
        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("user", user);
                List<Role> roles = roleService.findAll();
                model.addAttribute("roles", roles);
                return "new";
            }
            try {
                user.setRole(role);
                authenticationService.register(userService.convert(user));
            } catch (UserWithEmailExistsException | UserWithLoginExistsException
                    | UserBirthdayException | UserPasswordEqualsException e) {
                UserFormValidator.validate(model, e);
            }
        } catch (FormProcessingException e) {
            model.addAttribute("user", user);
            List<Role> roles = roleService.findAll();
            model.addAttribute("roles", roles);
            return "new";
        }
        return "redirect: /home";
    }
}