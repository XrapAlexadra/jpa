package com.github.xrapalexandra.kr.web.controller;

import com.github.xrapalexandra.kr.model.Product;
import com.github.xrapalexandra.kr.service.ProductService;
import com.github.xrapalexandra.kr.web.util.WebUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

@Controller
@RequestMapping("/admins/products")
public class AdminProductController {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final String ROOT_PATH = "/opt/tomcat/temp/files/";

    private final ProductService productService;

    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/list/{page}")
    public ModelAndView getProductsPage(@PathVariable Integer page) {
        Page<Product> productPage = productService.getProductsPage(page - 1);
        ModelAndView model = WebUtil.fillInModel(productPage);
        model.addObject("address", "/admins/products/list/");
        model.setViewName("admins/productsList");
        return model;
    }

    @GetMapping("/add")
    public String addProduct() {
        return "admins/addProducts";
    }

    @PostMapping("/add")
    public String addProduct(ModelMap model,
                             @RequestParam("file") MultipartFile file,
                             Product product){
        String fileName = file.getOriginalFilename();
        product.setImage(fileName);
        if (productService.addProduct(product)) {
            logger.info("Add product: {}", product);
            model.put("message", "Продукт успешно добавлен!");
            processImage(file, fileName);
            logger.info("File: {} upload", fileName);
        } else {
            model.put("error", "Невозможно добавить! Продукт с таким названием уже существует!");
            logger.info("Not add product: {}", product);
        }
        return "messages";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(ModelMap model, @PathVariable Integer id){
        productService.deleteProduct(id);
        model.put("message", "Товар удален");
        logger.info("Delete product with id: {}", id);
        return "messages";
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ModelAndView handleDataException(DataIntegrityViolationException ex) {
        logger.error("DataIntegrityViolationException. {}", ex.getMessage());
        ModelAndView model = new ModelAndView();
        model.setViewName("messages");
        model.addObject("error", "Невозможно удалить товар. Проверьте заказы.");
        return model;
    }

    @GetMapping("/update/{id}")
    public String updateProduct(ModelMap model, @PathVariable Integer id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            model.put("error", "Запрашиваемая страница не найдена.");
            return "messages";
        }
        model.put("product", product);
        return "admins/changeProducts";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(ModelMap model,
                                Product product,
                                @PathVariable Integer id,
                                @RequestParam("file") MultipartFile file) {
        if (file.getSize() != 0) {
            String fileName = file.getOriginalFilename();
            product.setImage(fileName);
            processImage(file, fileName);
            logger.info("File: {} upload", fileName);
        }
        product.setId(id);
        if (!productService.updateProduct(product)) {
            model.put("error", "Невозможно изменить товар! Такой уже существует!");
            logger.info("Not update product by {}", product);
        }
        else {
            model.put("message", "Товар изменен");
            logger.info("Update product by {}", product);
        }
        return "messages";
    }

    private void processImage(MultipartFile image, String fileName) {
        try {
            if (image != null && !image.isEmpty()) {
                validateImage(image);
                saveImage(fileName, image);
            }
        } catch (IOException ex) {
            logger.error("Exception {} upload file: {}.", ex.getMessage(), fileName);
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
