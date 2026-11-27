package com.example.branchservice.config;

import com.example.branchservice.entity.Branch;
import com.example.branchservice.entity.Equipment;
import com.example.branchservice.repository.BranchRepository;
import com.example.branchservice.repository.EquipmentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class DataInitializer implements CommandLineRunner {

    private final BranchRepository branchRepository;
    private final EquipmentRepository equipmentRepository;

    public DataInitializer(BranchRepository branchRepository, EquipmentRepository equipmentRepository) {
        this.branchRepository = branchRepository;
        this.equipmentRepository = equipmentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (branchRepository.count() == 0) {
            Branch b1 = branchRepository.save(new Branch(
                    null,
                    "BR-Q5-01",
                    "Chi nhánh Quận 5 - Nguyễn Văn Cừ",
                    "123 Nguyễn Văn Cừ, Phường 4, Quận 5, TP.HCM",
                    "02838123456",
                    "Nguyễn Quản Lý",
                    "ACTIVE"
            ));

            Branch b2 = branchRepository.save(new Branch(
                    null,
                    "BR-Q1-02",
                    "Chi nhánh Quận 1 - Phố Đi Bộ",
                    "45 Ngô Đức Kế, Bến Nghé, Quận 1, TP.HCM",
                    "02838998877",
                    "Trần Giám Sát",
                    "ACTIVE"
            ));

            equipmentRepository.save(new Equipment(
                    null,
                    "EQ-SEAL-01",
                    "Máy dập nắp tự động FEST RC-95",
                    "SEALING_MACHINE",
                    "OPERATIONAL",
                    LocalDate.now().minusDays(15),
                    LocalDate.now().plusMonths(3),
                    b1
            ));

            equipmentRepository.save(new Equipment(
                    null,
                    "EQ-BREW-01",
                    "Máy ủ trà áp suất cao Bear 20L",
                    "TEA_BREWER",
                    "OPERATIONAL",
                    LocalDate.now().minusDays(20),
                    LocalDate.now().plusMonths(2),
                    b1
            ));
        }
    }
}
