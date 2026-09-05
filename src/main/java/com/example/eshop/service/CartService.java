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

    // 加入購物車
    public void addToCart(Cart cart) {

        // 商品數量不可小於等於 0
        if (cart.getQuantity() <= 0) {
            throw new RuntimeException("商品數量必須大於0");
        }

        // 查詢目前會員的購物車中是否已經有這個商品
        Cart existingCart = cartMapper.findByMemberIdAndProductId(
                cart.getMemberId(),
                cart.getProductId());

        // 如果已經有這個商品
        if (existingCart != null) {

            // 原本數量 + 新加入的數量
            int newQuantity = existingCart.getQuantity()
                    + cart.getQuantity();

            // 更新數量
            cartMapper.updateQuantity(
                    cart.getMemberId(),
                    cart.getProductId(),
                    newQuantity);

        } else {

            // 如果沒有這個商品，才新增
            cartMapper.insert(cart);
        }
    }

    // 查詢會員購物車所有商品
    public List<Cart> getCart(Long memberId) {

        return cartMapper.findByMemberId(memberId);
    }

    // 修改商品購買數量
    public void updateQuantity(
            Long memberId,
            Long productId,
            Integer quantity) {

        // 數量不可小於等於 0
        if (quantity <= 0) {
            throw new RuntimeException("商品數量必須大於0");
        }

        cartMapper.updateQuantity(
                memberId,
                productId,
                quantity);
    }

    // 移除購物車商品
    public void removeCartItem(
            Long memberId,
            Long productId) {

        cartMapper.deleteCartItem(
                memberId,
                productId);
    }
}