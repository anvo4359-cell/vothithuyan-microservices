package com.example.branchservice.repository;

import com.example.branchservice.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    List<Equipment> findByBranchId(Long branchId);
    List<Equipment> findByStatus(String status);
}
