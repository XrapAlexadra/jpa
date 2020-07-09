package com.github.xrapalexandra.kr.web.controller;

import com.github.xrapalexandra.kr.model.Product;
import com.github.xrapalexandra.kr.model.Rating;
import com.github.xrapalexandra.kr.model.User;
import com.github.xrapalexandra.kr.service.ProductService;
import com.github.xrapalexandra.kr.service.RatingService;
import com.github.xrapalexandra.kr.web.util.WebUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

@Controller
@RequestMapping("/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final String ROOT_PATH = "/opt/tomcat/temp/files/";

    private final ProductService productService;
    private final RatingService ratingService;

    public ProductController(ProductService productService, RatingService ratingService) {
        this.productService = productService;
        this.ratingService = ratingService;
    }

    @GetMapping("/list/{page}")
    public ModelAndView getProductsPage(@PathVariable Integer page) {
        Page<Product> productPage = productService.getProductsPage(page - 1);
        ModelAndView model = WebUtil.fillInModel(productPage);
        model.addObject("address", "/products/list/");
        model.setViewName("productsList");
        return model;
    }

    @GetMapping("/{id}")
    public String getProducts(ModelMap model, @PathVariable Integer id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            model.put("error", "Запрашиваемая страница не найдена.");
            return "messages";
        }
        model.put("product", product);
        model.put("mark", ratingService.getAvrRatingByProduct(product));
        return "products";
    }

    @PostMapping("/{id}/addRating")
    public String addRating(ModelMap model,
                            HttpServletRequest req,
                            @PathVariable Integer id) {
        if(req.getParameter("mark") == null) {
            model.put("error", "Необходимо выбрать одно из значений.");
            return "messages";
        }
        Integer mark = Integer.parseInt(req.getParameter("mark"));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Rating rating = new Rating(mark, user, productService.getProductById(id));
        if (ratingService.addRating(rating) == null) {
            logger.info("Not add rating: {}.", rating);
            model.put("error", "Вы уже оценили этот товар.");
            return "messages";
        }
        logger.info("Add rating: {}.", rating);
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
