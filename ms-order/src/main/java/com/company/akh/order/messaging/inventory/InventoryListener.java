package com.company.akh.order.messaging.inventory;

import com.company.akh.order.config.kafka.properties.KafkaConstants;
import com.company.akh.order.messaging.event.InventoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@KafkaListener(
        id = KafkaConstants.CONTAINER_INVENTORY_RESPONSE_LISTENER,
        topics = KafkaConstants.TOPIC_INVENTORY_RESPONSE,
        groupId = KafkaConstants.GROUP,
        containerFactory = KafkaConstants.CONTAINER_INVENTORY_RESPONSE_LISTENER_FACTORY)
@RequiredArgsConstructor
public class InventoryListener {

    private final InventoryHandler inventoryHandler;

    @KafkaHandler
    public void onInventoryReceivedEvent(@Payload InventoryResponse event) {
        inventoryHandler.onInventoryReceivedEvent(event);
    }

}