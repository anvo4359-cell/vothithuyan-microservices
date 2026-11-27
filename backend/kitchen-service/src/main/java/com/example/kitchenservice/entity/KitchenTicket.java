package com.example.kitchenservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "kitchen_tickets")
public class KitchenTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ticket_code", unique = true, length = 50)
    private String ticketCode;

    @NotBlank(message = "Mã đơn hàng không được để trống")
    @Column(name = "order_code", nullable = false, length = 50)
    private String orderCode;

    @Column(name = "branch_id", nullable = false)
    private Long branchId;

    @Column(nullable = false, length = 30)
    private String status = "RECEIVED"; // RECEIVED, IN_PROGRESS, READY, CANCELLED

    @Column(length = 20)
    private String priority = "NORMAL"; // NORMAL, HIGH, URGENT

    @Column(name = "staff_name", length = 100)
    private String staffName;

    @Column(name = "received_at")
    private LocalDateTime receivedAt = LocalDateTime.now();

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<KitchenTicketItem> items = new ArrayList<>();

    public KitchenTicket() {
    }

    public KitchenTicket(Long id, String ticketCode, String orderCode, Long branchId, String status, String priority, String staffName) {
        this.id = id;
        this.ticketCode = ticketCode;
        this.orderCode = orderCode;
        this.branchId = branchId;
        this.status = status != null ? status : "RECEIVED";
        this.priority = priority != null ? priority : "NORMAL";
        this.staffName = staffName;
        this.receivedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public LocalDateTime getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(LocalDateTime receivedAt) {
        this.receivedAt = receivedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public List<KitchenTicketItem> getItems() {
        return items;
    }

    public void setItems(List<KitchenTicketItem> items) {
        this.items = items;
    }

    public void addItem(KitchenTicketItem item) {
        items.add(item);
        item.setTicket(this);
    }
}
