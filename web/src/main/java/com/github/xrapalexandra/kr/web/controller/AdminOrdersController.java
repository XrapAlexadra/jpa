package com.github.xrapalexandra.kr.web.controller;

import com.github.xrapalexandra.kr.model.Status;
import com.github.xrapalexandra.kr.service.OrderService;
import com.github.xrapalexandra.kr.web.util.WebUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admins/orders")
public class AdminOrdersController {

    private OrderService orderService;

    public AdminOrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{page}")
    public ModelAndView getOrders(@PathVariable Integer page){
        ModelAndView model = WebUtil.fillInModel(orderService.getAllOrders(page));
        model.addObject("address", "/admins/orders/");
        model.setViewName("adminBasket");
        return model;
    }

    @GetMapping("/writeOff")
    public String writeOff(){
        orderService.writeOffOrders();
        return "redirect:/admins/orders/1";
    }

    @PostMapping("/changeStatus/{id}")
    public String changeStatus(@RequestParam("newStatus") Status status,
                               @PathVariable Integer id){
        orderService.changeOrderStatus(id, status);
        return "redirect:/admins/orders/1";
    }
}
