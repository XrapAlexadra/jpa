package com.github.xrapalexandra.kr.web.controller;


import com.github.xrapalexandra.kr.model.ShopAddress;
import com.github.xrapalexandra.kr.service.ShopAddressService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/shopAddresses")
public class ShopAddressController {

    private ShopAddressService shopAddressService;

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
    public String addShopAddress(ModelMap model, ShopAddress shopAddress){
        shopAddressService.addAddress(shopAddress);
        model.put("shop", shopAddressService.getShopAddressList());
        return "shopAddresses";
    }

    @PostMapping("/delete")
    @Secured("ROLE_ADMIN")
    public String deleteShopAddress(ModelMap model, @RequestParam("delShop[]") Integer[] ids) {
        for (Integer i: ids)
            shopAddressService.delAddress(i);
        model.put("shop", shopAddressService.getShopAddressList());
        return "shopAddresses";
    }
}
