package com.company.akh.inventory.messaging.event;

import lombok.Data;

@Data
public class OrderRequestEvent {

    private String uid;
    private Long productId;
    private Integer quantity;
    private Long userId;

}