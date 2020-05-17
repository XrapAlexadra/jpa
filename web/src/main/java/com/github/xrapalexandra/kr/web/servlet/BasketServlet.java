package com.github.xrapalexandra.kr.web.servlet;

import com.github.xrapalexandra.kr.model.*;
import com.github.xrapalexandra.kr.service.OrderService;
import com.github.xrapalexandra.kr.service.ProductService;
import com.github.xrapalexandra.kr.service.impl.DefaultOrderService;
import com.github.xrapalexandra.kr.service.impl.DefaultProductService;
import com.github.xrapalexandra.kr.web.BasketBean;
import com.github.xrapalexandra.kr.web.WebUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "BasketServlet", urlPatterns = {"/basket"})
public class BasketServlet extends HttpServlet {

    ProductService productService = DefaultProductService.getInstance();
    OrderService orderService = DefaultOrderService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        BasketBean bean = (BasketBean) session.getAttribute("basket");
        if (bean != null) {
            List<Product> basket = new ArrayList<>();
            for (int product_id : bean.getOrders())
                basket.add(productService.getProductById(product_id));
            req.setAttribute("basket", basket);
        }

        User user = (User) session.getAttribute("user");
        if (user != null) {
            List<Order> orderInProcess = orderService.getUserOrders(user.getId());
            req.setAttribute("orderInProcess", orderInProcess);
        }
        req.setAttribute("pageJsp", "/pages/basket.jsp");
        WebUtils.forwardJSP("index", req, resp);
    }


//    Set orders
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            req.setAttribute("error", "Sign in to make an order.");
            doGet( req, resp);
        }

        int[] quantity = Arrays.stream(req.getParameterValues("quantity"))
                .mapToInt(Integer::parseInt).toArray();

        BasketBean bean = (BasketBean) req.getSession().getAttribute("basket");
        List<Integer> productIdList = bean.getOrders();
        List<OrderContent> orderContentList = new ArrayList<>();
        for (int i = 0; i < productIdList.size(); i++)
            orderContentList.add(
                    new OrderContent(productService.getProductById(productIdList.get(i)),quantity[i])
            );
        Order order = new Order(user, orderContentList, Status.ORDER);
        orderService.addOrder(order);

        req.getSession().removeAttribute("basket");
        doGet(req, resp);
    }
}
