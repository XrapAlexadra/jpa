package com.github.xrapalexandra.kr.web.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ExitServlet", urlPatterns = {"/exit"})
public class ExitServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().removeAttribute("user");
        req.getSession().removeAttribute("basket");
        req.getSession().invalidate();
        try {
            resp.sendRedirect("/web/index.jsp");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
