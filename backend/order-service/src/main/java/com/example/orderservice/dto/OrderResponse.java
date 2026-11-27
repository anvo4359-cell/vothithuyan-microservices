package com.example.orderservice.dto;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponse(
    Long id,
    String orderCode,
    String customerName,
    String customerPhone,
    String deliveryAddress,
    Long branchId,
    BigDecimal totalAmount,
    String status,
    String paymentMethod,
    List<OrderItemResponse> items
) {}
