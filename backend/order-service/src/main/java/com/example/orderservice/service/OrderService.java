package com.example.orderservice.service;

import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderItem;
import com.example.orderservice.exception.ResourceNotFoundException;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public Order getById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đơn hàng với ID: " + id));
    }

    public Order getByOrderCode(String orderCode) {
        return orderRepository.findByOrderCode(orderCode)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đơn hàng với mã: " + orderCode));
    }

    public List<Order> getByStatus(String status) {
        return orderRepository.findByStatus(status);
    }

    public List<Order> getByBranchId(Long branchId) {
        return orderRepository.findByBranchId(branchId);
    }

    public List<Order> getByCustomerPhone(String phone) {
        return orderRepository.findByCustomerPhone(phone);
    }

    @Transactional
    public Order create(Order order) {
        // Sinh mã đơn hàng ngẫu nhiên theo thời gian
        if (order.getOrderCode() == null || order.getOrderCode().trim().isEmpty()) {
            String timePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            int randomPart = ThreadLocalRandom.current().nextInt(100, 999);
            order.setOrderCode("ORD-" + timePart + "-" + randomPart);
        }

        if (order.getCreatedAt() == null) {
            order.setCreatedAt(LocalDateTime.now());
        }
        if (order.getStatus() == null) {
            order.setStatus("PENDING");
        }
        if (order.getPaymentMethod() == null) {
            order.setPaymentMethod("COD");
        }

        // Tính subtotal cho từng item và cộng dồn totalAmount
        BigDecimal calculatedTotal = BigDecimal.ZERO;
        if (order.getItems() != null && !order.getItems().isEmpty()) {
            for (OrderItem item : order.getItems()) {
                item.setOrder(order);
                if (item.getUnitPrice() != null && item.getQuantity() != null) {
                    BigDecimal subtotal = item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                    item.setSubtotal(subtotal);
                    calculatedTotal = calculatedTotal.add(subtotal);
                }
            }
        }
        if (order.getTotalAmount() == null || order.getTotalAmount().compareTo(BigDecimal.ZERO) <= 0) {
            order.setTotalAmount(calculatedTotal);
        }

        return orderRepository.save(order);
    }

    @Transactional
    public Order updateStatus(Long id, String status) {
        Order existing = getById(id);
        existing.setStatus(status.toUpperCase());
        return orderRepository.save(existing);
    }

    @Transactional
    public Order update(Long id, Order updatedOrder) {
        Order existing = getById(id);
        existing.setCustomerName(updatedOrder.getCustomerName());
        existing.setCustomerPhone(updatedOrder.getCustomerPhone());
        existing.setDeliveryAddress(updatedOrder.getDeliveryAddress());
        if (updatedOrder.getBranchId() != null) {
            existing.setBranchId(updatedOrder.getBranchId());
        }
        if (updatedOrder.getStatus() != null) {
            existing.setStatus(updatedOrder.getStatus());
        }
        if (updatedOrder.getPaymentMethod() != null) {
            existing.setPaymentMethod(updatedOrder.getPaymentMethod());
        }
        if (updatedOrder.getTotalAmount() != null) {
            existing.setTotalAmount(updatedOrder.getTotalAmount());
        }
        return orderRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        Order existing = getById(id);
        orderRepository.delete(existing);
    }
}
