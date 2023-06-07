package com.company.akh.order.messaging.inventory;

import com.company.akh.order.config.kafka.properties.KafkaConstants;
import com.company.akh.order.dto.InventoryStatus;
import com.company.akh.order.dto.OrderStatus;
import com.company.akh.order.messaging.event.InventoryResponse;
import com.company.akh.order.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class InventoryHandler {

    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, Object> prepareOrderRequestKafkaTemplate;

    public InventoryHandler(OrderRepository orderRepository,
                            KafkaTemplate<String, Object> prepareOrderRequestKafkaTemplate) {
        this.orderRepository = orderRepository;
        this.prepareOrderRequestKafkaTemplate = prepareOrderRequestKafkaTemplate;
    }

    @Transactional
    public void onInventoryReceivedEvent(InventoryResponse event) {
        log.info("onInventoryReceivedEvent: {}", event.getUid());
        var order = orderRepository.findByUid(event.getUid());

        if (InventoryStatus.SUCCESS.equals(event.getStatus())) {
            order.setStatus(OrderStatus.ORDER_COMPLETED);
            order.setInventoryStatus(InventoryStatus.SUCCESS);
            prepareOrderRequestKafkaTemplate.send(
                    KafkaConstants.TOPIC_PREPARE_ORDER_REQUEST, order);
        } else {
            order.setStatus(OrderStatus.ORDER_CANCELLED);
            order.setInventoryStatus(InventoryStatus.REJECTED);
            order.setDeclinedReason(event.getDescription());
        }

        orderRepository.save(order);
        log.info("onInventoryReceivedEvent processed successfully");
    }

}