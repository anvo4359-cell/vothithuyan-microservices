package com.example.orderservice.controller;

import com.example.orderservice.dto.ProductResponse;
import com.example.orderservice.entity.Order;
import com.example.orderservice.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Endpoint kiểm tra giao tiếp đồng bộ giữa hai Microservices bằng RestClient
    @GetMapping("/product/{id}")
    public ProductResponse getProduct(@PathVariable Long id) {
        return orderService.getProduct(id);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAll(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long branchId,
            @RequestParam(required = false) String phone) {

        if (status != null && !status.trim().isEmpty()) {
            return ResponseEntity.ok(orderService.getByStatus(status.trim().toUpperCase()));
        }
        if (branchId != null) {
            return ResponseEntity.ok(orderService.getByBranchId(branchId));
        }
        if (phone != null && !phone.trim().isEmpty()) {
            return ResponseEntity.ok(orderService.getByCustomerPhone(phone.trim()));
        }
        return ResponseEntity.ok(orderService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getById(id));
    }

    @GetMapping("/code/{orderCode}")
    public ResponseEntity<Order> getByCode(@PathVariable String orderCode) {
        return ResponseEntity.ok(orderService.getByOrderCode(orderCode));
    }

    @PostMapping
    public ResponseEntity<Order> create(@Valid @RequestBody Order order) {
        Order created = orderService.create(order);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> update(@PathVariable Long id, @Valid @RequestBody Order order) {
        Order updated = orderService.update(id, order);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Order> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> statusBody) {
        String newStatus = statusBody.get("status");
        if (newStatus == null || newStatus.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Order updated = orderService.updateStatus(id, newStatus.trim());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
