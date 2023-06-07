package com.company.akh.inventory.messaging.event;

import com.company.akh.inventory.dto.PaymentStatus;
import lombok.Data;

@Data
public class PaymentResponse {

    private String uid;
    private PaymentStatus status;
    private String description;

}