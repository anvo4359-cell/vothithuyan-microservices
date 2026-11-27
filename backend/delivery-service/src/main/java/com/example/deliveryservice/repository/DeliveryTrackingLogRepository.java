package com.example.deliveryservice.repository;

import com.example.deliveryservice.entity.DeliveryTrackingLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryTrackingLogRepository extends JpaRepository<DeliveryTrackingLog, Long> {
    List<DeliveryTrackingLog> findByDeliveryOrderId(Long deliveryOrderId);
}
