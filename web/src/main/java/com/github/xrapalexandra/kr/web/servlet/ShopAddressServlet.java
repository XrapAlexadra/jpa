package com.github.xrapalexandra.kr.web.servlet;

import com.github.xrapalexandra.kr.model.ShopAddress;
import com.github.xrapalexandra.kr.service.ShopAddressService;
import com.github.xrapalexandra.kr.service.impl.DefaultShopAddressService;
import com.github.xrapalexandra.kr.web.WebUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "ShopAddressServlet", urlPatterns = {"/shopAddress"})
public class ShopAddressServlet extends HttpServlet {

    private ShopAddressService shopAddressService = DefaultShopAddressService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("shop", shopAddressService.getShopAddressList());
        req.setAttribute("pageJsp", "/pages/shopAddress.jsp");
        WebUtils.forwardJSP("index", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        ShopAddress shopAddress = new ShopAddress(
                req.getParameter("city"),
                req.getParameter("street"),
                req.getParameter("building")
        );
        shopAddressService.addAddress(shopAddress);
        doGet(req, resp);
    }
}
