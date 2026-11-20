package com.example.menuservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "recipe_items")
public class RecipeItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên nguyên liệu không được để trống")
    @Size(max = 100, message = "Tên nguyên liệu tối đa 100 ký tự")
    @Column(name = "ingredient_name", nullable = false, length = 100)
    private String ingredientName;

    @NotNull(message = "Định lượng không được để trống")
    @DecimalMin(value = "0.01", message = "Định lượng phải lớn hơn 0")
    @Column(nullable = false)
    private Double quantity;

    @NotBlank(message = "Đơn vị tính không được để trống")
    @Size(max = 30, message = "Đơn vị tính tối đa 30 ký tự")
    @Column(nullable = false, length = 30)
    private String unit; // ml, gram, muỗng, lát...

    @Column(length = 255)
    private String notes; // Ví dụ: "Lắc đều 15s trước khi rót vào ly"

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public RecipeItem() {
    }

    public RecipeItem(Long id, String ingredientName, Double quantity, String unit, String notes, Product product) {
        this.id = id;
        this.ingredientName = ingredientName;
        this.quantity = quantity;
        this.unit = unit;
        this.notes = notes;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
