package com.company.akh.inventory.messaging.event;

import com.company.akh.inventory.dto.InventoryStatus;
import lombok.Data;

@Data
public class InventoryResponse {

    private String uid;
    private InventoryStatus status;
    private String description;

}