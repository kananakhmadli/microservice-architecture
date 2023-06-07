package com.company.akh.inventory.init;

import com.company.akh.inventory.entity.Product;
import com.company.akh.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class InitializeDemoObject {

    private final InventoryRepository inventoryRepository;

    @PostConstruct
    public void init() {
        var product1 = new Product();
        product1.setName("Tv");
        product1.setAmount(BigDecimal.valueOf(500));
        product1.setQuantity(10);

        var product2 = new Product();
        product2.setName("Phone");
        product2.setAmount(BigDecimal.valueOf(300));
        product2.setQuantity(2);

        var product3 = new Product();
        product3.setName("Computer");
        product3.setAmount(BigDecimal.valueOf(2000));
        product3.setQuantity(1);

        inventoryRepository.save(product1);
        inventoryRepository.save(product2);
        inventoryRepository.save(product3);
    }

}