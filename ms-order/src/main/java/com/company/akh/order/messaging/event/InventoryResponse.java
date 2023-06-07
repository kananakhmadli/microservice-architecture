package com.company.akh.order.messaging.event;

import com.company.akh.order.dto.InventoryStatus;
import lombok.Data;

@Data
public class InventoryResponse {

    private String uid;
    private InventoryStatus status;
    private String description;

}