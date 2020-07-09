package com.github.xrapalexandra.kr.web.controller;

import com.github.xrapalexandra.kr.model.Order;
import com.github.xrapalexandra.kr.model.OrderContent;
import com.github.xrapalexandra.kr.model.Status;
import com.github.xrapalexandra.kr.model.User;
import com.github.xrapalexandra.kr.service.OrderService;
import com.github.xrapalexandra.kr.service.ProductService;
import com.github.xrapalexandra.kr.web.BasketBean;
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
        BasketBean basketBean = (BasketBean) session.getAttribute("basket");
        if (basketBean != null)
            model.put("basket", productService.getProductListByIds(basketBean.getOrders()));
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
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BasketBean bean = (BasketBean) session.getAttribute("basket");
        List<OrderContent> orderContentList = orderService.createOrderContent(bean.getOrders(), quantities);
        Order order = new Order(user, orderContentList, Status.ORDER);
        orderService.addOrder(order);
        logger.info("Add order: {}", order);
        session.removeAttribute("basket");
        model.put("message", "Заказ создан.");
        return "messages";
    }

    @PostMapping("/delete/{id}")
    public String deleteOrder(HttpSession session,
                              @PathVariable Integer id) {
        BasketBean bean = BasketBean.get(session);
        bean.delProduct(id);
        logger.info("Delete product: {} from basket.", id);
        return "redirect:/basket/";
    }

    @PostMapping("/add/{id}")
    public String addToOrder(HttpSession session,
                             @PathVariable Integer id) {
        BasketBean bean = BasketBean.get(session);
        bean.addProductId(id);
        logger.info("Add product: {} in basket", id);
        return "redirect:/products/" + id;
    }
}
