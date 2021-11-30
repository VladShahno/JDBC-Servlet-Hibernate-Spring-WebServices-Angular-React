package com.nixsolutions.crudapp.util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;

import static com.nixsolutions.crudapp.util.UserAttributesUtil.getUserWithAttributes;
import static com.nixsolutions.crudapp.util.UserAttributesUtil.setDateError;

public class UserValidatorUtil {

    private static final String NAME = "[a-zA-Z]+";
    private static final String LOGIN = "^(?=.{2,10}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";

    public static void validateUser(HttpServletRequest req,
            HttpServletResponse resp, String forwardPath)
            throws ServletException, IOException {
        if (!req.getParameter("login").matches(LOGIN)) {
            req.setAttribute("loginError", "Incorrect login symbols or size!");
            req.setAttribute("user", getUserWithAttributes(req));
            req.getRequestDispatcher(forwardPath).forward(req, resp);
        } else if (!req.getParameter("first_name").matches(NAME) || (
                req.getParameter("first_name").length() < 2)) {
            req.setAttribute("firstNameError",
                    "Incorrect Name, use only latin letters!");
            req.setAttribute("user", getUserWithAttributes(req));
            req.getRequestDispatcher(forwardPath).forward(req, resp);
        } else if (!req.getParameter("last_name").matches(NAME) || (
                req.getParameter("first_name").length() < 2)) {
            req.setAttribute("lastNameError",
                    "Incorrect Surname, use only latin letters!");
            req.setAttribute("user", getUserWithAttributes(req));
            req.getRequestDispatcher(forwardPath).forward(req, resp);
        }
        Date date = Date.valueOf(req.getParameter("birthday"));
        if (date == null || date.before(Date.valueOf("1910-01-01"))
                || date.after(new Date(new java.util.Date().getTime()))) {
            setDateError(req);
            req.setAttribute("user", getUserWithAttributes(req));
            req.getRequestDispatcher(forwardPath).forward(req, resp);
        }
    }
}