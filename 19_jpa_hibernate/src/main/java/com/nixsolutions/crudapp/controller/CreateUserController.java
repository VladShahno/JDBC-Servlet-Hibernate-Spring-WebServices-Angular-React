package com.nixsolutions.crudapp.controller;

import com.nixsolutions.crudapp.entity.Role;
import com.nixsolutions.crudapp.entity.User;
import com.nixsolutions.crudapp.service.RoleService;
import com.nixsolutions.crudapp.service.UserService;
import com.nixsolutions.crudapp.service.impl.RoleServiceImpl;
import com.nixsolutions.crudapp.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/new")
public class CreateUserController extends HttpServlet {

    private UserService userService;

    private RoleService roleService;

    public CreateUserController() {
        userService = new UserServiceImpl();
        roleService = new RoleServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Role> roles = roleService.findAll();
        req.setAttribute("roles", roles);
        req.getRequestDispatcher("/WEB-INF/views/create_user.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

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

        if (userService.findByLogin(login).getLogin() != null) {
            String loginError = "User with such login had been already created!";
            req.setAttribute("loginError", loginError);
            req.setAttribute("user", user);
            req.setAttribute("roles", roles);
            req.setAttribute("selectedRoleId", roleIdFromDb);
            req.getRequestDispatcher("/WEB-INF/views/create_user.jsp")
                    .forward(req, resp);
            return;
        } else if (userService.findByEmail(email).getEmail() != null) {
            String emailError = "User with such email had been already created!";
            req.setAttribute("emailError", emailError);
            req.setAttribute("user", user);
            req.setAttribute("roles", roles);
            req.setAttribute("selectedRoleId", roleIdFromDb);
            req.getRequestDispatcher("/WEB-INF/views/create_user.jsp")
                    .forward(req, resp);
            return;
        } else if (date == null || date.before(Date.valueOf("1900-01-01"))
                || date.after(new Date(new java.util.Date().getTime()))) {
            String dateError = "Date is incorrect, please enter the right date!";
            req.setAttribute("dateError", dateError);
            req.setAttribute("user", user);
            req.setAttribute("roles", roles);
            req.setAttribute("selectedRoleId", roleIdFromDb);
            req.getRequestDispatcher("/WEB-INF/views/create_user.jsp")
                    .forward(req, resp);
            return;
        }
        userService.create(user);
        req.setAttribute("message", "You have registered successfully!");
        req.getRequestDispatcher("/WEB-INF/views/create_user.jsp")
                .forward(req, resp);
    }
}