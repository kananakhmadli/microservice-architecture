package com.company.akh.inventory.messaging.payment;

import com.company.akh.inventory.config.kafka.KafkaConstants;
import com.company.akh.inventory.messaging.event.PaymentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@KafkaListener(
        id = KafkaConstants.CONTAINER_PAYMENT_LISTENER,
        topics = KafkaConstants.TOPIC_PAYMENT_RESPONSE,
        groupId = KafkaConstants.GROUP,
        containerFactory = KafkaConstants.CONTAINER_PAYMENT_LISTENER_FACTORY)
@RequiredArgsConstructor
public class PaymentListener {

    private final PaymentHandler paymentHandler;

    @KafkaHandler
    public void onPaymentReceivedEvent(@Payload PaymentResponse event) {
        paymentHandler.onPaymentReceivedEvent(event);
    }

}