package com.github.xrapalexandra.kr.web.servlet;

import com.github.xrapalexandra.kr.model.Product;
import com.github.xrapalexandra.kr.service.ProductService;
import com.github.xrapalexandra.kr.service.impl.DefaultProductService;
import com.github.xrapalexandra.kr.web.WebUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "AddProductServlet", urlPatterns = {"/addProduct"})
public class AddProductServlet extends HttpServlet {
    private ProductService productService = DefaultProductService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        req.setAttribute("pageJsp", "/pages/addProduct.jsp");
        WebUtils.forwardJSP("index", req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        Product product = WebUtils.getProductFromReq(req);
        if (!productService.addProduct(product))
            req.setAttribute("error", "Невозможно добавить! Продукт с таким названием уже существует!");
        else
            req.setAttribute("message", "Продукт успешно добавлен!");
        WebUtils.forward("adminProductList", req, resp);
    }
}
