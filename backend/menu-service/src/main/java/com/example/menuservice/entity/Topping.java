package com.example.menuservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Table(name = "toppings")
public class Topping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên topping không được để trống")
    @Size(max = 100, message = "Tên topping tối đa 100 ký tự")
    @Column(nullable = false, length = 100)
    private String name;

    @NotNull(message = "Giá thêm không được để trống")
    @DecimalMin(value = "0.0", message = "Giá topping phải lớn hơn hoặc bằng 0")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(name = "in_stock", nullable = false)
    private Boolean inStock = true;

    public Topping() {
    }

    public Topping(Long id, String name, BigDecimal price, Boolean inStock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.inStock = inStock != null ? inStock : true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getInStock() {
        return inStock;
    }

    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }
}
