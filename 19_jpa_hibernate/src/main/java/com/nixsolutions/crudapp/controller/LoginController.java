package com.nixsolutions.crudapp.controller;

import com.nixsolutions.crudapp.entity.User;
import com.nixsolutions.crudapp.service.UserService;
import com.nixsolutions.crudapp.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    private UserService userService;

    public LoginController() {
        userService = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        User user = userService.findByLogin(login);
        if (!userService.existsByLogin(login)) {
            req.setAttribute("error", "User with such login is not found!");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp")
                    .forward(req, resp);
            return;
        }
        if (!user.getPassword().equals(password)) {
            req.setAttribute("error", "Password is invalid, please check "
                    + "the password you have entered");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp")
                    .forward(req, resp);
            return;
        }
        HttpSession session = req.getSession();
        session.setAttribute("login", user.getLogin());
        session.setAttribute("role", user.getRole().getName());
        session.setAttribute("name", user.getFirstName());
        resp.sendRedirect(req.getContextPath() + "/home");
    }
}