package com.company.akh.order.dto;

import lombok.Data;

@Data
public class OrderDetail {

    private Long id;
    private String uid;
    private Long productId;
    private Integer quantity;
    private Long userId;

}