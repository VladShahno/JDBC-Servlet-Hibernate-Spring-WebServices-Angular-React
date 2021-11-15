package com.nixsolutions.crudapp.filter;

import com.nixsolutions.crudapp.service.UserService;
import com.nixsolutions.crudapp.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    private UserService userService;

    public AuthFilter() {
        userService = new UserServiceImpl();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
            ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String url = req.getServletPath();
        if (url.equals("/login")) {
            filterChain.doFilter(req, resp);
            return;
        }
        String login = (String) req.getSession().getAttribute("login");
        if ((login == null || userService.findByLogin(login) == null)) {
            resp.sendRedirect("/login");
            return;
        }
        filterChain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
    }
}