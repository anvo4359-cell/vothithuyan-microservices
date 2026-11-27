package com.example.kitchenservice.config;

import com.example.kitchenservice.entity.KitchenTicket;
import com.example.kitchenservice.entity.KitchenTicketItem;
import com.example.kitchenservice.repository.KitchenTicketRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class DataInitializer implements CommandLineRunner {

    private final KitchenTicketRepository kitchenTicketRepository;

    public DataInitializer(KitchenTicketRepository kitchenTicketRepository) {
        this.kitchenTicketRepository = kitchenTicketRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (kitchenTicketRepository.count() == 0) {
            KitchenTicket ticket = new KitchenTicket(
                    null,
                    "KDS-20261120-001",
                    "ORD-20261120-001",
                    1L,
                    "IN_PROGRESS",
                    "HIGH",
                    "Pha chế viên Tuấn"
            );
            ticket.setReceivedAt(LocalDateTime.now().minusMinutes(5));

            KitchenTicketItem item1 = new KitchenTicketItem(
                    null,
                    "Trà sữa Oolong Nướng",
                    "L",
                    "50%",
                    "70%",
                    "Trân châu hoàng kim",
                    "Cốt trà Oolong 150ml, Bột sữa non 35g, Nước đường 15ml, 1 muỗng trân châu",
                    1,
                    "IN_PROGRESS",
                    ticket
            );

            KitchenTicketItem item2 = new KitchenTicketItem(
                    null,
                    "Trà Đào Cam Sả",
                    "M",
                    "70%",
                    "100%",
                    "Thạch củ năng ngũ sắc",
                    "Cốt trà đen 120ml, 2 lát đào, Nước cốt cam 30ml, 1 muỗng thạch",
                    1,
                    "PENDING",
                    ticket
            );

            ticket.addItem(item1);
            ticket.addItem(item2);
            kitchenTicketRepository.save(ticket);
        }
    }
}
