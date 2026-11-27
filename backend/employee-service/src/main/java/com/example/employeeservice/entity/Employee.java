package com.example.employeeservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_code", unique = true, length = 30)
    private String employeeCode;

    @NotBlank(message = "Họ tên nhân viên không được để trống")
    @Size(max = 100, message = "Họ tên tối đa 100 ký tự")
    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Email(message = "Email không hợp lệ")
    @Column(length = 100)
    private String email;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Size(max = 20, message = "Số điện thoại tối đa 20 ký tự")
    @Column(nullable = false, length = 20)
    private String phone;

    @Column(length = 50)
    private String role = "BARISTA"; // MANAGER, CASHIER, BARISTA, SHIPPER

    @NotNull(message = "Chi nhánh làm việc không được để trống")
    @Column(name = "branch_id", nullable = false)
    private Long branchId;

    @Column(name = "employment_type", length = 30)
    private String employmentType = "FULL_TIME"; // FULL_TIME, PART_TIME

    @Column(nullable = false)
    private Boolean active = true;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<WorkShift> shifts = new ArrayList<>();

    public Employee() {
    }

    public Employee(Long id, String employeeCode, String fullName, String email, String phone, String role, Long branchId, String employmentType, Boolean active) {
        this.id = id;
        this.employeeCode = employeeCode;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.role = role != null ? role : "BARISTA";
        this.branchId = branchId;
        this.employmentType = employmentType != null ? employmentType : "FULL_TIME";
        this.active = active != null ? active : true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<WorkShift> getShifts() {
        return shifts;
    }

    public void setShifts(List<WorkShift> shifts) {
        this.shifts = shifts;
    }
}
