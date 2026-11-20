package com.example.menuservice.service;

import com.example.menuservice.entity.Category;
import com.example.menuservice.exception.ResourceNotFoundException;
import com.example.menuservice.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public List<Category> getActiveCategories() {
        return categoryRepository.findByActiveTrue();
    }

    public Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy danh mục với ID: " + id));
    }

    @Transactional
    public Category create(Category category) {
        if (category.getActive() == null) {
            category.setActive(true);
        }
        return categoryRepository.save(category);
    }

    @Transactional
    public Category update(Long id, Category updatedCategory) {
        Category existing = getById(id);
        existing.setName(updatedCategory.getName());
        existing.setDescription(updatedCategory.getDescription());
        if (updatedCategory.getActive() != null) {
            existing.setActive(updatedCategory.getActive());
        }
        return categoryRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        Category existing = getById(id);
        categoryRepository.delete(existing);
    }
}
