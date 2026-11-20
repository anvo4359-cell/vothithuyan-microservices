package com.example.menuservice.controller;

import com.example.menuservice.entity.RecipeItem;
import com.example.menuservice.service.RecipeItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@CrossOrigin(origins = "*")
public class RecipeItemController {

    private final RecipeItemService recipeItemService;

    public RecipeItemController(RecipeItemService recipeItemService) {
        this.recipeItemService = recipeItemService;
    }

    @GetMapping
    public ResponseEntity<List<RecipeItem>> getAll(@RequestParam(required = false) Long productId) {
        if (productId != null) {
            return ResponseEntity.ok(recipeItemService.getByProductId(productId));
        }
        return ResponseEntity.ok(recipeItemService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeItem> getById(@PathVariable Long id) {
        return ResponseEntity.ok(recipeItemService.getById(id));
    }

    @PostMapping
    public ResponseEntity<RecipeItem> create(@Valid @RequestBody RecipeItem recipeItem) {
        RecipeItem created = recipeItemService.create(recipeItem);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeItem> update(@PathVariable Long id, @Valid @RequestBody RecipeItem recipeItem) {
        RecipeItem updated = recipeItemService.update(id, recipeItem);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        recipeItemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
