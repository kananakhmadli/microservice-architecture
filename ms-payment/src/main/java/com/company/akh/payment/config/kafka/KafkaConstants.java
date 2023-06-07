package com.company.akh.payment.config.kafka;

public final class KafkaConstants {

    public static final String GROUP = "group";

    // payment
    public static final String TOPIC_PAYMENT_RESPONSE = "payment_response";

    // inventoryRequest
    public static final String TOPIC_INVENTORY_REQUEST = "inventory_request";
    public static final String CONTAINER_INVENTORY_LISTENER = "containerInventoryListener";
    public static final String CONTAINER_INVENTORY_REQUEST_LISTENER_FACTORY =
            "orderListenerFactory";
    public static final String CONTAINER_INVENTORY_REQUEST_CONSUMER_FACTORY =
            "orderConsumerFactory";

    private KafkaConstants() {
    }

}