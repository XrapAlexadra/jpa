package com.github.xrapalexandra.kr.web.servlet;

import com.github.xrapalexandra.kr.model.Status;
import com.github.xrapalexandra.kr.service.OrderService;
import com.github.xrapalexandra.kr.service.impl.DefaultOrderService;
import com.github.xrapalexandra.kr.web.WebUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ChangeBasketServlet", urlPatterns = {"/changeBasket"})
public class ChangeBasketServlet extends HttpServlet {

    OrderService orderService = DefaultOrderService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        WebUtils.forward("adminBasket", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        Status status = Status.valueOf(req.getParameter("newStatus"));
        int orderId = Integer.parseInt(req.getParameter("orderId"));
        orderService.changeOrderStatus(orderId, status);

        try {
            resp.sendRedirect("/web/adminBasket");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
