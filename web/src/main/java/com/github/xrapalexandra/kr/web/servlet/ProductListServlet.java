package com.github.xrapalexandra.kr.web.servlet;

import com.github.xrapalexandra.kr.service.ProductService;
import com.github.xrapalexandra.kr.service.impl.DefaultProductService;
import com.github.xrapalexandra.kr.web.WebUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ProductListServlet", urlPatterns = {"/productList"})
public class ProductListServlet extends HttpServlet {

    private ProductService productService = DefaultProductService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        int page;
        if(req.getParameter("page") == null)
            page = 1;
        else
            page = Integer.parseInt(req.getParameter("page"));
        Integer pageCount = productService.getPageCount();

        req.setAttribute("productList", productService.getProductList(page));
        req.setAttribute("pageJsp", "/pages/productList.jsp");
        req.setAttribute("pageCount", pageCount);
        req.setAttribute("page", page);

        WebUtils.forwardJSP("index", req, resp);
    }
}
