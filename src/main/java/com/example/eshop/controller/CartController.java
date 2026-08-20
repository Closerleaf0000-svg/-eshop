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

    // 加入購物車
    @PostMapping
    public void addToCart(
            @RequestBody Cart cart,
            HttpSession session) {

        // 從 Session 取得目前登入會員的 ID
        Long memberId = (Long) session.getAttribute("memberId");

        // 將會員 ID 放入購物車資料
        cart.setMemberId(memberId);

        // 加入購物車
        cartService.addToCart(cart);
    }

    // 查詢目前登入會員自己的購物車
    @GetMapping
    public List<Cart> getMyCart(
            HttpSession session) {

        // 從 Session 取得目前登入會員的 ID
        Long memberId = (Long) session.getAttribute("memberId");

        // 查詢該會員的購物車
        return cartService.getCart(memberId);
    }

    // 修改商品數量
    @PutMapping
    public void updateQuantity(
            @RequestParam Long productId,
            @RequestParam Integer quantity,
            HttpSession session) {

        // 從 Session 取得目前登入會員的 ID
        Long memberId = (Long) session.getAttribute("memberId");

        // 修改該會員指定商品的數量
        cartService.updateQuantity(
                memberId,
                productId,
                quantity);
    }

    // 移除購物車商品
    @DeleteMapping("/{productId}")
    public String removeCartItem(
            @PathVariable Long productId,
            HttpSession session) {

        // 從 Session 取得目前登入會員的 ID
        Long memberId = (Long) session.getAttribute("memberId");

        // 移除該會員指定的商品
        cartService.removeCartItem(
                memberId,
                productId);

        return "商品已移除";
    }
}