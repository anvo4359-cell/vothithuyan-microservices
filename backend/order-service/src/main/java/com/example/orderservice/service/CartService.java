package com.example.orderservice.service;

import com.example.orderservice.entity.Cart;
import com.example.orderservice.entity.CartItem;
import com.example.orderservice.exception.ResourceNotFoundException;
import com.example.orderservice.repository.CartItemRepository;
import com.example.orderservice.repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public List<Cart> getAll() {
        return cartRepository.findAll();
    }

    public Cart getById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giỏ hàng với ID: " + id));
    }

    public Cart getOrCreateCart(String sessionId) {
        return cartRepository.findBySessionId(sessionId)
                .orElseGet(() -> {
                    Cart newCart = new Cart(null, sessionId);
                    return cartRepository.save(newCart);
                });
    }

    @Transactional
    public Cart addItem(String sessionId, CartItem item) {
        Cart cart = getOrCreateCart(sessionId);
        cart.addItem(item);
        cart.setUpdatedAt(LocalDateTime.now());
        cartItemRepository.save(item);
        return cartRepository.save(cart);
    }

    @Transactional
    public void removeItem(Long cartItemId) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy món trong giỏ hàng với ID: " + cartItemId));
        cartItemRepository.delete(item);
    }

    @Transactional
    public void clearCart(String sessionId) {
        Cart cart = getOrCreateCart(sessionId);
        cart.getItems().clear();
        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);
    }

    @Transactional
    public void deleteCart(Long id) {
        Cart cart = getById(id);
        cartRepository.delete(cart);
    }
}
