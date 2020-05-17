package com.github.xrapalexandra.kr.web.servlet;

import com.github.xrapalexandra.kr.model.Product;
import com.github.xrapalexandra.kr.model.Rating;
import com.github.xrapalexandra.kr.model.User;
import com.github.xrapalexandra.kr.service.ProductService;
import com.github.xrapalexandra.kr.service.RatingService;
import com.github.xrapalexandra.kr.service.impl.DefaultProductService;
import com.github.xrapalexandra.kr.service.impl.DefaultRatingService;
import com.github.xrapalexandra.kr.web.WebUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "RatingServlet", urlPatterns = {"/rating"})
public class RatingServlet extends HttpServlet {

    RatingService ratingService = DefaultRatingService.getInstance();
    ProductService productService = DefaultProductService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        WebUtils.forward("productList", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getParameter("rating") == null) {
            req.setAttribute("error", "Выберите одно из значений!");
        } else {
            int productId = Integer.parseInt(req.getParameter("pId"));
            Integer mark = Integer.parseInt(req.getParameter("rating"));
            Product product = productService.getProductById(productId);
            User user = (User) req.getSession().getAttribute("user");
            Rating rating = new Rating(mark, user, product);
            if (ratingService.addRating(rating) == null)
                req.setAttribute("error", "Вы уже оценили этот товар.");
        }
        WebUtils.forward("product", req, resp);
    }
}
