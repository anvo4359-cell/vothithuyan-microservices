package com.example.branchservice.controller;

import com.example.branchservice.entity.Equipment;
import com.example.branchservice.service.EquipmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/equipments")
@CrossOrigin(origins = "*")
public class EquipmentController {

    private final EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @GetMapping
    public ResponseEntity<List<Equipment>> getAll(@RequestParam(required = false) Long branchId) {
        if (branchId != null) {
            return ResponseEntity.ok(equipmentService.getByBranch(branchId));
        }
        return ResponseEntity.ok(equipmentService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipment> getById(@PathVariable Long id) {
        return ResponseEntity.ok(equipmentService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Equipment> create(@Valid @RequestBody Equipment equipment) {
        Equipment created = equipmentService.create(equipment);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Equipment> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String status = body.get("status");
        if (status == null || status.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(equipmentService.updateStatus(id, status.trim()));
    }

    @PostMapping("/{id}/maintenance")
    public ResponseEntity<Equipment> recordMaintenance(
            @PathVariable Long id,
            @RequestBody(required = false) Map<String, String> body) {
        LocalDate date = (body != null && body.containsKey("date")) ? LocalDate.parse(body.get("date")) : LocalDate.now();
        LocalDate nextDate = (body != null && body.containsKey("nextDate")) ? LocalDate.parse(body.get("nextDate")) : date.plusMonths(3);
        return ResponseEntity.ok(equipmentService.recordMaintenance(id, date, nextDate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        equipmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
