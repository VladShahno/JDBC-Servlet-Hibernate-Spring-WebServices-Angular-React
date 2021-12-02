package com.nixsolutions.crudapp.filter;

import com.nixsolutions.crudapp.service.UserService;
import com.nixsolutions.crudapp.service.impl.UserServiceImpl;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    private final UserService userService;

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
        HttpSession session = req.getSession(false);
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
        if (session.getAttribute("role").equals("USER") && !url.equals("/home")) {
            resp.sendError(403);
            return;
        }
        filterChain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
    }
}