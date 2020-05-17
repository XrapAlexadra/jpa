package com.github.xrapalexandra.kr.web.servlet;

import com.github.xrapalexandra.kr.service.ProductService;
import com.github.xrapalexandra.kr.service.impl.DefaultProductService;
import com.github.xrapalexandra.kr.web.WebUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DelProductServlet", urlPatterns = {"/delProduct"})
public class DelProductServlet extends HttpServlet {

    ProductService productService = DefaultProductService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        WebUtils.forward("adminBasket", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        Integer productId = Integer.parseInt(req.getParameter("productId"));

        if (!productService.delProduct(productId))
            req.setAttribute("error", "Невозможно удалить товар! Проверьте заказы!");
        else
            req.setAttribute("message", "Товар удален");

        WebUtils.forward("adminProductList", req, resp);
    }
}
