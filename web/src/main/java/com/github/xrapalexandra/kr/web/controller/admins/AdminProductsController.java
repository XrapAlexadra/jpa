package com.github.xrapalexandra.kr.web.controller.admins;

import com.github.xrapalexandra.kr.model.Product;
import com.github.xrapalexandra.kr.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/admins/products")
public class AdminProductsController {

    private ProductService productService;

    public AdminProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/list/{page}")
    public String getProductsPage(ModelMap model, @PathVariable Integer page) {
        Page<Product> productPage = productService.getProductsPage(page);
        model.put("productList", productPage.getContent());
        model.put("pageCount", productPage.getTotalPages());
        model.put("page", productPage.getPageable().getPageNumber()+1);
        return "adminProductsList";
    }
    @GetMapping("/add")
    public String addProduct(ModelMap model){
        return "addProducts";
    }

    @PostMapping("/add")
    public String addProduct(ModelMap model,
                             @Valid Product product,
                             BindingResult br) {
        if (!br.hasErrors()) {
            if (productService.addProduct(product))
                model.put("message", "Продукт успешно добавлен!");
            else
                model.put("error", "Невозможно добавить! Продукт с таким названием уже существует!");
        }
        return "message";

    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(ModelMap model, @PathVariable Integer id) {
        if (!productService.delProduct(id))
            model.put("error", "Невозможно удалить товар! Проверьте заказы!");
        else
            model.put("message", "Товар удален");
        return "message";
    }

    @GetMapping("/update/{id}")
    public String updateProduct(ModelMap model, @PathVariable Integer id) {
        Product product = productService.getProductById(id);
        model.put("product", product);
        return "changeProducts";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(ModelMap model, Product product, @PathVariable Integer id) {
        product.setId(id);
        if (!productService.updateProduct(product))
            model.put("error", "Невозможно изменить товар! Такой уже существует!");
        else
            model.put("message", "Товар изменен");

        return "message";
    }
}
