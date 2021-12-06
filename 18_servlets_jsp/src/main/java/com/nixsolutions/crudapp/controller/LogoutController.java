package com.nixsolutions.crudapp.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        req.getSession().removeAttribute("login");
        req.getSession().removeAttribute("role");
        req.getSession().removeAttribute("name");
        resp.sendRedirect(getServletContext().getContextPath() + "/login");
    }
}
