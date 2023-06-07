package com.company.akh.payment.messaging.event;

import com.company.akh.payment.dto.PaymentStatus;
import lombok.Data;

@Data
public class PaymentResponse {

    private String uid;
    private PaymentStatus status;
    private String description;

}