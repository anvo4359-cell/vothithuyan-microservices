package com.example.orderservice.dto;

import java.math.BigDecimal;

public record OrderItemResponse(
    Long id,
    Long productId,
    String productName,
    String size,
    String sugarLevel,
    String iceLevel,
    String toppings,
    Integer quantity,
    BigDecimal unitPrice,
    BigDecimal subtotal
) {}
