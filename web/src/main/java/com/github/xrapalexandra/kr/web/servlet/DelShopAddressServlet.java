package com.github.xrapalexandra.kr.web.servlet;

import com.github.xrapalexandra.kr.service.ShopAddressService;
import com.github.xrapalexandra.kr.service.impl.DefaultShopAddressService;
import com.github.xrapalexandra.kr.web.WebUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DelShopAddressServlet", urlPatterns = {"/delShopAddress"})
public class DelShopAddressServlet extends HttpServlet {

    private ShopAddressService shopAddressService = DefaultShopAddressService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        WebUtils.forward("shopAddress", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
       String[] shopId = req.getParameterValues("delShop[]");
       for (String i:shopId ){
           shopAddressService.delAddress(Integer.parseInt(i));
       }
        try {
            resp.sendRedirect("/web/shopAddress");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
