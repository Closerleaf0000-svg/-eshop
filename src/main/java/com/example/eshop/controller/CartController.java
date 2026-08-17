package com.example.eshop.controller;

import com.example.eshop.entity.Cart;
import com.example.eshop.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // 查詢自己的購物車
    @GetMapping
    public List<Cart> getMyCart(HttpSession session) {

        String username = (String) session.getAttribute("username");

        return cartService.getCart(username);

    }

    // 修改商品數量
    @PutMapping
    public void updateQuantity(
            @RequestParam Long productId,
            @RequestParam Integer quantity,
            HttpSession session) {

        String username = (String) session.getAttribute("username");

        cartService.updateQuantity(
                username,
                productId,
                quantity);
    }

    // 移除購物車商品
    @DeleteMapping("/{productId}")
    public String removeCartItem(
            @PathVariable Long productId,
            HttpSession session) {

        String username = (String) session.getAttribute("username");

        cartService.removeCartItem(
                username,
                productId);

        return "商品已移除";

    }
}