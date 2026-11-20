package com.example.orderservice.controller;

import com.example.orderservice.entity.Cart;
import com.example.orderservice.entity.CartItem;
import com.example.orderservice.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
@CrossOrigin(origins = "*")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<Cart>> getAll() {
        return ResponseEntity.ok(cartService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cart> getById(@PathVariable Long id) {
        return ResponseEntity.ok(cartService.getById(id));
    }

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<Cart> getBySessionId(@PathVariable String sessionId) {
        return ResponseEntity.ok(cartService.getOrCreateCart(sessionId));
    }

    @PostMapping("/session/{sessionId}/items")
    public ResponseEntity<Cart> addItem(@PathVariable String sessionId, @Valid @RequestBody CartItem item) {
        Cart updatedCart = cartService.addItem(sessionId, item);
        return new ResponseEntity<>(updatedCart, HttpStatus.CREATED);
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> removeItem(@PathVariable Long itemId) {
        cartService.removeItem(itemId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/session/{sessionId}/clear")
    public ResponseEntity<Void> clearCart(@PathVariable String sessionId) {
        cartService.clearCart(sessionId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        cartService.deleteCart(id);
        return ResponseEntity.noContent().build();
    }
}
