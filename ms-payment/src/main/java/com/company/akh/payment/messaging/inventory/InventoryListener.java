package com.company.akh.payment.messaging.inventory;

import com.company.akh.payment.config.kafka.KafkaConstants;
import com.company.akh.payment.messaging.event.InventoryRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@KafkaListener(
        id = KafkaConstants.CONTAINER_INVENTORY_LISTENER,
        topics = KafkaConstants.TOPIC_INVENTORY_REQUEST,
        groupId = KafkaConstants.GROUP,
        containerFactory = KafkaConstants.CONTAINER_INVENTORY_REQUEST_LISTENER_FACTORY)
@RequiredArgsConstructor
public class InventoryListener {

    private final InventoryHandler inventoryHandler;

    @KafkaHandler
    public void onInventoryReceivedEvent(@Payload InventoryRequest event) {
        inventoryHandler.onInventoryReceivedEvent(event);
    }

}