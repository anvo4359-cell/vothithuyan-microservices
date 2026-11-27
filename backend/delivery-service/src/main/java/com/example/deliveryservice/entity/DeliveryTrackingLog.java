package com.example.deliveryservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "delivery_tracking_logs")
public class DeliveryTrackingLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Trạng thái không được để trống")
    @Column(nullable = false, length = 30)
    private String status;

    @Column(length = 255)
    private String location;

    @Column(length = 255)
    private String note;

    @Column(name = "logged_at")
    private LocalDateTime loggedAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    @JsonIgnore
    private DeliveryOrder deliveryOrder;

    public DeliveryTrackingLog() {
    }

    public DeliveryTrackingLog(Long id, String status, String location, String note, DeliveryOrder deliveryOrder) {
        this.id = id;
        this.status = status;
        this.location = location;
        this.note = note;
        this.loggedAt = LocalDateTime.now();
        this.deliveryOrder = deliveryOrder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getLoggedAt() {
        return loggedAt;
    }

    public void setLoggedAt(LocalDateTime loggedAt) {
        this.loggedAt = loggedAt;
    }

    public DeliveryOrder getDeliveryOrder() {
        return deliveryOrder;
    }

    public void setDeliveryOrder(DeliveryOrder deliveryOrder) {
        this.deliveryOrder = deliveryOrder;
    }
}
