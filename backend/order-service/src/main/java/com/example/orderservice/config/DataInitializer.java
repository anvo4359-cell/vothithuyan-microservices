package com.example.orderservice.config;

import com.example.orderservice.entity.Cart;
import com.example.orderservice.entity.CartItem;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderItem;
import com.example.orderservice.repository.CartRepository;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Configuration
public class DataInitializer implements CommandLineRunner {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    public DataInitializer(OrderRepository orderRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (orderRepository.count() == 0) {
            // Seed sample Order 1
            Order order1 = new Order(
                    null,
                    "ORD-20261120-001",
                    "Nguyễn Văn An",
                    "0909123456",
                    "123 Nguyễn Văn Cừ, Quận 5, TP.HCM",
                    1L,
                    new BigDecimal("83000"),
                    "PREPARING",
                    "MOMO"
            );
            order1.setCreatedAt(LocalDateTime.now().minusMinutes(15));

            OrderItem item1 = new OrderItem(
                    null,
                    1L,
                    "Trà sữa Oolong Nướng",
                    "L",
                    "50%",
                    "70%",
                    "Trân châu hoàng kim",
                    1,
                    new BigDecimal("43000"),
                    new BigDecimal("43000"),
                    order1
            );

            OrderItem item2 = new OrderItem(
                    null,
                    2L,
                    "Trà Đào Cam Sả",
                    "M",
                    "70%",
                    "100%",
                    "Thạch củ năng ngũ sắc",
                    1,
                    new BigDecimal("40000"),
                    new BigDecimal("40000"),
                    order1
            );

            order1.addItem(item1);
            order1.addItem(item2);
            orderRepository.save(order1);

            // Seed sample Cart
            Cart cart = new Cart(null, "guest_session_101");
            CartItem cartItem = new CartItem(
                    null,
                    1L,
                    "Trà sữa Oolong Nướng",
                    "M",
                    "100%",
                    "100%",
                    "Trân châu hoàng kim",
                    2,
                    new BigDecimal("35000"),
                    cart
            );
            cart.addItem(cartItem);
            cartRepository.save(cart);
        }
    }
}
