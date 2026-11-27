package com.example.branchservice.service;

import com.example.branchservice.entity.Branch;
import com.example.branchservice.entity.Equipment;
import com.example.branchservice.exception.ResourceNotFoundException;
import com.example.branchservice.repository.BranchRepository;
import com.example.branchservice.repository.EquipmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final BranchRepository branchRepository;

    public EquipmentService(EquipmentRepository equipmentRepository, BranchRepository branchRepository) {
        this.equipmentRepository = equipmentRepository;
        this.branchRepository = branchRepository;
    }

    public List<Equipment> getAll() {
        return equipmentRepository.findAll();
    }

    public Equipment getById(Long id) {
        return equipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy thiết bị với ID: " + id));
    }

    public List<Equipment> getByBranch(Long branchId) {
        return equipmentRepository.findByBranchId(branchId);
    }

    @Transactional
    public Equipment create(Equipment equipment) {
        if (equipment.getBranch() == null || equipment.getBranch().getId() == null) {
            throw new IllegalArgumentException("Thiết bị bắt buộc phải gán cho chi nhánh");
        }
        Branch branch = branchRepository.findById(equipment.getBranch().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy chi nhánh ID: " + equipment.getBranch().getId()));
        equipment.setBranch(branch);
        if (equipment.getStatus() == null) {
            equipment.setStatus("OPERATIONAL");
        }
        return equipmentRepository.save(equipment);
    }

    @Transactional
    public Equipment updateStatus(Long id, String status) {
        Equipment existing = getById(id);
        existing.setStatus(status.toUpperCase());
        return equipmentRepository.save(existing);
    }

    @Transactional
    public Equipment recordMaintenance(Long id, LocalDate maintenanceDate, LocalDate nextDate) {
        Equipment existing = getById(id);
        existing.setLastMaintenance(maintenanceDate != null ? maintenanceDate : LocalDate.now());
        if (nextDate != null) {
            existing.setNextMaintenance(nextDate);
        }
        existing.setStatus("OPERATIONAL");
        return equipmentRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        Equipment existing = getById(id);
        equipmentRepository.delete(existing);
    }
}
