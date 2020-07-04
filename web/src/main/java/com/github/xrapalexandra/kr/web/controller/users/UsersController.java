package com.github.xrapalexandra.kr.web.controller.users;

import com.github.xrapalexandra.kr.model.Role;
import com.github.xrapalexandra.kr.model.User;
import com.github.xrapalexandra.kr.service.ShopAddressService;
import com.github.xrapalexandra.kr.service.UserService;
import com.github.xrapalexandra.kr.web.util.WebUtil;
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
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping
public class UsersController {

    private ShopAddressService shopAddressService;
    private UserService userService;

    public UsersController(ShopAddressService shopAddressService, UserService userService) {
        this.shopAddressService = shopAddressService;
        this.userService = userService;
    }

    @GetMapping("/")
    public ModelAndView getDefault() {
        ModelAndView model = new ModelAndView();
        model.setViewName("default");
        return model;
    }


    @GetMapping("/login")
    public ModelAndView login(ModelAndView model) {
        if (!WebUtil.isAuthentication())
            model.setViewName("login");
        else
            model.setViewName("login");
        return model;
    }

    @PostMapping("/login")
    public String login(ModelMap model, HttpServletRequest rq) {
        String login = rq.getParameter("login");
        String password = rq.getParameter("password");
        User user = userService.logIn(login, password);
        if (user == null) {
            model.put("error", "Неправильный логин или пароль.");
            return "message";
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "default";
    }

    private List<GrantedAuthority> getAuthorities() {
        return Arrays.asList((GrantedAuthority) () -> "ROLE_ADMIN",
                (GrantedAuthority) () -> "ROLE_USER");
    }


    @GetMapping("/auth")
    public ModelAndView authenticate(ModelAndView model) {
        if (!WebUtil.isAuthentication())
            model.setViewName("auth");
        else
            model.setViewName("default");
        return model;
    }

    @PostMapping("/auth")
    public String authenticate(ModelMap model, HttpServletRequest rq) {
        String login = rq.getParameter("login");
        String password = rq.getParameter("password");
        User user = userService.addUser(new User(login, Role.USER, password));
        if (user == null) {
            model.put("error", "Пользователь с логином:" + login + " уже существует. Используйте другой логин.");
            return "message";
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "default";
    }

    @GetMapping("/exit")
    public ModelAndView exit(ModelAndView model, HttpServletRequest rq) {
        SecurityContextHolder.clearContext();
        try {
            rq.logout();
        } catch (ServletException e) {
            throw new RuntimeException();
        }
        model.setViewName("login");
        return model;
    }

    @GetMapping("/shopAddresses")
    public String getShopAddresses(ModelMap model) {
        model.put("shop", shopAddressService.getShopAddressList());
        return "shopAddresses";
    }
}