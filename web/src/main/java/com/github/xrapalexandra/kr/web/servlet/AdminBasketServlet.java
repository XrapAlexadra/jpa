package com.github.xrapalexandra.kr.web.servlet;

import com.github.xrapalexandra.kr.model.Order;
import com.github.xrapalexandra.kr.service.OrderService;
import com.github.xrapalexandra.kr.service.impl.DefaultOrderService;
import com.github.xrapalexandra.kr.web.WebUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminBasketServlet", urlPatterns = {"/adminBasket"})
public class AdminBasketServlet extends HttpServlet {

    OrderService orderService = DefaultOrderService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("allOrders", orderService.getAllOrders(1));
        req.setAttribute("pageJsp", "/pages/adminBasket.jsp");
        WebUtils.forwardJSP("index", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
            List<Order> paidOrders = orderService.getPaidOrders();
            for( Order order: paidOrders){
                orderService.updateProductQuantity(order);
                orderService.delOrder(order.getId());
            }
            try {
                resp.sendRedirect("/web/adminBasket");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }
}
