package com.example.kitchenservice.repository;

import com.example.kitchenservice.entity.KitchenTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KitchenTicketRepository extends JpaRepository<KitchenTicket, Long> {
    Optional<KitchenTicket> findByTicketCode(String ticketCode);
    List<KitchenTicket> findByBranchId(Long branchId);
    List<KitchenTicket> findByStatus(String status);
    List<KitchenTicket> findByBranchIdAndStatus(Long branchId, String status);
}
