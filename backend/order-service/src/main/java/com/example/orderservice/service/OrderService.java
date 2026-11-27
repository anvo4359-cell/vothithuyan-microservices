package com.example.orderservice.service;

import com.example.orderservice.client.ProductClient;
import com.example.orderservice.dto.OrderItemRequest;
import com.example.orderservice.dto.OrderItemResponse;
import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.dto.ProductResponse;
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
    private final ProductClient productClient;

    public OrderService(OrderRepository orderRepository, ProductClient productClient) {
        this.orderRepository = orderRepository;
        this.productClient = productClient;
    }

    public ProductResponse getProduct(Long productId) {
        return productClient.getById(productId);
    }

    public OrderResponse toOrderResponse(Order order) {
        List<OrderItemResponse> itemResponses = order.getItems() != null ? order.getItems().stream().map(item ->
                new OrderItemResponse(
                        item.getId(),
                        item.getProductId(),
                        item.getProductName(),
                        item.getSize(),
                        item.getSugarLevel(),
                        item.getIceLevel(),
                        item.getToppings(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getSubtotal()
                )
        ).toList() : List.of();

        return new OrderResponse(
                order.getId(),
                order.getOrderCode(),
                order.getCustomerName(),
                order.getCustomerPhone(),
                order.getDeliveryAddress(),
                order.getBranchId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getPaymentMethod(),
                itemResponses
        );
    }

    public List<OrderResponse> getAll() {
        return orderRepository.findAll()
                .stream()
                .map(this::toOrderResponse)
                .toList();
    }

    public OrderResponse getById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đơn hàng với ID: " + id));
        return toOrderResponse(order);
    }

    public OrderResponse getByOrderCode(String orderCode) {
        Order order = orderRepository.findByOrderCode(orderCode)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đơn hàng với mã: " + orderCode));
        return toOrderResponse(order);
    }

    public List<OrderResponse> getByStatus(String status) {
        return orderRepository.findByStatus(status)
                .stream()
                .map(this::toOrderResponse)
                .toList();
    }

    public List<OrderResponse> getByBranchId(Long branchId) {
        return orderRepository.findByBranchId(branchId)
                .stream()
                .map(this::toOrderResponse)
                .toList();
    }

    public List<OrderResponse> getByCustomerPhone(String phone) {
        return orderRepository.findByCustomerPhone(phone)
                .stream()
                .map(this::toOrderResponse)
                .toList();
    }

    /**
     * Quy trình nghiệp vụ liên Microservice (Câu C - Lab 03):
     * 1. Tiếp nhận danh sách sản phẩm và số lượng từ OrderRequest.
     * 2. Gọi Menu/Product Service qua RestClient để lấy thông tin và đơn giá mới nhất.
     * 3. Tính thành tiền (subtotal) cho từng món và tổng tiền (totalAmount) của toàn bộ đơn hàng.
     * 4. Lưu thông tin đơn hàng và chi tiết các món vào SQL Server.
     * 5. Trả về kết quả thông qua OrderResponse.
     */
    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        Order order = new Order();

        String timePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        int randomPart = ThreadLocalRandom.current().nextInt(100, 999);
        order.setOrderCode("ORD-" + timePart + "-" + randomPart);

        order.setCustomerName(request.customerName() != null && !request.customerName().trim().isEmpty() ? request.customerName() : "Khách hàng online");
        order.setCustomerPhone(request.customerPhone() != null && !request.customerPhone().trim().isEmpty() ? request.customerPhone() : "0900000000");
        order.setDeliveryAddress(request.deliveryAddress());
        order.setBranchId(request.branchId() != null ? request.branchId() : 1L);
        order.setPaymentMethod(request.paymentMethod() != null ? request.paymentMethod() : "COD");
        order.setStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());

        for (OrderItemRequest itemReq : request.items()) {
            // 1. Gọi Product Service để lấy thông tin món và đơn giá
            ProductResponse product = productClient.getById(itemReq.productId());

            // 2. Tính thành tiền của từng món
            BigDecimal unitPrice = product.price();
            BigDecimal amount = unitPrice.multiply(BigDecimal.valueOf(itemReq.quantity()));

            // 3. Tính tổng tiền đơn hàng
            totalAmount = totalAmount.add(amount);

            // 4. Tạo OrderItem tương ứng
            OrderItem item = new OrderItem();
            item.setProductId(product.id());
            item.setProductName(product.name());
            item.setQuantity(itemReq.quantity());
            item.setUnitPrice(unitPrice);
            item.setSubtotal(amount);
            item.setSize(itemReq.size() != null ? itemReq.size() : "M");
            item.setSugarLevel(itemReq.sugarLevel() != null ? itemReq.sugarLevel() : "100%");
            item.setIceLevel(itemReq.iceLevel() != null ? itemReq.iceLevel() : "100%");
            item.setToppings(itemReq.toppings());

            order.addItem(item);
        }

        // Cập nhật tổng tiền đơn hàng
        order.setTotalAmount(totalAmount);

        // Lưu Order và OrderItem vào Database
        Order savedOrder = orderRepository.save(order);

        // Trả về kết quả thông qua OrderResponse
        return toOrderResponse(savedOrder);
    }

    @Transactional
    public OrderResponse updateStatus(Long id, String status) {
        Order existing = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đơn hàng với ID: " + id));
        existing.setStatus(status.toUpperCase());
        return toOrderResponse(orderRepository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        Order existing = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đơn hàng với ID: " + id));
        orderRepository.delete(existing);
    }
}
