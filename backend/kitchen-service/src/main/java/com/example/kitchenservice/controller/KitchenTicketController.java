package com.example.kitchenservice.controller;

import com.example.kitchenservice.entity.KitchenTicket;
import com.example.kitchenservice.service.KitchenTicketService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/kitchen/tickets")
@CrossOrigin(origins = "*")
public class KitchenTicketController {

    private final KitchenTicketService kitchenTicketService;

    public KitchenTicketController(KitchenTicketService kitchenTicketService) {
        this.kitchenTicketService = kitchenTicketService;
    }

    @GetMapping
    public ResponseEntity<List<KitchenTicket>> getAll(
            @RequestParam(required = false) Long branchId,
            @RequestParam(required = false) String status) {
        if (branchId != null) {
            return ResponseEntity.ok(kitchenTicketService.getByBranch(branchId, status));
        }
        return ResponseEntity.ok(kitchenTicketService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<KitchenTicket> getById(@PathVariable Long id) {
        return ResponseEntity.ok(kitchenTicketService.getById(id));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<KitchenTicket> getByCode(@PathVariable String code) {
        return ResponseEntity.ok(kitchenTicketService.getByCode(code));
    }

    @PostMapping
    public ResponseEntity<KitchenTicket> create(@Valid @RequestBody KitchenTicket ticket) {
        KitchenTicket created = kitchenTicketService.create(ticket);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<KitchenTicket> update(@PathVariable Long id, @Valid @RequestBody KitchenTicket ticket) {
        KitchenTicket updated = kitchenTicketService.update(id, ticket);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<KitchenTicket> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> statusBody) {
        String newStatus = statusBody.get("status");
        if (newStatus == null || newStatus.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        KitchenTicket updated = kitchenTicketService.updateStatus(id, newStatus.trim());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        kitchenTicketService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
