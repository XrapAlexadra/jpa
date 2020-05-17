package com.github.xrapalexandra.kr.web.servlet;

import com.github.xrapalexandra.kr.model.User;
import com.github.xrapalexandra.kr.service.UserService;
import com.github.xrapalexandra.kr.service.impl.DefaultUserService;
import com.github.xrapalexandra.kr.web.WebUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private UserService userService = DefaultUserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        Object user = req.getSession().getAttribute("user");
        if (user == null) {
            req.setAttribute("pageJsp", "/pages/login.jsp");
        }
        WebUtils.forwardJSP("index", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter("login");
        String pass = req.getParameter("pass");
        User user = userService.logIn(login, pass);

        if (user == null) {
            req.setAttribute("error", "Неправильный логин или пароль!");
            req.setAttribute("pageJsp", "/pages/login.jsp");
        } else {
            req.getSession().setAttribute("user", user);
        }

        WebUtils.forwardJSP("index", req, resp);
    }
}
