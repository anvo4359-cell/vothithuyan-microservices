package com.example.menuservice.service;

import com.example.menuservice.entity.Category;
import com.example.menuservice.entity.Product;
import com.example.menuservice.exception.ResourceNotFoundException;
import com.example.menuservice.repository.CategoryRepository;
import com.example.menuservice.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public List<Product> getActiveProducts() {
        return productRepository.findByActiveTrue();
    }

    public List<Product> getByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public List<Product> searchByName(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }

    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy món với ID: " + id));
    }

    @Transactional
    public Product create(Product product) {
        if (product.getCategory() != null && product.getCategory().getId() != null) {
            Category category = categoryRepository.findById(product.getCategory().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy danh mục ID: " + product.getCategory().getId()));
            product.setCategory(category);
        }
        if (product.getActive() == null) {
            product.setActive(true);
        }
        return productRepository.save(product);
    }

    @Transactional
    public Product update(Long id, Product updatedProduct) {
        Product existing = getById(id);
        existing.setName(updatedProduct.getName());
        existing.setDescription(updatedProduct.getDescription());
        existing.setBasePrice(updatedProduct.getBasePrice());
        existing.setImageUrl(updatedProduct.getImageUrl());
        if (updatedProduct.getActive() != null) {
            existing.setActive(updatedProduct.getActive());
        }
        if (updatedProduct.getCategory() != null && updatedProduct.getCategory().getId() != null) {
            Category category = categoryRepository.findById(updatedProduct.getCategory().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy danh mục ID: " + updatedProduct.getCategory().getId()));
            existing.setCategory(category);
        }
        return productRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        Product existing = getById(id);
        productRepository.delete(existing);
    }
}
