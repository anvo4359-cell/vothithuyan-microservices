package com.example.branchservice.service;

import com.example.branchservice.entity.Branch;
import com.example.branchservice.exception.ResourceNotFoundException;
import com.example.branchservice.repository.BranchRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BranchService {

    private final BranchRepository branchRepository;

    public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    public List<Branch> getAll() {
        return branchRepository.findAll();
    }

    public Branch getById(Long id) {
        return branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy chi nhánh với ID: " + id));
    }

    public List<Branch> getActiveBranches() {
        return branchRepository.findByStatus("ACTIVE");
    }

    @Transactional
    public Branch create(Branch branch) {
        if (branch.getStatus() == null) {
            branch.setStatus("ACTIVE");
        }
        return branchRepository.save(branch);
    }

    @Transactional
    public Branch update(Long id, Branch updated) {
        Branch existing = getById(id);
        existing.setName(updated.getName());
        existing.setAddress(updated.getAddress());
        existing.setPhone(updated.getPhone());
        existing.setManagerName(updated.getManagerName());
        if (updated.getStatus() != null) {
            existing.setStatus(updated.getStatus());
        }
        return branchRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        Branch existing = getById(id);
        branchRepository.delete(existing);
    }
}
