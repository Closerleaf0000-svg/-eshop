package com.example.eshop.mapper;

import com.example.eshop.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

    // 建立訂單
    void insertOrder(Order order);

    // 查詢訂單
    Order findByOrderId(Long orderId);

}