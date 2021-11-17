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

import static com.nixsolutions.crudapp.util.UserAttributesUtil.setDateError;
import static com.nixsolutions.crudapp.util.UserAttributesUtil.setEmailError;
import static com.nixsolutions.crudapp.util.UserAttributesUtil.setRolesAttribute;
import static com.nixsolutions.crudapp.util.UserAttributesUtil.setSelectedRoleId;

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
        setRolesAttribute(req);
        setSelectedRoleId(req, user);
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

        boolean validEmail = email.equals(user.getEmail());
        if (!validEmail) {
            validEmail = !userService.existsByEmail(email);
        }
        if (validEmail) {
            if (date == null || date.before(Date.valueOf("1900-01-01"))
                    || date.after(new Date(new java.util.Date().getTime()))) {
                setDateError(req);
                req.setAttribute("user", user);
                user.setEmail(email);
                setRolesAttribute(req);
                setSelectedRoleId(req, user);
                req.getRequestDispatcher("/WEB-INF/views/update_user.jsp")
                        .forward(req, resp);
                return;
            }
            user.setEmail(email);
            userService.update(user);
            resp.sendRedirect(getServletContext().getContextPath() + "/home");
        } else {
            setEmailError(req);
            req.setAttribute("user", user);
            user.setEmail(email);
            setRolesAttribute(req);
            setSelectedRoleId(req, user);
            req.getRequestDispatcher("/WEB-INF/views/update_user.jsp")
                    .forward(req, resp);
        }
    }
}