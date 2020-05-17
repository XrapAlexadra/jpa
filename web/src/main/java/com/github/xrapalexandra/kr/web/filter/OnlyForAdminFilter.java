package com.github.xrapalexandra.kr.web.filter;

import com.github.xrapalexandra.kr.model.Role;
import com.github.xrapalexandra.kr.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OnlyForAdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        if (req.getSession().getAttribute("user") != null) {
            User user = (User) req.getSession().getAttribute("user");
            if (user.getRole() == Role.ADMIN)
                chain.doFilter(request, response);
        } else {
            try {
                resp.sendRedirect("/web/index.jsp");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
