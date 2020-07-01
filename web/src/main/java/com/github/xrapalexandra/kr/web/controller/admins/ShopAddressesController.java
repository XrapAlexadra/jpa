package com.github.xrapalexandra.kr.web.controller.admins;


import com.github.xrapalexandra.kr.model.ShopAddress;
import com.github.xrapalexandra.kr.service.ShopAddressService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admins/shopAddresses")
public class ShopAddressesController {

    private ShopAddressService shopAddressService;

    public ShopAddressesController(ShopAddressService shopAddressService) {
        this.shopAddressService = shopAddressService;
    }

    @GetMapping("")
    public String getShopAddresses(ModelMap model){
        model.put("shop", shopAddressService.getShopAddressList());
        return "shopAddresses";
    }

    @PostMapping("/add")
    public String addShopAddress(ModelMap model, ShopAddress shopAddress){
        shopAddressService.addAddress(shopAddress);
        model.put("shop", shopAddressService.getShopAddressList());
        return "shopAddresses";
    }

    @PostMapping("/delete")
    public String deleteShopAddress(ModelMap model, @RequestParam("delShop[]") Integer[] ids) {
        for (Integer i: ids)
            shopAddressService.delAddress(i);
        model.put("shop", shopAddressService.getShopAddressList());
        return "shopAddresses";
    }

}
