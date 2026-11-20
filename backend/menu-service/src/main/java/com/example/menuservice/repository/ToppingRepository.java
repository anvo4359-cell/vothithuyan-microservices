package com.example.menuservice.repository;

import com.example.menuservice.entity.Topping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ToppingRepository extends JpaRepository<Topping, Long> {
    List<Topping> findByInStockTrue();
}
