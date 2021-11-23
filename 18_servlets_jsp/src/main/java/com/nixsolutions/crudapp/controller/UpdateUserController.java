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
        String email = req.getParameter("email");
        Date date = Date.valueOf(req.getParameter("birthday"));
        Long id = Long.valueOf(req.getParameter("id"));

        boolean validEmail = email.equals(user.getEmail());
        if (!validEmail) {
            validEmail = !userService.existsByEmail(email);
        }
        User userError;
        if (validEmail) {
            if (date == null || date.before(Date.valueOf("1900-01-01"))
                    || date.after(new Date(new java.util.Date().getTime()))) {
                setDateError(req);
                userError = getUserWithAttributes(req);
                userError.setId(id);
                req.setAttribute("user", userError);
                setRolesAttribute(req);
                setSelectedRoleId(req, userError);
                req.getRequestDispatcher("/WEB-INF/views/update_user.jsp")
                        .forward(req, resp);
                return;
            }
            userError = getUserWithAttributes(req);
            userError.setId(id);
            userService.update(userError);
            resp.sendRedirect(getServletContext().getContextPath() + "/home");
        } else {
            setEmailError(req);
            userError = getUserWithAttributes(req);
            userError.setId(id);
            req.setAttribute("user", userError);
            setRolesAttribute(req);
            setSelectedRoleId(req, userError);
            req.getRequestDispatcher("/WEB-INF/views/update_user.jsp")
                    .forward(req, resp);
        }
    }
}