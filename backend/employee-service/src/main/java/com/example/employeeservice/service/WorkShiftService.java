package com.example.employeeservice.service;

import com.example.employeeservice.entity.Employee;
import com.example.employeeservice.entity.WorkShift;
import com.example.employeeservice.exception.ResourceNotFoundException;
import com.example.employeeservice.repository.EmployeeRepository;
import com.example.employeeservice.repository.WorkShiftRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class WorkShiftService {

    private final WorkShiftRepository workShiftRepository;
    private final EmployeeRepository employeeRepository;

    public WorkShiftService(WorkShiftRepository workShiftRepository, EmployeeRepository employeeRepository) {
        this.workShiftRepository = workShiftRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<WorkShift> getAll() {
        return workShiftRepository.findAll();
    }

    public List<WorkShift> getByEmployeeId(Long employeeId) {
        return workShiftRepository.findByEmployeeId(employeeId);
    }

    public List<WorkShift> getByDate(LocalDate date) {
        return workShiftRepository.findByShiftDate(date);
    }

    public WorkShift getById(Long id) {
        return workShiftRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy ca làm việc với ID: " + id));
    }

    @Transactional
    public WorkShift create(WorkShift shift) {
        if (shift.getEmployee() == null || shift.getEmployee().getId() == null) {
            throw new IllegalArgumentException("Ca làm việc bắt buộc phải gán nhân viên");
        }
        Employee employee = employeeRepository.findById(shift.getEmployee().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhân viên ID: " + shift.getEmployee().getId()));
        shift.setEmployee(employee);
        if (shift.getStatus() == null) {
            shift.setStatus("SCHEDULED");
        }
        return workShiftRepository.save(shift);
    }

    @Transactional
    public WorkShift checkIn(Long shiftId) {
        WorkShift shift = getById(shiftId);
        shift.setCheckInTime(LocalTime.now());
        shift.setStatus("PRESENT");
        return workShiftRepository.save(shift);
    }

    @Transactional
    public WorkShift checkOut(Long shiftId) {
        WorkShift shift = getById(shiftId);
        shift.setCheckOutTime(LocalTime.now());
        return workShiftRepository.save(shift);
    }

    @Transactional
    public void delete(Long id) {
        WorkShift existing = getById(id);
        workShiftRepository.delete(existing);
    }
}
