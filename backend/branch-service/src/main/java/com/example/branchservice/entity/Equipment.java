package com.example.branchservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "equipments")
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "equipment_code", unique = true, length = 50)
    private String equipmentCode;

    @NotBlank(message = "Tên thiết bị không được để trống")
    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 50)
    private String type; // SEALING_MACHINE (Máy dập nắp), TEA_BREWER (Máy ủ trà), ICE_MACHINE, BLENDER

    @Column(nullable = false, length = 30)
    private String status = "OPERATIONAL"; // OPERATIONAL, UNDER_MAINTENANCE, BROKEN

    @Column(name = "last_maintenance")
    private LocalDate lastMaintenance;

    @Column(name = "next_maintenance")
    private LocalDate nextMaintenance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    public Equipment() {
    }

    public Equipment(Long id, String equipmentCode, String name, String type, String status, LocalDate lastMaintenance, LocalDate nextMaintenance, Branch branch) {
        this.id = id;
        this.equipmentCode = equipmentCode;
        this.name = name;
        this.type = type;
        this.status = status != null ? status : "OPERATIONAL";
        this.lastMaintenance = lastMaintenance;
        this.nextMaintenance = nextMaintenance;
        this.branch = branch;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEquipmentCode() {
        return equipmentCode;
    }

    public void setEquipmentCode(String equipmentCode) {
        this.equipmentCode = equipmentCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getLastMaintenance() {
        return lastMaintenance;
    }

    public void setLastMaintenance(LocalDate lastMaintenance) {
        this.lastMaintenance = lastMaintenance;
    }

    public LocalDate getNextMaintenance() {
        return nextMaintenance;
    }

    public void setNextMaintenance(LocalDate nextMaintenance) {
        this.nextMaintenance = nextMaintenance;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
}
