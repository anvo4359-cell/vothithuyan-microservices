package com.example.deliveryservice.service;

import com.example.deliveryservice.entity.DeliveryOrder;
import com.example.deliveryservice.entity.DeliveryTrackingLog;
import com.example.deliveryservice.exception.ResourceNotFoundException;
import com.example.deliveryservice.repository.DeliveryOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class DeliveryOrderService {

    private final DeliveryOrderRepository deliveryOrderRepository;

    public DeliveryOrderService(DeliveryOrderRepository deliveryOrderRepository) {
        this.deliveryOrderRepository = deliveryOrderRepository;
    }

    public List<DeliveryOrder> getAll() {
        return deliveryOrderRepository.findAll();
    }

    public DeliveryOrder getById(Long id) {
        return deliveryOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đơn vận chuyển ID: " + id));
    }

    public DeliveryOrder getByOrderCode(String orderCode) {
        return deliveryOrderRepository.findByOrderCode(orderCode)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đơn vận chuyển theo mã đơn: " + orderCode));
    }

    public List<DeliveryOrder> getByStatus(String status) {
        return deliveryOrderRepository.findByStatus(status);
    }

    @Transactional
    public DeliveryOrder create(DeliveryOrder deliveryOrder) {
        if (deliveryOrder.getDeliveryCode() == null || deliveryOrder.getDeliveryCode().trim().isEmpty()) {
            String timePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss"));
            int randomPart = ThreadLocalRandom.current().nextInt(100, 999);
            deliveryOrder.setDeliveryCode("SHIP-" + timePart + "-" + randomPart);
        }
        if (deliveryOrder.getStatus() == null) {
            deliveryOrder.setStatus("ASSIGNED");
        }
        if (deliveryOrder.getAssignedAt() == null) {
            deliveryOrder.setAssignedAt(LocalDateTime.now());
        }

        DeliveryTrackingLog initialLog = new DeliveryTrackingLog(
                null,
                deliveryOrder.getStatus(),
                "Chi nhánh xuất hàng",
                "Đơn hàng đã được điều phối cho shipper",
                deliveryOrder
        );
        deliveryOrder.addLog(initialLog);

        return deliveryOrderRepository.save(deliveryOrder);
    }

    @Transactional
    public DeliveryOrder updateStatus(Long id, String status, String location, String note) {
        DeliveryOrder existing = getById(id);
        existing.setStatus(status.toUpperCase());
        if ("DELIVERED".equalsIgnoreCase(status)) {
            existing.setDeliveredAt(LocalDateTime.now());
        }

        DeliveryTrackingLog newLog = new DeliveryTrackingLog(
                null,
                existing.getStatus(),
                location != null ? location : "Đang di chuyển trên đường",
                note != null ? note : "Cập nhật tiến trình giao hàng",
                existing
        );
        existing.addLog(newLog);

        return deliveryOrderRepository.save(existing);
    }

    @Transactional
    public DeliveryOrder update(Long id, DeliveryOrder updated) {
        DeliveryOrder existing = getById(id);
        existing.setCustomerName(updated.getCustomerName());
        existing.setCustomerPhone(updated.getCustomerPhone());
        existing.setDeliveryAddress(updated.getDeliveryAddress());
        existing.setShipperName(updated.getShipperName());
        existing.setShipperPhone(updated.getShipperPhone());
        return deliveryOrderRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        DeliveryOrder existing = getById(id);
        deliveryOrderRepository.delete(existing);
    }
}
