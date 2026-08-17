package com.example.eshop.controller;

import com.example.eshop.entity.Order;
import com.example.eshop.entity.OrderItem;
import com.example.eshop.service.OrderService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/order")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // 結帳建立訂單
    @PostMapping("/checkout")
    public Order checkout(
            @RequestBody Order order) {

        return orderService.createOrder(
                order,
                null);
    }

    // 查詢訂單商品
    @GetMapping("/{orderId}/items")
    public List<OrderItem> getItems(
            @PathVariable Long orderId) {
        return orderService.getOrderItems(orderId);
    }
}