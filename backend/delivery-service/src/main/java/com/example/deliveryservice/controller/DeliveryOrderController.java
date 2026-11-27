package com.example.deliveryservice.controller;

import com.example.deliveryservice.entity.DeliveryOrder;
import com.example.deliveryservice.service.DeliveryOrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/deliveries")
@CrossOrigin(origins = "*")
public class DeliveryOrderController {

    private final DeliveryOrderService deliveryOrderService;

    public DeliveryOrderController(DeliveryOrderService deliveryOrderService) {
        this.deliveryOrderService = deliveryOrderService;
    }

    @GetMapping
    public ResponseEntity<List<DeliveryOrder>> getAll(@RequestParam(required = false) String status) {
        if (status != null && !status.trim().isEmpty()) {
            return ResponseEntity.ok(deliveryOrderService.getByStatus(status.trim().toUpperCase()));
        }
        return ResponseEntity.ok(deliveryOrderService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryOrder> getById(@PathVariable Long id) {
        return ResponseEntity.ok(deliveryOrderService.getById(id));
    }

    @GetMapping("/order/{orderCode}")
    public ResponseEntity<DeliveryOrder> getByOrderCode(@PathVariable String orderCode) {
        return ResponseEntity.ok(deliveryOrderService.getByOrderCode(orderCode));
    }

    @PostMapping
    public ResponseEntity<DeliveryOrder> create(@Valid @RequestBody DeliveryOrder deliveryOrder) {
        DeliveryOrder created = deliveryOrderService.create(deliveryOrder);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeliveryOrder> update(@PathVariable Long id, @Valid @RequestBody DeliveryOrder deliveryOrder) {
        DeliveryOrder updated = deliveryOrderService.update(id, deliveryOrder);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<DeliveryOrder> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String status = body.get("status");
        String location = body.get("location");
        String note = body.get("note");
        if (status == null || status.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        DeliveryOrder updated = deliveryOrderService.updateStatus(id, status, location, note);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deliveryOrderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
