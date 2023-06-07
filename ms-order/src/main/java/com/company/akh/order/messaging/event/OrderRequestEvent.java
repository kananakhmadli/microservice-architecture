package com.company.akh.order.messaging.event;

import lombok.Data;

@Data
public class OrderRequestEvent {

    private String uid;
    private Long userId;
    private Long productId;
    private Integer quantity;

}