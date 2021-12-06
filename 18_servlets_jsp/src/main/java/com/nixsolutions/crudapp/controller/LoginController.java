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
import java.util.Optional;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    private final UserService userService;

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
        Optional<User> user = Optional.ofNullable(
                userService.findByLogin(login));
        if (user.isEmpty()) {
            req.setAttribute("error", "User with such login is not found!");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp")
                    .forward(req, resp);
            return;
        } else if (!user.get().getPassword().equals(password)) {
            req.setAttribute("error", "Password is invalid, please check "
                    + "the password you have entered");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp")
                    .forward(req, resp);
            return;
        }
        HttpSession session = req.getSession();
        session.setAttribute("login", user.get().getLogin());
        session.setAttribute("role", user.get().getRole().getName());
        session.setAttribute("name", user.get().getFirstName());
        resp.sendRedirect(req.getContextPath() + "/home");
    }
}