package com.github.xrapalexandra.kr.web.controller;

import com.github.xrapalexandra.kr.model.Product;
import com.github.xrapalexandra.kr.service.ProductService;
import com.github.xrapalexandra.kr.web.util.WebUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/admins/products")
public class AdminProductsController {

    private final String ROOT_PATH = "/opt/tomcat/temp/files/";

    private ProductService productService;

    public AdminProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/list/{page}")
    public ModelAndView getProductsPage(@PathVariable Integer page) {
        Page<Product> productPage = productService.getProductsPage(page);
        ModelAndView model = WebUtil.fillInModel(productPage);
        model.addObject("address", "/admins/products/list/");
        model.setViewName("adminProductsList");
        return model;
    }

    @GetMapping("/add")
    public String addProduct() {
        return "addProducts";
    }

    @PostMapping("/add")
    public String addProduct(ModelMap model,
                             @RequestParam("file") MultipartFile file,
                             Product product) {
        String fileName = file.getOriginalFilename();
        product.setImage(fileName);
        if (productService.addProduct(product)) {
            model.put("message", "Продукт успешно добавлен!");
            processImage(file, fileName);
        } else
            model.put("error", "Невозможно добавить! Продукт с таким названием уже существует!");
        return "message";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(ModelMap model, @PathVariable Integer id) throws Exception {
        productService.deleteProduct(id);
        model.put("message", "Товар удален");
        return "message";
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleAllException(Exception ex) {

        ModelAndView model = new ModelAndView();
        model.setViewName("message");
        model.addObject("error", "this is Exception.class");

        return model;
    }


    @GetMapping("/update/{id}")
    public String updateProduct(ModelMap model, @PathVariable Integer id) {
        Product product = productService.getProductById(id);
        model.put("product", product);
        return "changeProducts";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(ModelMap model,
                                Product product,
                                @PathVariable Integer id,
                                @RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        product.setId(id);
        product.setImage(fileName);
        if (!productService.updateProduct(product))
            model.put("error", "Невозможно изменить товар! Такой уже существует!");
        else {
            model.put("message", "Товар изменен");
            processImage(file, fileName);
        }
        return "message";
    }

    private void processImage(MultipartFile image, String fileName) {
        try {
            if (image != null && !image.isEmpty()) {
                validateImage(image);
                saveImage(fileName, image);
            }
        } catch (IOException e) {
            //Error handling
        }
    }

    private void validateImage(MultipartFile image) throws IOException {
        if (!image.getContentType().equals("image/jpeg")) {
            throw new IOException("Only JPG images accepted");
        }
    }

    private void saveImage(String filename, MultipartFile image) throws IOException {
        File file = new File(ROOT_PATH + filename);
        FileUtils.writeByteArrayToFile(file, image.getBytes());
    }
}
