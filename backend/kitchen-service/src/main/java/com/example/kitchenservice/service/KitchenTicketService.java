package com.example.kitchenservice.service;

import com.example.kitchenservice.entity.KitchenTicket;
import com.example.kitchenservice.entity.KitchenTicketItem;
import com.example.kitchenservice.exception.ResourceNotFoundException;
import com.example.kitchenservice.repository.KitchenTicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class KitchenTicketService {

    private final KitchenTicketRepository kitchenTicketRepository;

    public KitchenTicketService(KitchenTicketRepository kitchenTicketRepository) {
        this.kitchenTicketRepository = kitchenTicketRepository;
    }

    public List<KitchenTicket> getAll() {
        return kitchenTicketRepository.findAll();
    }

    public KitchenTicket getById(Long id) {
        return kitchenTicketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phiếu bếp với ID: " + id));
    }

    public KitchenTicket getByCode(String code) {
        return kitchenTicketRepository.findByTicketCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phiếu bếp với mã: " + code));
    }

    public List<KitchenTicket> getByBranch(Long branchId, String status) {
        if (status != null && !status.trim().isEmpty()) {
            return kitchenTicketRepository.findByBranchIdAndStatus(branchId, status.trim().toUpperCase());
        }
        return kitchenTicketRepository.findByBranchId(branchId);
    }

    @Transactional
    public KitchenTicket create(KitchenTicket ticket) {
        if (ticket.getTicketCode() == null || ticket.getTicketCode().trim().isEmpty()) {
            String timePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            int randomPart = ThreadLocalRandom.current().nextInt(100, 999);
            ticket.setTicketCode("KDS-" + timePart + "-" + randomPart);
        }
        if (ticket.getStatus() == null) {
            ticket.setStatus("RECEIVED");
        }
        if (ticket.getReceivedAt() == null) {
            ticket.setReceivedAt(LocalDateTime.now());
        }
        if (ticket.getItems() != null) {
            for (KitchenTicketItem item : ticket.getItems()) {
                item.setTicket(ticket);
            }
        }
        return kitchenTicketRepository.save(ticket);
    }

    @Transactional
    public KitchenTicket updateStatus(Long id, String status) {
        KitchenTicket existing = getById(id);
        existing.setStatus(status.toUpperCase());
        if ("READY".equalsIgnoreCase(status) || "COMPLETED".equalsIgnoreCase(status)) {
            existing.setCompletedAt(LocalDateTime.now());
        }
        return kitchenTicketRepository.save(existing);
    }

    @Transactional
    public KitchenTicket update(Long id, KitchenTicket updated) {
        KitchenTicket existing = getById(id);
        existing.setStatus(updated.getStatus());
        existing.setPriority(updated.getPriority());
        existing.setStaffName(updated.getStaffName());
        return kitchenTicketRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        KitchenTicket existing = getById(id);
        kitchenTicketRepository.delete(existing);
    }
}
