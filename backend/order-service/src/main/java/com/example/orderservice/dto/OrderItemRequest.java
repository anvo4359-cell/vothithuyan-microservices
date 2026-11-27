package com.example.orderservice.dto;

import jakarta.validation.constraints.*;

public record OrderItemRequest(
    @NotNull(message = "Mã sản phẩm không được để trống")
    @Positive(message = "Mã sản phẩm phải là số dương")
    Long productId,

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng phải lớn hơn 0")
    Integer quantity,

    String size,

    String sugarLevel,

    String iceLevel,

    String toppings
) {}
