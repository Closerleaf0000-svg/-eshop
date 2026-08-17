package com.example.eshop.mapper;

import com.example.eshop.entity.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface CartMapper {

        // 加入購物車
        void insert(Cart cart);

        // 查詢會員購物車
        List<Cart> findByUsername(@Param("username") String username);

        // 修改商品數量
        void updateQuantity(
                        @Param("username") String username,
                        @Param("productId") Long productId,
                        @Param("quantity") Integer quantity);

        // 商品數量 +1
        void increaseQuantity(
                        @Param("username") String username,
                        @Param("productId") Long productId);

        // 商品數量 -1
        void decreaseQuantity(
                        @Param("username") String username,
                        @Param("productId") Long productId);

        // 移除購物車商品
        void deleteCartItem(
                        @Param("username") String username,
                        @Param("productId") Long productId);

}