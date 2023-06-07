package com.company.akh.inventory.config.kafka;

public final class KafkaConstants {

    public static final String GROUP = "group1";

    // topic
    public static final String TOPIC_INVENTORY_RESPONSE = "inventory_response";
    public static final String TOPIC_INVENTORY_REQUEST = "inventory_request";
    public static final String TOPIC_PAYMENT_RESPONSE = "payment_response";
    public static final String TOPIC_ORDER_REQUEST = "order_request";

    // inventoryResponse
    public static final String CONTAINER_INVENTORY_RESPONSE_PRODUCER_FACTORY =
            "inventoryResponseProducerFactory";
    public static final String INVENTORY_RESPONSE_KAFKA_TEMPLATE = "inventoryResponseKafkaTemplate";

    // inventoryRequest
    public static final String CONTAINER_INVENTORY_REQUEST_PRODUCER_FACTORY =
            "inventoryRequestProducerFactory";
    public static final String INVENTORY_REQUEST_KAFKA_TEMPLATE = "inventoryRequestKafkaTemplate";

    // payment
    public static final String CONTAINER_PAYMENT_LISTENER = "payment";
    public static final String CONTAINER_PAYMENT_LISTENER_FACTORY = "paymentListenerFactory";
    public static final String CONTAINER_PAYMENT_CONSUMER_FACTORY = "paymentConsumerFactory";
    public static final String CONTAINER_PAYMENT_PRODUCER_FACTORY = "paymentProducerFactory";
    public static final String PAYMENT_KAFKA_TEMPLATE = "paymentKafkaTemplate";

    // order
    public static final String CONTAINER_ORDER_LISTENER = "order";
    public static final String CONTAINER_ORDER_LISTENER_FACTORY = "orderListenerFactory";
    public static final String CONTAINER_ORDER_CONSUMER_FACTORY = "orderConsumerFactory";

    private KafkaConstants() {
    }

}