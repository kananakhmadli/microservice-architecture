package com.company.akh.order.dto;

import lombok.Data;

@Data
public class OrderResponse {

    private String uid;
    private OrderStatus status;
    private InventoryStatus inventoryStatus;
    private String declinedReason;

}