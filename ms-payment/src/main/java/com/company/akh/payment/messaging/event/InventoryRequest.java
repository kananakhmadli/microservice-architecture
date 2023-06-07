package com.company.akh.payment.messaging.event;

import com.company.akh.payment.dto.InventoryStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class InventoryRequest {

    private String uid;
    private InventoryStatus status;
    private BigDecimal amount;
    private Integer quantity;
    private Long userId;

}