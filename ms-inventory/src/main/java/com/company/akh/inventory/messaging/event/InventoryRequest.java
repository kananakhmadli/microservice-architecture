package com.company.akh.inventory.messaging.event;

import com.company.akh.inventory.dto.InventoryStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class InventoryRequest {

    private String uid;
    private InventoryStatus status;
    private BigDecimal amount;
    private Long userId;
    private Integer quantity;

}