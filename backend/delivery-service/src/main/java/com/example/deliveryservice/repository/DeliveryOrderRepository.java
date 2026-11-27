package com.example.deliveryservice.repository;

import com.example.deliveryservice.entity.DeliveryOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryOrderRepository extends JpaRepository<DeliveryOrder, Long> {
    Optional<DeliveryOrder> findByDeliveryCode(String deliveryCode);
    Optional<DeliveryOrder> findByOrderCode(String orderCode);
    List<DeliveryOrder> findByStatus(String status);
    List<DeliveryOrder> findByShipperPhone(String shipperPhone);
}
