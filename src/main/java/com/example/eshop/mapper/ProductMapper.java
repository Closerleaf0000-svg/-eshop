package com.example.eshop.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.example.eshop.entity.Product;

@Mapper
public interface ProductMapper {

        // 查詢全部商品
        List<Product> findAll();

        // 商品名稱關鍵字搜尋
        List<Product> searchByProductName(
                        @Param("keyword") String keyword);

        // 根據商品ID查詢商品
        Product findByProductId(
                        @Param("productId") Long productId);

}