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
        if (!WebUtils.isExist(basket, productId))
            basket.add(productId);
    }

    public synchronized void delProduct(Integer productId){
        basket.remove(productId);
    }

    public synchronized List<Integer> getOrders(){
        return new ArrayList<>(basket);
    }


    public String saveImage(String image, Integer productId) {
        try {
            BufferedImage img = ImageIO.read(new URL(image));
            String fileName = 1 + ".jpg";
            File file = new File("src/main/java/com/github/xrapalexandra/kr/web/img", fileName);
            ImageIO.write(img, "jpg", file);
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
