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
import java.util.List;

@WebServlet("/home")
public class HomeController extends HttpServlet {

    private UserService userService;

    public HomeController() {
        userService = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (req.getSession().getAttribute("role").equals("ADMIN")) {
            List<User> userList = userService.findAll();
            req.setAttribute("users", userList);
            req.getRequestDispatcher("/WEB-INF/views/admin_home.jsp")
                    .forward(req, resp);
            return;
        } else {
            req.getRequestDispatcher("/WEB-INF/views/user_home.jsp")
                    .forward(req, resp);
        }
    }
}