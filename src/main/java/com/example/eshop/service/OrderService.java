package com.example.eshop.service;

import com.example.eshop.entity.Order;
import com.example.eshop.entity.OrderItem;
import com.example.eshop.mapper.OrderItemMapper;
import com.example.eshop.mapper.OrderMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderService {

    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    public OrderService(
            OrderMapper orderMapper,
            OrderItemMapper orderItemMapper) {

        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;

    }

    // 建立訂單
    public Order createOrder(Order order,
            List<OrderItem> items) {

        orderMapper.insertOrder(order);

        for (OrderItem item : items) {

            item.setOrderId(order.getOrderId());

            orderItemMapper.insertOrderItem(item);

        }

        return order;

    }

    // 查詢訂單明細
    public List<OrderItem> getOrderItems(Long orderId) {

        return orderItemMapper.findByOrderId(orderId);

    }

}