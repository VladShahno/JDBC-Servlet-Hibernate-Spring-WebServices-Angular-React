package com.nixsolutions.crudapp.controller;

import com.nixsolutions.crudapp.entity.Role;
import com.nixsolutions.crudapp.entity.User;
import com.nixsolutions.crudapp.exception.ValidationException;
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
        Date date = java.sql.Date.valueOf(req.getParameter("birthday"));
        Long roleId = Long.valueOf(Integer.parseInt(req.getParameter("role")));
        req.setAttribute("selectedRoleId", roleId);
        Role role;
        try {
            role = roleService.findById(roleId);
            userService.create(
                    new User(login, password, email, firstName, lastName, date,
                            role));
        } catch (ValidationException e) {
            if (e.getMessage().contains("PUBLIC.USER(EMAIL)")) {
                String emailError = "User with such email had been already created!";
                req.setAttribute("emailError", emailError);
                req.getRequestDispatcher("/WEB-INF/views/create_user.jsp")
                        .forward(req, resp);
                return;
            }
            String loginError = "User with such login had been already created!";
            req.setAttribute("loginError", loginError);
            req.getRequestDispatcher("/WEB-INF/views/create_user.jsp")
                    .forward(req, resp);
            return;
        }
        req.setAttribute("message", "User have add successfully!");
        req.getRequestDispatcher("/WEB-INF/views/create_user.jsp")
                .forward(req, resp);
    }
}
