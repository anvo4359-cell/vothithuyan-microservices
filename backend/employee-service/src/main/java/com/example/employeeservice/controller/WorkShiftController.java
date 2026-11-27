package com.example.employeeservice.controller;

import com.example.employeeservice.entity.WorkShift;
import com.example.employeeservice.service.WorkShiftService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/shifts")
@CrossOrigin(origins = "*")
public class WorkShiftController {

    private final WorkShiftService workShiftService;

    public WorkShiftController(WorkShiftService workShiftService) {
        this.workShiftService = workShiftService;
    }

    @GetMapping
    public ResponseEntity<List<WorkShift>> getAll(
            @RequestParam(required = false) Long employeeId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        if (employeeId != null) {
            return ResponseEntity.ok(workShiftService.getByEmployeeId(employeeId));
        }
        if (date != null) {
            return ResponseEntity.ok(workShiftService.getByDate(date));
        }
        return ResponseEntity.ok(workShiftService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkShift> getById(@PathVariable Long id) {
        return ResponseEntity.ok(workShiftService.getById(id));
    }

    @PostMapping
    public ResponseEntity<WorkShift> create(@Valid @RequestBody WorkShift shift) {
        WorkShift created = workShiftService.create(shift);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/check-in")
    public ResponseEntity<WorkShift> checkIn(@PathVariable Long id) {
        return ResponseEntity.ok(workShiftService.checkIn(id));
    }

    @PostMapping("/{id}/check-out")
    public ResponseEntity<WorkShift> checkOut(@PathVariable Long id) {
        return ResponseEntity.ok(workShiftService.checkOut(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        workShiftService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
