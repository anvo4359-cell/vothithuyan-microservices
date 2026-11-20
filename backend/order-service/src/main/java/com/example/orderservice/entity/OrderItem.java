package com.example.orderservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "ID sản phẩm không được để trống")
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Column(name = "product_name", nullable = false, length = 150)
    private String productName;

    @Column(length = 20)
    private String size = "M"; // M, L

    @Column(name = "sugar_level", length = 20)
    private String sugarLevel = "100%"; // 0%, 30%, 50%, 70%, 100%

    @Column(name = "ice_level", length = 20)
    private String iceLevel = "100%"; // 0%, 50%, 100%

    @Column(length = 255)
    private String toppings; // vd: Trân châu hoàng kim, Pudding

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng ít nhất là 1")
    @Column(nullable = false)
    private Integer quantity;

    @NotNull(message = "Đơn giá không được để trống")
    @DecimalMin(value = "0.0", message = "Đơn giá phải lớn hơn hoặc bằng 0")
    @Column(name = "unit_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal unitPrice;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;

    public OrderItem() {
    }

    public OrderItem(Long id, Long productId, String productName, String size, String sugarLevel, String iceLevel, String toppings, Integer quantity, BigDecimal unitPrice, BigDecimal subtotal, Order order) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.size = size != null ? size : "M";
        this.sugarLevel = sugarLevel != null ? sugarLevel : "100%";
        this.iceLevel = iceLevel != null ? iceLevel : "100%";
        this.toppings = toppings;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = subtotal != null ? subtotal : (unitPrice != null && quantity != null ? unitPrice.multiply(BigDecimal.valueOf(quantity)) : BigDecimal.ZERO);
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSugarLevel() {
        return sugarLevel;
    }

    public void setSugarLevel(String sugarLevel) {
        this.sugarLevel = sugarLevel;
    }

    public String getIceLevel() {
        return iceLevel;
    }

    public void setIceLevel(String iceLevel) {
        this.iceLevel = iceLevel;
    }

    public String getToppings() {
        return toppings;
    }

    public void setToppings(String toppings) {
        this.toppings = toppings;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
