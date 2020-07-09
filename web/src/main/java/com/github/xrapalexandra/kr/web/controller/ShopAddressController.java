package com.github.xrapalexandra.kr.web.controller;


import com.github.xrapalexandra.kr.model.ShopAddress;
import com.github.xrapalexandra.kr.service.ShopAddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.invoke.MethodHandles;

@Controller
@RequestMapping("/shopAddresses")
public class ShopAddressController {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ShopAddressService shopAddressService;

    public ShopAddressController(ShopAddressService shopAddressService) {
        this.shopAddressService = shopAddressService;
    }

    @GetMapping("/")
    public String getShopAddresses(ModelMap model) {
        model.put("shop", shopAddressService.getShopAddressList());
        return "shopAddresses";
    }

    @PostMapping("/add")
    @Secured("ROLE_ADMIN")
    public String addShopAddress(ShopAddress shopAddress) {
        shopAddressService.addAddress(shopAddress);
        logger.info("Add shopAddress: {}.", shopAddress);
        return "redirect:/shopAddresses/";
    }

    @PostMapping("/delete")
    @Secured("ROLE_ADMIN")
    public String deleteShopAddress(ModelMap model, @RequestParam("delShop[]") Integer[] ids) {
        for (Integer i : ids){
            shopAddressService.delAddress(i);
            logger.info("Delete shopAddress: {}.", i);
        }

        return "redirect:/shopAddresses/";
    }
}
