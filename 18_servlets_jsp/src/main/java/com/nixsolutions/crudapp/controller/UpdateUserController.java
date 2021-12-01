package com.nixsolutions.crudapp.controller;

import com.nixsolutions.crudapp.entity.User;
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
import static com.nixsolutions.crudapp.util.UserAttributesUtil.setDateError;
import static com.nixsolutions.crudapp.util.UserAttributesUtil.setEmailError;
import static com.nixsolutions.crudapp.util.UserAttributesUtil.setRolesAttribute;
import static com.nixsolutions.crudapp.util.UserAttributesUtil.setSelectedRoleId;

@WebServlet("/users/update")
public class UpdateUserController extends HttpServlet {

    private final UserService userService;

    public UpdateUserController() {
        userService = new UserServiceImpl();

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

        User user = userService.findByLogin(req.getParameter("login"));
        Long id = Long.valueOf(req.getParameter("id"));

        User userWithAttributes = getUserWithAttributes(req);
        if (userService.findByEmail(userWithAttributes.getEmail()) != null) {
            if (!userService.findByLogin(user.getLogin()).getEmail()
                    .equals(userWithAttributes.getEmail())) {
                setEmailError(req);
                userWithAttributes.setId(id);
                req.setAttribute("user", userWithAttributes);
                setRolesAttribute(req);
                setSelectedRoleId(req, userWithAttributes);
                req.getRequestDispatcher("/WEB-INF/views/update_user.jsp")
                        .forward(req, resp);
            }
        }
        if (userWithAttributes.getBirthday() == null
                || userWithAttributes.getBirthday()
                .before(Date.valueOf("1900-01-01"))
                || userWithAttributes.getBirthday()
                .after(new Date(new java.util.Date().getTime()))) {
            setDateError(req);
            userWithAttributes.setId(id);
            req.setAttribute("user", userWithAttributes);
            setRolesAttribute(req);
            setSelectedRoleId(req, userWithAttributes);
            req.getRequestDispatcher("/WEB-INF/views/update_user.jsp")
                    .forward(req, resp);
            return;
        }
        userWithAttributes.setId(id);
        userService.update(userWithAttributes);
        resp.sendRedirect(getServletContext().getContextPath() + "/home");
    }
}