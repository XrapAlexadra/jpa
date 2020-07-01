package com.github.xrapalexandra.kr.web.controller.admins;

import com.github.xrapalexandra.kr.model.Status;
import com.github.xrapalexandra.kr.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admins/orders")
public class AdminOrdersController {

    private OrderService orderService;

    public AdminOrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("")
    public String getOrders(ModelMap model){
        model.put("allOrders", orderService.getAllOrders(1).getContent());
        return "adminBasket";
    }

    @GetMapping("/writeOff")
    public String writeOff(){
        orderService.writeOffOrders();
        return "redirect:/admins/orders";
    }

    @PostMapping("/changeStatus/{id}")
    public String changeStatus(@RequestParam("newStatus") Status status,
                               @PathVariable Integer id){
        orderService.changeOrderStatus(id, status);
        return "redirect:/admins/orders";
    }
}
