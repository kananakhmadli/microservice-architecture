package com.company.akh.inventory.messaging.order;

import com.company.akh.inventory.config.kafka.KafkaConstants;
import com.company.akh.inventory.dto.InventoryStatus;
import com.company.akh.inventory.entity.Product;
import com.company.akh.inventory.messaging.event.InventoryRequest;
import com.company.akh.inventory.messaging.event.InventoryResponse;
import com.company.akh.inventory.messaging.event.OrderRequestEvent;
import com.company.akh.inventory.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class InventoryHandler {

    private final InventoryRepository inventoryRepository;
    private final KafkaTemplate<String, Object> inventoryResponseKafkaTemplate;
    private final KafkaTemplate<String, Object> inventoryRequestKafkaTemplate;

    public InventoryHandler(InventoryRepository inventoryRepository,
                            KafkaTemplate<String, Object> inventoryResponseKafkaTemplate,
                            KafkaTemplate<String, Object> inventoryRequestKafkaTemplate) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryResponseKafkaTemplate = inventoryResponseKafkaTemplate;
        this.inventoryRequestKafkaTemplate = inventoryRequestKafkaTemplate;
    }

    @Transactional
    public void onOrderReceivedEvent(OrderRequestEvent event) {
        log.info("onOrderReceivedEvent: {}", event.getUid());
        inventoryRepository.findById(event.getProductId())
                .ifPresentOrElse(product -> {
                    if (hasEnoughProductCount(event, product)) {
                        processSuccessfulOrder(event, product);
                    } else {
                        handleInsufficientProduct(event);
                    }
                }, () -> handleProductNotFound(event));

        log.info("onOrderReceivedEvent processed successfully");
    }

    private void handleProductNotFound(OrderRequestEvent event) {
        InventoryResponse inventoryResponse = createInventoryResponse(
                event, InventoryStatus.REJECTED,
                "Product not found: id " + event.getProductId());
        inventoryResponseKafkaTemplate.send(
                KafkaConstants.TOPIC_INVENTORY_RESPONSE, inventoryResponse);
    }

    private void processSuccessfulOrder(OrderRequestEvent event, Product product) {
        // decreasing product count
        int count = product.getQuantity() - event.getQuantity();
        product.setQuantity(count);
        // saving in database
        inventoryRepository.save(product);

        var inventoryRequest = new InventoryRequest();
        inventoryRequest.setUid(event.getUid());
        inventoryRequest.setStatus(InventoryStatus.SUCCESS);
        inventoryRequest.setUserId(event.getUserId());
        inventoryRequest.setAmount(product.getPrice());
        inventoryRequest.setQuantity(event.getQuantity());
        // creating new event for checking user balance
        inventoryRequestKafkaTemplate.send(
                KafkaConstants.TOPIC_INVENTORY_REQUEST, inventoryRequest);
    }

    private void handleInsufficientProduct(OrderRequestEvent event) {
        InventoryResponse inventoryResponse = createInventoryResponse(
                event, InventoryStatus.REJECTED, "Not enough products in inventory");
        inventoryResponseKafkaTemplate.send(
                KafkaConstants.TOPIC_INVENTORY_RESPONSE, inventoryResponse);
    }

    private InventoryResponse createInventoryResponse(
            OrderRequestEvent event, InventoryStatus status, String description) {
        var inventoryResponse = new InventoryResponse();
        inventoryResponse.setUid(event.getUid());
        inventoryResponse.setStatus(status);
        inventoryResponse.setDescription(description);
        return inventoryResponse;
    }

    private boolean hasEnoughProductCount(OrderRequestEvent event, Product product) {
        return product.getQuantity() >= event.getQuantity();
    }

}