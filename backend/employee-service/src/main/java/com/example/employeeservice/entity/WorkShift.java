package com.example.employeeservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "work_shifts")
public class WorkShift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Ngày làm việc không được để trống")
    @Column(name = "shift_date", nullable = false)
    private LocalDate shiftDate;

    @NotBlank(message = "Loại ca không được để trống")
    @Column(name = "shift_type", nullable = false, length = 30)
    private String shiftType; // MORNING (6h-14h), AFTERNOON (14h-22h), NIGHT

    @Column(name = "check_in_time")
    private LocalTime checkInTime;

    @Column(name = "check_out_time")
    private LocalTime checkOutTime;

    @Column(length = 30)
    private String status = "SCHEDULED"; // SCHEDULED, PRESENT, ABSENT, LATE

    @Column(length = 255)
    private String note;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    public WorkShift() {
    }

    public WorkShift(Long id, LocalDate shiftDate, String shiftType, LocalTime checkInTime, LocalTime checkOutTime, String status, String note, Employee employee) {
        this.id = id;
        this.shiftDate = shiftDate;
        this.shiftType = shiftType;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.status = status != null ? status : "SCHEDULED";
        this.note = note;
        this.employee = employee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getShiftDate() {
        return shiftDate;
    }

    public void setShiftDate(LocalDate shiftDate) {
        this.shiftDate = shiftDate;
    }

    public String getShiftType() {
        return shiftType;
    }

    public void setShiftType(String shiftType) {
        this.shiftType = shiftType;
    }

    public LocalTime getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(LocalTime checkInTime) {
        this.checkInTime = checkInTime;
    }

    public LocalTime getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(LocalTime checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
