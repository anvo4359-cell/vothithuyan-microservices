package com.example.branchservice.controller;

import com.example.branchservice.entity.Branch;
import com.example.branchservice.service.BranchService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branches")
@CrossOrigin(origins = "*")
public class BranchController {

    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @GetMapping
    public ResponseEntity<List<Branch>> getAll(@RequestParam(required = false) Boolean activeOnly) {
        if (Boolean.TRUE.equals(activeOnly)) {
            return ResponseEntity.ok(branchService.getActiveBranches());
        }
        return ResponseEntity.ok(branchService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Branch> getById(@PathVariable Long id) {
        return ResponseEntity.ok(branchService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Branch> create(@Valid @RequestBody Branch branch) {
        Branch created = branchService.create(branch);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Branch> update(@PathVariable Long id, @Valid @RequestBody Branch branch) {
        Branch updated = branchService.update(id, branch);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        branchService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
