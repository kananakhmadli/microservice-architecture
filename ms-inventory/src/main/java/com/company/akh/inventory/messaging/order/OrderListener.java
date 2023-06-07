package com.company.akh.inventory.messaging.order;

import com.company.akh.inventory.config.kafka.KafkaConstants;
import com.company.akh.inventory.messaging.event.OrderRequestEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@KafkaListener(
        id = KafkaConstants.CONTAINER_ORDER_LISTENER,
        topics = KafkaConstants.TOPIC_ORDER_REQUEST,
        groupId = KafkaConstants.GROUP,
        containerFactory = KafkaConstants.CONTAINER_ORDER_LISTENER_FACTORY)
@RequiredArgsConstructor
public class OrderListener {

    private final InventoryHandler inventoryHandler;

    @KafkaHandler
    public void onOrderReceivedEvent(@Payload OrderRequestEvent event) {
        inventoryHandler.onOrderReceivedEvent(event);
    }

}