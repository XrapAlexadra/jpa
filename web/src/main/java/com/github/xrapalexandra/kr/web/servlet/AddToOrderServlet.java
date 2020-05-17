package com.github.xrapalexandra.kr.web.servlet;


import com.github.xrapalexandra.kr.web.BasketBean;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AddToOrderServlet", urlPatterns = {"/addToOrder"})
public class AddToOrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        try {
            resp.sendRedirect("/web/productList");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        HttpSession session = req.getSession();
        BasketBean bean = BasketBean.get(session);

        bean.addProductId(Integer.parseInt(req.getParameter("pId")));
        doGet(req, resp);
    }
}
