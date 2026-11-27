package com.example.orderservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record OrderRequest(
    String customerName,

    String customerPhone,

    String deliveryAddress,

    Long branchId,

    String paymentMethod,

    @NotEmpty(message = "Đơn hàng phải có sản phẩm")
    List<@NotNull @Valid OrderItemRequest> items
) {}
