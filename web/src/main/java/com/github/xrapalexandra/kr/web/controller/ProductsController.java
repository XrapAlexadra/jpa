package com.github.xrapalexandra.kr.web.controller;

import com.github.xrapalexandra.kr.model.Product;
import com.github.xrapalexandra.kr.model.Rating;
import com.github.xrapalexandra.kr.model.User;
import com.github.xrapalexandra.kr.service.ProductService;
import com.github.xrapalexandra.kr.service.RatingService;
import com.github.xrapalexandra.kr.web.util.WebUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/products")
public class ProductsController {

    private final String ROOT_PATH = "/opt/tomcat/temp/files/";

    private final ProductService productService;
    private RatingService ratingService;

    public ProductsController(ProductService productService, RatingService ratingService) {
        this.productService = productService;
        this.ratingService = ratingService;
    }

    @GetMapping("/list/{page}")
    public ModelAndView getProductsPage(@PathVariable Integer page) {
        Page<Product> productPage = productService.getProductsPage(page);
        ModelAndView model = WebUtil.fillInModel(productPage);
        model.addObject("address", "/products/list/");
        model.setViewName("productsList");
        return model;
    }

    @GetMapping("/{id}")
    public String getProducts(ModelMap model, @PathVariable Integer id) {
        Product product = productService.getProductById(id);
        model.put("product", productService.getProductById(id));
        model.put("mark", ratingService.getAvrRatingByProduct(product));
        return "products";
    }

    @PostMapping("/{id}/addRating")
    public String addRating(ModelMap model,
                            @RequestParam("mark") Integer mark,
                            @PathVariable Integer id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Rating rating = new Rating(mark, user, productService.getProductById(id));
        if (ratingService.addRating(rating) == null) {
            model.put("error", "Вы уже оценили этот товар.");
            return "message";
        }
        return "redirect:/products/" + id;
    }

    @GetMapping("/image")
    public ResponseEntity<byte[]> download(@RequestParam(value = "name") String fileName) {
        File file = new File(ROOT_PATH + fileName);
        if (file.exists()) {
            byte[] content = new byte[0];
            try {
                content = FileUtils.readFileToByteArray(file);
            } catch (IOException e) {
                //Exception handling
            }
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentLength(content.length);
            return new ResponseEntity<>(content, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}