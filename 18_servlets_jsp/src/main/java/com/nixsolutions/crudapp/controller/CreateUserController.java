package com.nixsolutions.crudapp.controller;

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

import static com.nixsolutions.crudapp.util.UserAttributesUtil.getUserWithAttributes;
import static com.nixsolutions.crudapp.util.UserAttributesUtil.setDateError;
import static com.nixsolutions.crudapp.util.UserAttributesUtil.setEmailError;
import static com.nixsolutions.crudapp.util.UserAttributesUtil.setLoginError;
import static com.nixsolutions.crudapp.util.UserAttributesUtil.setRolesAttribute;

@WebServlet("/new")
public class CreateUserController extends HttpServlet {

    private final UserService userService;

    private final RoleService roleService;

    public CreateUserController() {
        userService = new UserServiceImpl();
        roleService = new RoleServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        setRolesAttribute(req);
        req.getRequestDispatcher("/WEB-INF/views/create_user.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User userWithAttributes = getUserWithAttributes(req);
        if (userService.findByLogin(userWithAttributes.getLogin()) != null) {
            setLoginError(req);
            req.setAttribute("user", userWithAttributes);
            req.getRequestDispatcher("/WEB-INF/views/create_user.jsp")
                    .forward(req, resp);
            return;
        } else if (userService.findByEmail(userWithAttributes.getEmail())
                != null) {
            setEmailError(req);
            req.setAttribute("user", userWithAttributes);
            req.getRequestDispatcher("/WEB-INF/views/create_user.jsp")
                    .forward(req, resp);
            return;
        } else if (userWithAttributes.getBirthday() == null
                || userWithAttributes.getBirthday()
                .before(Date.valueOf("1900-01-01"))
                || userWithAttributes.getBirthday()
                .after(new Date(new java.util.Date().getTime()))) {
            setDateError(req);
            req.setAttribute("user", userWithAttributes);
            req.getRequestDispatcher("/WEB-INF/views/create_user.jsp")
                    .forward(req, resp);
            return;
        }
        userService.create(userWithAttributes);
        req.setAttribute("message", "You have registered successfully!");
        req.getRequestDispatcher("/WEB-INF/views/create_user.jsp")
                .forward(req, resp);
    }
}