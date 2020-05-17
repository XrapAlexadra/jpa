package com.github.xrapalexandra.kr.web.servlet;

import com.github.xrapalexandra.kr.service.ProductService;
import com.github.xrapalexandra.kr.service.impl.DefaultProductService;
import com.github.xrapalexandra.kr.web.WebUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "AdminProductListServlet", urlPatterns = {"/adminProductList"})
public class AdminProductListServlet extends HttpServlet {

    ProductService productService = DefaultProductService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("productList", productService.getProductList(1));
        req.setAttribute("pageJsp", "/pages/adminProductList.jsp");
        WebUtils.forwardJSP("index", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        doGet(req, resp);
    }
}
