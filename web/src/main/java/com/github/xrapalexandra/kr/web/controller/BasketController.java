package com.github.xrapalexandra.kr.web.controller;

import com.github.xrapalexandra.kr.model.Basket;
import com.github.xrapalexandra.kr.model.Order;
import com.github.xrapalexandra.kr.model.User;
import com.github.xrapalexandra.kr.service.OrderService;
import com.github.xrapalexandra.kr.service.ProductService;
import com.github.xrapalexandra.kr.web.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.lang.invoke.MethodHandles;
import java.util.List;

@Controller
@RequestMapping("/basket")
public class BasketController {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final OrderService orderService;
    private final ProductService productService;

    public BasketController(OrderService orderService, ProductService productService) {
        this.orderService = orderService;
        this.productService = productService;
    }

    @GetMapping("/")
    public String getBasket(ModelMap model,
                            HttpSession session) {
        Basket basket = (Basket) session.getAttribute("basket");
        if (basket != null)
            model.put("basket", productService.getProductListByIds(basket.getOrdersIds()));
        if (WebUtil.isAuthentication()) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            List<Order> orderInProcess = orderService.getUserOrders(user.getLogin());
            model.put("orderInProcess", orderInProcess);
        }
        return "basket";
    }

    @PostMapping("/setOrders")
    public String setOrders(ModelMap model,
                            @RequestParam("quantity") Integer[] quantities,
                            HttpSession session) {
        if (!WebUtil.isAuthentication()) {
            model.put("error", "Авторизируйтесь, чтобы сделать заказ.");
            return "messages";
        }
        Basket basket = (Basket) session.getAttribute("basket");
        orderService.addOrder(basket.createOrderСatalog(quantities));
        logger.info("Add order with products: {}", basket.getOrdersIds());
        session.removeAttribute("basket");
        model.put("message", "Заказ создан.");
        return "messages";
    }

    @PostMapping("/delete/{id}")
    public String deleteOrder(HttpSession session,
                              @PathVariable Integer id) {
        Basket basket = (Basket) session.getAttribute("basket");
        basket.delProduct(id);
        logger.info("Delete product: {} from basket.", id);
        return "redirect:/basket/";
    }

    @PostMapping("/add/{id}")
    public String addToOrder(HttpSession session,
                             @PathVariable Integer id) {
        Basket basket;
        if (session.getAttribute("basket") == null)
            basket = new Basket();
        else
            basket = (Basket) session.getAttribute("basket");
        basket.addProductId(id);
        session.setAttribute("basket", basket);
        logger.info("Add product: {} in basket", id);
        return "redirect:/products/" + id;
    }
}
