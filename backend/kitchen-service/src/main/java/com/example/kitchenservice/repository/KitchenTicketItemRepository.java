package com.example.kitchenservice.repository;

import com.example.kitchenservice.entity.KitchenTicketItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KitchenTicketItemRepository extends JpaRepository<KitchenTicketItem, Long> {
    List<KitchenTicketItem> findByTicketId(Long ticketId);
}
