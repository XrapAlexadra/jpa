package com.github.xrapalexandra.kr.web.controller;

import com.github.xrapalexandra.kr.model.Status;
import com.github.xrapalexandra.kr.service.OrderService;
import com.github.xrapalexandra.kr.web.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.lang.invoke.MethodHandles;

@Controller
@RequestMapping("/admins/orders")
public class AdminOrderController {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final OrderService orderService;

    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{page}")
    public ModelAndView getOrders(@PathVariable Integer page){
        ModelAndView model = WebUtil.fillInModel(orderService.getAllOrders(page-1));
        model.addObject("address", "/admins/orders/");
        model.setViewName("admins/basket");
        return model;
    }

    @GetMapping("/writeOff")
    public String writeOff(){
        orderService.writeOffOrders();
        logger.info("writeOff PAID orders");
        return "redirect:/admins/orders/1";
    }

    @PostMapping("/changeStatus/{id}")
    public String changeStatus(@RequestParam("newStatus") Status status,
                               @PathVariable Integer id){
        orderService.changeOrderStatus(id, status);
        logger.info("Change order: {} status : {}", id, status);
        return "redirect:/admins/orders/1";
    }
}
