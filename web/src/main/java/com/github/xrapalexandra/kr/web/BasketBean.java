package com.github.xrapalexandra.kr.web;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BasketBean {

    private List<Integer> basket = new ArrayList<>();

    public static BasketBean get(HttpSession session) {
        BasketBean basket = (BasketBean) session.getAttribute("basket");
        if (basket == null) {
            basket = new BasketBean();
            session.setAttribute("basket", basket);
        }
        return basket;
    }

    public synchronized void addProductId(int productId){
        if (!isExist(productId))
            basket.add(productId);
    }

    public synchronized void delProduct(Integer productId){
        basket.remove(productId);
    }

    public synchronized List<Integer> getOrders(){
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
