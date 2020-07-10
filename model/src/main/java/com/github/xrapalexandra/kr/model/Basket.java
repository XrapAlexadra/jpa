package com.github.xrapalexandra.kr.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Basket {

    private final List<Integer> basket = new ArrayList<>();

    public void addProductId(int productId){
        if (!isExist(productId))
            basket.add(productId);
    }

    public void delProduct(Integer productId){
        basket.remove(productId);
    }

    public List<Integer> getOrdersIds(){
        return new ArrayList<>(basket);
    }

    public Map<Integer, Integer> createOrderСatalog(Integer[] quantities){
        Map<Integer, Integer> order = new HashMap<>();
        for(int i = 0; i < basket.size(); i++){
            order.put(basket.get(i), quantities[i]);
        }
        return order;
    }

    private Boolean isExist(int item){
        for(int i : basket){
            if (i == item)
                return true;
        }
        return false;
    }

}
