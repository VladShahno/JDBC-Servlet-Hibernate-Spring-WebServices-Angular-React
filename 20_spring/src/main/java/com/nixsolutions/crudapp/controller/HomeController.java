package com.nixsolutions.crudapp.controller;

import com.nixsolutions.crudapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    private final UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String getHome(Authentication authentication, Model model) {
        for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
            String login = SecurityContextHolder.getContext()
                    .getAuthentication().getName();
            String name = userService.findByLogin(login).getFirstName();
            model.addAttribute("role", authentication.getAuthorities());
            model.addAttribute("name", name);
            if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                model.addAttribute("users", userService.findAll());

                return "admin_home";
            }
        }
        model.addAttribute("message",
                "Hello, " + authentication.getName() + "!");
        return "user_home";

    }
}