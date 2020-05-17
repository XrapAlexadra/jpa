package com.github.xrapalexandra.kr.web.servlet;

import com.github.xrapalexandra.kr.model.Role;
import com.github.xrapalexandra.kr.model.User;
import com.github.xrapalexandra.kr.service.UserService;
import com.github.xrapalexandra.kr.service.impl.DefaultUserService;
import com.github.xrapalexandra.kr.web.WebUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AuthServlet", urlPatterns = {"/auth"})
public class AuthServlet extends HttpServlet {

    private UserService userService = DefaultUserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        Object user = req.getSession().getAttribute("user");
        if (user == null) {
            req.setAttribute("pageJsp", "/pages/auth.jsp");
        }
        WebUtils.forwardJSP("index", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter("login");
        User user = new User(login, Role.USER, req.getParameter("pass"));

        if (userService.addUser(user) == null) {
            req.setAttribute("error", "Пользователь с таким логином уже существует. Используйте другой.");
            req.setAttribute("pageJsp", "/pages/auth.jsp");
        } else
            req.getSession().setAttribute("user", user);

        WebUtils.forwardJSP("index", req, resp);
    }
}
