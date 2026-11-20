package com.example.menuservice.service;

import com.example.menuservice.entity.Product;
import com.example.menuservice.entity.RecipeItem;
import com.example.menuservice.exception.ResourceNotFoundException;
import com.example.menuservice.repository.ProductRepository;
import com.example.menuservice.repository.RecipeItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecipeItemService {

    private final RecipeItemRepository recipeItemRepository;
    private final ProductRepository productRepository;

    public RecipeItemService(RecipeItemRepository recipeItemRepository, ProductRepository productRepository) {
        this.recipeItemRepository = recipeItemRepository;
        this.productRepository = productRepository;
    }

    public List<RecipeItem> getAll() {
        return recipeItemRepository.findAll();
    }

    public List<RecipeItem> getByProductId(Long productId) {
        return recipeItemRepository.findByProductId(productId);
    }

    public RecipeItem getById(Long id) {
        return recipeItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy công thức với ID: " + id));
    }

    @Transactional
    public RecipeItem create(RecipeItem recipeItem) {
        if (recipeItem.getProduct() == null || recipeItem.getProduct().getId() == null) {
            throw new IllegalArgumentException("Công thức pha chế bắt buộc phải gán cho một món cụ thể (product_id)");
        }
        Product product = productRepository.findById(recipeItem.getProduct().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy món với ID: " + recipeItem.getProduct().getId()));
        recipeItem.setProduct(product);
        return recipeItemRepository.save(recipeItem);
    }

    @Transactional
    public RecipeItem update(Long id, RecipeItem updatedItem) {
        RecipeItem existing = getById(id);
        existing.setIngredientName(updatedItem.getIngredientName());
        existing.setQuantity(updatedItem.getQuantity());
        existing.setUnit(updatedItem.getUnit());
        existing.setNotes(updatedItem.getNotes());
        if (updatedItem.getProduct() != null && updatedItem.getProduct().getId() != null) {
            Product product = productRepository.findById(updatedItem.getProduct().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy món với ID: " + updatedItem.getProduct().getId()));
            existing.setProduct(product);
        }
        return recipeItemRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        RecipeItem existing = getById(id);
        recipeItemRepository.delete(existing);
    }
}
