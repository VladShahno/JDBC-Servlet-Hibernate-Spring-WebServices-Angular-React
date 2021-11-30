package com.nixsolutions.crudapp.controller;

import com.nixsolutions.crudapp.service.UserService;
import com.nixsolutions.crudapp.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

import static com.nixsolutions.crudapp.util.UserAttributesUtil.getUserWithAttributes;
import static com.nixsolutions.crudapp.util.UserAttributesUtil.setEmailError;
import static com.nixsolutions.crudapp.util.UserAttributesUtil.setLoginError;
import static com.nixsolutions.crudapp.util.UserAttributesUtil.setRolesAttribute;
import static com.nixsolutions.crudapp.util.UserValidatorUtil.validateUser;

@WebServlet("/new")
public class CreateUserController extends HttpServlet {

    private final UserService userService;

    public CreateUserController() {
        userService = new UserServiceImpl();
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

        Date date = Date.valueOf(req.getParameter("birthday"));

        if (userService.findByLogin(req.getParameter("login")) != null) {
            setLoginError(req);
            req.setAttribute("user", getUserWithAttributes(req));
            req.getRequestDispatcher("/WEB-INF/views/create_user.jsp")
                    .forward(req, resp);
            return;
        }
        validateUser(req, resp, "/WEB-INF/views/create_user.jsp");
        if (userService.findByEmail(req.getParameter("email")) != null) {
            setEmailError(req);
            req.setAttribute("user", getUserWithAttributes(req));
            req.getRequestDispatcher("/WEB-INF/views/create_user.jsp")
                    .forward(req, resp);
            return;
        }
        userService.create(getUserWithAttributes(req));
        req.setAttribute("message", "You have registered successfully!");
        req.getRequestDispatcher("/WEB-INF/views/create_user.jsp")
                .forward(req, resp);
    }
}