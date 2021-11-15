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

@WebServlet("/users/update")
public class UpdateUserController extends HttpServlet {

    private RoleService roleService;
    private UserService userService;

    public UpdateUserController() {
        userService = new UserServiceImpl();
        roleService = new RoleServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = userService.findByLogin(req.getParameter("login"));
        req.setAttribute("user", user);
        List<Role> roles = roleService.findAll();
        req.setAttribute("roles", roles);
        Long roleIdFromDb = user.getRole().getId();
        req.setAttribute("selectedRoleId", roleIdFromDb);
        req.getRequestDispatcher("/WEB-INF/views/update_user.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String login = req.getParameter("login");
        User user = userService.findByLogin(login);
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        Date date = Date.valueOf(req.getParameter("birthday"));
        Long roleId = Long.valueOf((req.getParameter("role")));
        Role role;
        user.setLastName(lastName);
        user.setFirstName(firstName);
        if (password.length() > 0) {
            user.setPassword(password);
        }
        user.setBirthday(date);
        role = roleService.findById(roleId);
        user.setRole(role);
        List<Role> roles = roleService.findAll();
        Long roleIdFromDb = user.getRole().getId();
        if ((email.equals(user.getEmail())
                | userService.findByEmail(email) == null)) {
            if (date == null || date.before(Date.valueOf("1900-01-01"))
                    || date.after(new Date(new java.util.Date().getTime()))) {
                String dateError = "Date is incorrect, please enter the right date!";
                req.setAttribute("dateError", dateError);
                req.setAttribute("user", user);
                user.setEmail(email);
                req.setAttribute("roles", roles);
                req.setAttribute("selectedRoleId", roleIdFromDb);
                req.getRequestDispatcher("/WEB-INF/views/update_user.jsp")
                        .forward(req, resp);
                return;
            }
            user.setEmail(email);
            userService.update(user);
            resp.sendRedirect(getServletContext().getContextPath() + "/home");
        } else {
            String emailError = "User with such email had been already created!";
            req.setAttribute("emailError", emailError);
            req.setAttribute("user", user);
            user.setEmail(email);
            req.setAttribute("roles", roles);
            req.setAttribute("selectedRoleId", roleIdFromDb);
            req.getRequestDispatcher("/WEB-INF/views/update_user.jsp")
                    .forward(req, resp);
            return;
        }
    }
}