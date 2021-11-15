package com.nixsolutions.crudapp.controller;

import com.nixsolutions.crudapp.entity.User;
import com.nixsolutions.crudapp.service.UserService;
import com.nixsolutions.crudapp.service.impl.UserServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users/delete")
public class DeleteUserController extends HttpServlet {

    private UserService userService;

    public DeleteUserController() {
        userService = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        User user = userService.findByLogin(req.getParameter("login"));
        userService.remove(user);
        resp.sendRedirect(getServletContext().getContextPath() + "/home");
    }
}