package com.github.xrapalexandra.kr.web.controller;

import com.github.xrapalexandra.kr.model.Role;
import com.github.xrapalexandra.kr.model.User;
import com.github.xrapalexandra.kr.service.UserService;
import com.github.xrapalexandra.kr.web.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.lang.invoke.MethodHandles;
import java.util.*;

@Controller
@RequestMapping
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private static final Map<Role, List<GrantedAuthority>> roles = new HashMap<>();

    static {
        roles.put(Role.ADMIN, Arrays.asList((GrantedAuthority) () -> "ROLE_ADMIN",
                (GrantedAuthority) () -> "ROLE_USER"));
        roles.put(Role.USER, Collections.singletonList((GrantedAuthority) () -> "ROLE_USER"));
    }

    @GetMapping("/login")
    public ModelAndView login(ModelAndView model) {
        if (!WebUtil.isAuthentication())
            model.setViewName("login");
        else
            model.setViewName("welcome");
        return model;
    }

    @PostMapping("/login")
    public String login(ModelMap model, HttpServletRequest rq) {
        String login = rq.getParameter("login");
        String password = rq.getParameter("password");
        User user = userService.logIn(login, password);
        if (user == null) {
            logger.info("Wrong entry: login: {} or password invalid", login);
            model.put("error", "Неправильный логин или пароль.");
            return "messages";
        }
        logger.info("User: {} login", login);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, roles.get(user.getRole()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "welcome";
    }

    @GetMapping("/auth")
    public ModelAndView authenticate(ModelAndView model) {
        if (!WebUtil.isAuthentication())
            model.setViewName("auth");
        else
            model.setViewName("welcome");
        return model;
    }

    @PostMapping("/auth")
    public String authenticate(ModelMap model, HttpServletRequest rq) {
        String login = rq.getParameter("login");
        String password = rq.getParameter("password");
        User user = userService.addUser(new User(login, Role.USER, password));
        if (user == null) {
            logger.info("Not authorize user with login: {}.", login);
            model.put("error", "Пользователь с логином:" + login + " уже существует. Используйте другой логин.");
            return "messages";
        }
        logger.info("Authorize user with login: {}.", login);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, roles.get(user.getRole()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "welcome";
    }

    @GetMapping("/exit")
    public String exit(HttpServletRequest rq) {
        SecurityContextHolder.clearContext();
        try {
            rq.logout();
        } catch (ServletException e) {
            throw new RuntimeException();
        };
        return "login";
    }
}