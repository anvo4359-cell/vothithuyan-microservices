package com.example.employeeservice.service;

import com.example.employeeservice.entity.Employee;
import com.example.employeeservice.exception.ResourceNotFoundException;
import com.example.employeeservice.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public Employee getById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhân viên với ID: " + id));
    }

    public List<Employee> getByBranch(Long branchId) {
        return employeeRepository.findByBranchId(branchId);
    }

    public List<Employee> getActiveEmployees() {
        return employeeRepository.findByActiveTrue();
    }

    @Transactional
    public Employee create(Employee employee) {
        if (employee.getEmployeeCode() == null || employee.getEmployeeCode().trim().isEmpty()) {
            String timePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
            int randomPart = ThreadLocalRandom.current().nextInt(100, 999);
            employee.setEmployeeCode("EMP-" + timePart + "-" + randomPart);
        }
        if (employee.getActive() == null) {
            employee.setActive(true);
        }
        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee update(Long id, Employee updated) {
        Employee existing = getById(id);
        existing.setFullName(updated.getFullName());
        existing.setEmail(updated.getEmail());
        existing.setPhone(updated.getPhone());
        existing.setRole(updated.getRole());
        existing.setBranchId(updated.getBranchId());
        existing.setEmploymentType(updated.getEmploymentType());
        if (updated.getActive() != null) {
            existing.setActive(updated.getActive());
        }
        return employeeRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        Employee existing = getById(id);
        employeeRepository.delete(existing);
    }
}
