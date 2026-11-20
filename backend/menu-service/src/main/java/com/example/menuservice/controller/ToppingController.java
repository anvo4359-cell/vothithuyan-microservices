package com.example.menuservice.controller;

import com.example.menuservice.entity.Topping;
import com.example.menuservice.service.ToppingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/toppings")
@CrossOrigin(origins = "*")
public class ToppingController {

    private final ToppingService toppingService;

    public ToppingController(ToppingService toppingService) {
        this.toppingService = toppingService;
    }

    @GetMapping
    public ResponseEntity<List<Topping>> getAll(@RequestParam(required = false) Boolean inStockOnly) {
        if (Boolean.TRUE.equals(inStockOnly)) {
            return ResponseEntity.ok(toppingService.getInStockToppings());
        }
        return ResponseEntity.ok(toppingService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Topping> getById(@PathVariable Long id) {
        return ResponseEntity.ok(toppingService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Topping> create(@Valid @RequestBody Topping topping) {
        Topping created = toppingService.create(topping);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Topping> update(@PathVariable Long id, @Valid @RequestBody Topping topping) {
        Topping updated = toppingService.update(id, topping);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        toppingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
