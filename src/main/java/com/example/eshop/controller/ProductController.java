package com.example.eshop.controller;

import com.example.eshop.entity.Product;
import com.example.eshop.service.ProductService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 查詢全部商品
    @GetMapping("/list")
    public List<Product> productList() {
        return productService.findAll();
    }

    // 商品關鍵字搜尋
    @GetMapping("/search")
    public List<Product> search(
            @RequestParam String keyword) {
        return productService.search(keyword);
    }

    // 根據商品 ID 查詢單一商品
    @GetMapping("/{productId}")
    public Product getProductById(
            @PathVariable Long productId) {
        return productService.findByProductId(productId);
    }
}