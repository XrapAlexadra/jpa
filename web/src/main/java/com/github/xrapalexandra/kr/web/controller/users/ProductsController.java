package com.github.xrapalexandra.kr.web.controller.users;

import com.github.xrapalexandra.kr.model.Product;
import com.github.xrapalexandra.kr.model.Rating;
import com.github.xrapalexandra.kr.service.ProductService;
import com.github.xrapalexandra.kr.service.RatingService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductsController {

    private ProductService productService;
    private RatingService ratingService;

    public ProductsController(ProductService productService, RatingService ratingService) {
        this.productService = productService;
        this.ratingService = ratingService;
    }

    @GetMapping("/list/{page}")
    public String getProductsPage(ModelMap model, @PathVariable Integer page) {
        Page<Product> productPage = productService.getProductsPage(page);
        model.put("productList", productPage.getContent());
        model.put("pageCount", productPage.getTotalPages());
        model.put("page", productPage.getPageable().getPageNumber()+1);
        return "productsList";
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
                            Rating rating,
                            @PathVariable Integer productId) {
        if (ratingService.addRating(rating) == null)
            model.put("error", "Вы уже оценили этот товар.");
        return "products";
    }
}
