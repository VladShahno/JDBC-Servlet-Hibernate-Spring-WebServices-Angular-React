package com.nixsolutions.crudapp.util;

import com.nixsolutions.crudapp.entity.Role;
import com.nixsolutions.crudapp.entity.User;
import com.nixsolutions.crudapp.service.RoleService;
import com.nixsolutions.crudapp.service.impl.RoleServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.spec.RSAOtherPrimeInfo;
import java.sql.Date;
import java.util.List;

public class UserAttributesUtil {

    private static RoleService roleService = new RoleServiceImpl();

    public static User getUserWithAttributes(HttpServletRequest req) {

        String login = req.getParameter("login");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        Date date = Date.valueOf(req.getParameter("birthday"));
        Long roleId = Long.valueOf((req.getParameter("role")));

        User user = new User();
        user.setLogin(login);
        user.setEmail(email);
        user.setLastName(lastName);
        user.setFirstName(firstName);
        user.setPassword(password);
        user.setBirthday(date);
        Role role;
        role = roleService.findById(roleId);
        user.setRole(role);
        List<Role> roles = roleService.findAll();
        Long roleIdFromDb = user.getRole().getId();
        req.setAttribute("roles", roles);
        req.setAttribute("selectedRoleId", roleIdFromDb);
        return user;
    }

    public static void setDateError(HttpServletRequest req) {
        String dateError = "Date is incorrect, please enter the right date!";
        req.setAttribute("dateError", dateError);
    }

    public static void setEmailError(HttpServletRequest req) {
        String emailError = "User with such email had been already created!";
        req.setAttribute("emailError", emailError);
    }

    public static void setLoginError(HttpServletRequest req) {
        String loginError = "User with such login had been already created!";
        req.setAttribute("loginError", loginError);
    }

    public static List<Role> setRolesAttribute(HttpServletRequest req) {
        List<Role> roles = roleService.findAll();
        req.setAttribute("roles", roles);
        return roles;
    }

    public static void setSelectedRoleId(HttpServletRequest req, User user) {
        Long roleIdFromDb = user.getRole().getId();
        req.setAttribute("selectedRoleId", roleIdFromDb);
    }
}