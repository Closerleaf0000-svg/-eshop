package com.example.eshop.service;

import com.example.eshop.entity.Product;
import com.example.eshop.mapper.ProductMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    private final ProductMapper productMapper;

    public ProductService(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    // 查詢全部商品
    public List<Product> findAll() {
        return productMapper.findAll();
    }

    // 商品名稱關鍵字搜尋
    public List<Product> search(String keyword) {
        return productMapper.searchByProductName(keyword);
    }

    // 根據商品 ID 查詢單一商品
    public Product findByProductId(Long productId) {
        return productMapper.findByProductId(productId);
    }
}