package com.example.menuservice.repository;

import com.example.menuservice.entity.RecipeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RecipeItemRepository extends JpaRepository<RecipeItem, Long> {
    List<RecipeItem> findByProductId(Long productId);
}
