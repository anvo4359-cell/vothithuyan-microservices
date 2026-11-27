package com.example.branchservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "branches")
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "branch_code", unique = true, length = 30)
    private String branchCode;

    @NotBlank(message = "Tên chi nhánh không được để trống")
    @Size(max = 150, message = "Tên chi nhánh tối đa 150 ký tự")
    @Column(nullable = false, length = 150)
    private String name;

    @NotBlank(message = "Địa chỉ chi nhánh không được để trống")
    @Column(nullable = false, length = 255)
    private String address;

    @Column(length = 20)
    private String phone;

    @Column(name = "manager_name", length = 100)
    private String managerName;

    @Column(nullable = false, length = 30)
    private String status = "ACTIVE"; // ACTIVE, TEMPORARILY_CLOSED, INACTIVE

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Equipment> equipments = new ArrayList<>();

    public Branch() {
    }

    public Branch(Long id, String branchCode, String name, String address, String phone, String managerName, String status) {
        this.id = id;
        this.branchCode = branchCode;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.managerName = managerName;
        this.status = status != null ? status : "ACTIVE";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }
}
