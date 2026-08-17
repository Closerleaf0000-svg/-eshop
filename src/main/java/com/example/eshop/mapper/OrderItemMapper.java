package com.example.eshop.mapper;

import com.example.eshop.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface OrderItemMapper {

    // 新增訂單商品
    void insertOrderItem(OrderItem orderItem);

    // 查詢訂單商品
    List<OrderItem> findByOrderId(
            @Param("orderId") Long orderId);

}