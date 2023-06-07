package com.company.akh.inventory.dto;

import lombok.Data;

@Data
public class OrderResponse {

    private String uid;
    private OrderStatus status;
    private PaymentStatus paymentStatus;
    private InventoryStatus inventoryStatus;

}