package com.company.akh.payment.dto;

import lombok.Data;

@Data
public class OrderResponse {

    private String uid;
    private OrderStatus status;
    private PaymentStatus paymentStatus;
    private InventoryStatus inventoryStatus;

}