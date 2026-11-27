package com.example.deliveryservice.config;

import com.example.deliveryservice.entity.DeliveryOrder;
import com.example.deliveryservice.repository.DeliveryOrderRepository;
import com.example.deliveryservice.service.DeliveryOrderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataInitializer implements CommandLineRunner {

    private final DeliveryOrderRepository deliveryOrderRepository;
    private final DeliveryOrderService deliveryOrderService;

    public DataInitializer(DeliveryOrderRepository deliveryOrderRepository, DeliveryOrderService deliveryOrderService) {
        this.deliveryOrderRepository = deliveryOrderRepository;
        this.deliveryOrderService = deliveryOrderService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (deliveryOrderRepository.count() == 0) {
            DeliveryOrder order = new DeliveryOrder(
                    null,
                    "SHIP-260101-101",
                    "ORD-20261120-001",
                    "Nguyễn Văn An",
                    "0909123456",
                    "123 Nguyễn Văn Cừ, Quận 5, TP.HCM",
                    "Shipper Hoàng",
                    "0933112233",
                    "DELIVERING",
                    new BigDecimal("15000")
            );
            deliveryOrderService.create(order);
        }
    }
}
