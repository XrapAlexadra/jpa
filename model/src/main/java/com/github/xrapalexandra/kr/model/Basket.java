package com.github.xrapalexandra.kr.model;

import java.util.ArrayList;
import java.util.List;

public class Basket {

    private List<Integer> basket = new ArrayList<>();

    public static Basket getBasket(Basket basket) {
        if (basket == null) {
            basket = new Basket();
        }
        return basket;
    }

    public void addProductId(int productId){
        if (!isExist(productId))
            basket.add(productId);
    }

    public void delProduct(Integer productId){
        basket.remove(productId);
    }

    public List<Integer> getOrders(){
        return new ArrayList<>(basket);
    }

    private Boolean isExist(int item){
        for(int i : basket){
            if (i == item)
                return true;
        }
        return false;
    }
}
