package com.github.xrapalexandra.kr.web.servlet;

import com.github.xrapalexandra.kr.model.Product;
import com.github.xrapalexandra.kr.service.ProductService;
import com.github.xrapalexandra.kr.service.RatingService;
import com.github.xrapalexandra.kr.service.impl.DefaultProductService;
import com.github.xrapalexandra.kr.service.impl.DefaultRatingService;
import com.github.xrapalexandra.kr.web.WebUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ProductServlet", urlPatterns = {"/product"})
public class ProductServlet extends HttpServlet {

    private ProductService productService = DefaultProductService.getInstance();
    private RatingService ratingService = DefaultRatingService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getParameter("pId") == null)
            WebUtils.forward("productList", req, resp);

        Product product = productService.getProductById(Integer.parseInt(req.getParameter("pId")));
        Integer mark = ratingService.getAvrRatingByProduct(product);

        req.setAttribute("mark", mark);
        req.setAttribute("product", product);
        req.setAttribute("pageJsp", "/pages/product.jsp");
        WebUtils.forwardJSP("index", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        doGet(req, resp);
    }

}