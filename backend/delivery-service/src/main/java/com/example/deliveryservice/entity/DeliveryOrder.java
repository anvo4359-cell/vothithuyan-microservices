package com.example.deliveryservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "delivery_orders")
public class DeliveryOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "delivery_code", unique = true, length = 50)
    private String deliveryCode;

    @NotBlank(message = "Mã đơn hàng không được để trống")
    @Column(name = "order_code", nullable = false, length = 50)
    private String orderCode;

    @NotBlank(message = "Tên người nhận không được để trống")
    @Column(name = "customer_name", nullable = false, length = 100)
    private String customerName;

    @NotBlank(message = "Số điện thoại người nhận không được để trống")
    @Column(name = "customer_phone", nullable = false, length = 20)
    private String customerPhone;

    @NotBlank(message = "Địa chỉ giao hàng không được để trống")
    @Column(name = "delivery_address", nullable = false, length = 255)
    private String deliveryAddress;

    @Column(name = "shipper_name", length = 100)
    private String shipperName;

    @Column(name = "shipper_phone", length = 20)
    private String shipperPhone;

    @Column(nullable = false, length = 30)
    private String status = "ASSIGNED"; // ASSIGNED, PICKING_UP, DELIVERING, DELIVERED, FAILED

    @NotNull(message = "Phí giao hàng không được để trống")
    @DecimalMin(value = "0.0", message = "Phí giao hàng không được âm")
    @Column(name = "shipping_fee", nullable = false, precision = 12, scale = 2)
    private BigDecimal shippingFee;

    @Column(name = "assigned_at")
    private LocalDateTime assignedAt = LocalDateTime.now();

    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;

    @OneToMany(mappedBy = "deliveryOrder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<DeliveryTrackingLog> trackingLogs = new ArrayList<>();

    public DeliveryOrder() {
    }

    public DeliveryOrder(Long id, String deliveryCode, String orderCode, String customerName, String customerPhone, String deliveryAddress, String shipperName, String shipperPhone, String status, BigDecimal shippingFee) {
        this.id = id;
        this.deliveryCode = deliveryCode;
        this.orderCode = orderCode;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.deliveryAddress = deliveryAddress;
        this.shipperName = shipperName;
        this.shipperPhone = shipperPhone;
        this.status = status != null ? status : "ASSIGNED";
        this.shippingFee = shippingFee;
        this.assignedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeliveryCode() {
        return deliveryCode;
    }

    public void setDeliveryCode(String deliveryCode) {
        this.deliveryCode = deliveryCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getShipperName() {
        return shipperName;
    }

    public void setShipperName(String shipperName) {
        this.shipperName = shipperName;
    }

    public String getShipperPhone() {
        return shipperPhone;
    }

    public void setShipperPhone(String shipperPhone) {
        this.shipperPhone = shipperPhone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }

    public LocalDateTime getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(LocalDateTime deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public List<DeliveryTrackingLog> getTrackingLogs() {
        return trackingLogs;
    }

    public void setTrackingLogs(List<DeliveryTrackingLog> trackingLogs) {
        this.trackingLogs = trackingLogs;
    }

    public void addLog(DeliveryTrackingLog log) {
        trackingLogs.add(log);
        log.setDeliveryOrder(this);
    }
}
