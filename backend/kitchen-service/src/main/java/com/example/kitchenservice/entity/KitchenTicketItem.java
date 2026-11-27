package com.example.kitchenservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "kitchen_ticket_items")
public class KitchenTicketItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên món không được để trống")
    @Column(name = "product_name", nullable = false, length = 150)
    private String productName;

    @Column(length = 20)
    private String size = "M";

    @Column(name = "sugar_level", length = 20)
    private String sugarLevel = "100%";

    @Column(name = "ice_level", length = 20)
    private String iceLevel = "100%";

    @Column(length = 255)
    private String toppings;

    @Column(name = "recipe_details", length = 500)
    private String recipeDetails; // Chi tiết định lượng chuẩn: Cốt trà 150ml, bột béo 35g...

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng tối thiểu là 1")
    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "item_status", length = 30)
    private String itemStatus = "PENDING"; // PENDING, PREPARING, FINISHED

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id")
    @JsonIgnore
    private KitchenTicket ticket;

    public KitchenTicketItem() {
    }

    public KitchenTicketItem(Long id, String productName, String size, String sugarLevel, String iceLevel, String toppings, String recipeDetails, Integer quantity, String itemStatus, KitchenTicket ticket) {
        this.id = id;
        this.productName = productName;
        this.size = size != null ? size : "M";
        this.sugarLevel = sugarLevel != null ? sugarLevel : "100%";
        this.iceLevel = iceLevel != null ? iceLevel : "100%";
        this.toppings = toppings;
        this.recipeDetails = recipeDetails;
        this.quantity = quantity;
        this.itemStatus = itemStatus != null ? itemStatus : "PENDING";
        this.ticket = ticket;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRecipeDetails() {
        return recipeDetails;
    }

    public void setRecipeDetails(String recipeDetails) {
        this.recipeDetails = recipeDetails;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    public KitchenTicket getTicket() {
        return ticket;
    }

    public void setTicket(KitchenTicket ticket) {
        this.ticket = ticket;
    }
}
