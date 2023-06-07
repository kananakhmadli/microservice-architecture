package com.company.akh.order.config.kafka.properties;

public final class KafkaConstants {

    public static final String GROUP = "group";

    // topic
    public static final String TOPIC_ORDER_REQUEST = "order_request";
    public static final String TOPIC_INVENTORY_REQUEST = "inventory_request";
    public static final String TOPIC_PAYMENT_RESPONSE = "payment_response";
    public static final String TOPIC_INVENTORY_RESPONSE = "inventory_response";
    public static final String TOPIC_PREPARE_ORDER_REQUEST = "prepare_order_request";

    // inventory-response
    public static final String CONTAINER_INVENTORY_RESPONSE_LISTENER =
            "containerInventoryResponseListener";
    public static final String CONTAINER_INVENTORY_RESPONSE_LISTENER_FACTORY =
            "inventoryListenerListenerContainerFactory";
    public static final String CONTAINER_INVENTORY_RESPONSE_CONSUMER_FACTORY =
            "inventoryResponseConsumerFactory";

    // order-request
    public static final String ORDER_REQUEST_KAFKA_TEMPLATE = "orderRequestKafkaTemplate";
    public static final String ORDER_REQUEST_PRODUCER_FACTORY = "orderRequestProducerFactory";

    // prepare-order-request
    public static final String PREPARE_ORDER_REQUEST_KAFKA_TEMPLATE =
            "prepareOrderRequestKafkaTemplate";
    public static final String PREPARE_REQUEST_PRODUCER_FACTORY =
            "prepareOrderRequestProducerFactory";

    private KafkaConstants() {
    }

}