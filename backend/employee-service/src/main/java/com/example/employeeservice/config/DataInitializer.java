package com.example.employeeservice.config;

import com.example.employeeservice.entity.Employee;
import com.example.employeeservice.entity.WorkShift;
import com.example.employeeservice.repository.EmployeeRepository;
import com.example.employeeservice.repository.WorkShiftRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalTime;

@Configuration
public class DataInitializer implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;
    private final WorkShiftRepository workShiftRepository;

    public DataInitializer(EmployeeRepository employeeRepository, WorkShiftRepository workShiftRepository) {
        this.employeeRepository = employeeRepository;
        this.workShiftRepository = workShiftRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (employeeRepository.count() == 0) {
            Employee emp1 = employeeRepository.save(new Employee(
                    null,
                    "EMP-260101",
                    "Trần Minh Tuấn",
                    "tuan.tm@milktea.vn",
                    "0912345678",
                    "BARISTA",
                    1L,
                    "FULL_TIME",
                    true
            ));

            Employee emp2 = employeeRepository.save(new Employee(
                    null,
                    "EMP-260102",
                    "Lê Thu Hà",
                    "ha.lt@milktea.vn",
                    "0987654321",
                    "CASHIER",
                    1L,
                    "PART_TIME",
                    true
            ));

            workShiftRepository.save(new WorkShift(
                    null,
                    LocalDate.now(),
                    "MORNING",
                    LocalTime.of(6, 0),
                    LocalTime.of(14, 0),
                    "PRESENT",
                    "Ca sáng chuẩn giờ",
                    emp1
            ));

            workShiftRepository.save(new WorkShift(
                    null,
                    LocalDate.now(),
                    "AFTERNOON",
                    null,
                    null,
                    "SCHEDULED",
                    "Ca chiều quầy thu ngân",
                    emp2
            ));
        }
    }
}
