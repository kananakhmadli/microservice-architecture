package com.company.akh.inventory.messaging.payment;

import com.company.akh.inventory.client.OrderClient;
import com.company.akh.inventory.client.model.OrderDetail;
import com.company.akh.inventory.config.kafka.KafkaConstants;
import com.company.akh.inventory.dto.InventoryStatus;
import com.company.akh.inventory.dto.PaymentStatus;
import com.company.akh.inventory.messaging.event.InventoryResponse;
import com.company.akh.inventory.messaging.event.PaymentResponse;
import com.company.akh.inventory.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class PaymentHandler {

    private final KafkaTemplate<String, Object> inventoryResponseKafkaTemplate;
    private final OrderClient orderClient;
    private final InventoryRepository inventoryRepository;

    public PaymentHandler(KafkaTemplate<String, Object> inventoryResponseKafkaTemplate,
                          OrderClient orderClient,
                          InventoryRepository inventoryRepository) {
        this.inventoryResponseKafkaTemplate = inventoryResponseKafkaTemplate;
        this.orderClient = orderClient;
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional
    public void onPaymentReceivedEvent(PaymentResponse event) {
        log.info("onPaymentReceivedEvent: {}", event.getUid());
        var inventoryResponse = new InventoryResponse();
        inventoryResponse.setUid(event.getUid());
        inventoryResponse.setDescription(event.getDescription());

        if (PaymentStatus.PAYMENT_COMPLETED.equals(event.getStatus())) {
            inventoryResponse.setStatus(InventoryStatus.SUCCESS);
        } else {
            // updating status
            inventoryResponse.setStatus(InventoryStatus.REJECTED);
            // getting order detail
            OrderDetail detail = orderClient.getDetailByUid(event.getUid());

            // saving changes in database
            inventoryRepository.findById(detail.getProductId())
                    .map(p -> p.updateQuantity(detail.getQuantity()))
                    .map(inventoryRepository::save);
        }

        inventoryResponseKafkaTemplate.send(
                KafkaConstants.TOPIC_INVENTORY_RESPONSE, inventoryResponse);

        log.info("onPaymentReceivedEvent processed successfully");
    }

}