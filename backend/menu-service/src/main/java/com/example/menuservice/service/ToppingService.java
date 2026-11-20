package com.example.menuservice.service;

import com.example.menuservice.entity.Topping;
import com.example.menuservice.exception.ResourceNotFoundException;
import com.example.menuservice.repository.ToppingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ToppingService {

    private final ToppingRepository toppingRepository;

    public ToppingService(ToppingRepository toppingRepository) {
        this.toppingRepository = toppingRepository;
    }

    public List<Topping> getAll() {
        return toppingRepository.findAll();
    }

    public List<Topping> getInStockToppings() {
        return toppingRepository.findByInStockTrue();
    }

    public Topping getById(Long id) {
        return toppingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy topping với ID: " + id));
    }

    @Transactional
    public Topping create(Topping topping) {
        if (topping.getInStock() == null) {
            topping.setInStock(true);
        }
        return toppingRepository.save(topping);
    }

    @Transactional
    public Topping update(Long id, Topping updatedTopping) {
        Topping existing = getById(id);
        existing.setName(updatedTopping.getName());
        existing.setPrice(updatedTopping.getPrice());
        if (updatedTopping.getInStock() != null) {
            existing.setInStock(updatedTopping.getInStock());
        }
        return toppingRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        Topping existing = getById(id);
        toppingRepository.delete(existing);
    }
}
