package com.example.eshop.service;

import com.example.eshop.entity.Cart;
import com.example.eshop.mapper.CartMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CartService {

    private final CartMapper cartMapper;

    public CartService(CartMapper cartMapper) {
        this.cartMapper = cartMapper;
    }

    // 查詢會員購物車所有商品
    public List<Cart> getCart(String username) {

        return cartMapper.findByUsername(username);

    }

    // 修改商品購買數量
    public void updateQuantity(
            String username,
            Long productId,
            Integer quantity) {

        // 數量不可小於等於 0
        if (quantity <= 0) {
            throw new RuntimeException("商品數量必須大於0");
        }

        cartMapper.updateQuantity(
                username,
                productId,
                quantity);
    }

    // 移除購物車商品
    public void removeCartItem(
            String username,
            Long productId) {

        cartMapper.deleteCartItem(
                username,
                productId);

    }

}