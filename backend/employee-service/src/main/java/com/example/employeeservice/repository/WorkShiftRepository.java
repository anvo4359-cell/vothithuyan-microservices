package com.example.employeeservice.repository;

import com.example.employeeservice.entity.WorkShift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkShiftRepository extends JpaRepository<WorkShift, Long> {
    List<WorkShift> findByEmployeeId(Long employeeId);
    List<WorkShift> findByShiftDate(LocalDate shiftDate);
}
