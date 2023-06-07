package com.company.akh.inventory.messaging.config;

import com.company.akh.inventory.config.kafka.CustomKafkaProperties;
import com.company.akh.inventory.config.kafka.KafkaConstants;
import com.company.akh.inventory.messaging.event.OrderRequestEvent;
import com.company.akh.inventory.messaging.event.PaymentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfiguration {

    private final KafkaProperties kafkaProperties;
    private final CustomKafkaProperties customKafkaProperties;

    @Bean(KafkaConstants.CONTAINER_ORDER_CONSUMER_FACTORY)
    public ConsumerFactory<String, Object> orderConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                kafkaProperties.getBootstrapServers());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
                kafkaProperties.getConsumer().getAutoOffsetReset());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 300000);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 10000);
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 3000);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 100);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(JsonDeserializer.TYPE_MAPPINGS,
                String.join(",", customKafkaProperties.getTypeMappings()));
        props.put(JsonDeserializer.REMOVE_TYPE_INFO_HEADERS, false);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
//        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, OrderRequestEvent.class);

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean(KafkaConstants.CONTAINER_ORDER_LISTENER_FACTORY)
    public ConcurrentKafkaListenerContainerFactory<String, Object> orderListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> containerFactory =
                new ConcurrentKafkaListenerContainerFactory<>();
        containerFactory.setConsumerFactory(orderConsumerFactory());
        containerFactory.setConcurrency(customKafkaProperties.getTopics()
                .get(KafkaConstants.TOPIC_ORDER_REQUEST)
                .getPartitions());
        containerFactory.getContainerProperties().setIdleEventInterval(60000L);
        containerFactory.getContainerProperties().setAckMode(ContainerProperties.AckMode.BATCH);

        return containerFactory;
    }


    @Bean(KafkaConstants.CONTAINER_PAYMENT_CONSUMER_FACTORY)
    public ConsumerFactory<String, Object> paymentConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                kafkaProperties.getBootstrapServers());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
                kafkaProperties.getConsumer().getAutoOffsetReset());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 300000);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 10000);
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 3000);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 100);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(JsonDeserializer.TYPE_MAPPINGS,
                String.join(",", customKafkaProperties.getTypeMappings()));
        props.put(JsonDeserializer.REMOVE_TYPE_INFO_HEADERS, false);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
//        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, PaymentResponse.class);

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean(KafkaConstants.CONTAINER_PAYMENT_LISTENER_FACTORY)
    public ConcurrentKafkaListenerContainerFactory<String, Object> paymentListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> containerFactory =
                new ConcurrentKafkaListenerContainerFactory<>();
        containerFactory.setConsumerFactory(paymentConsumerFactory());
        containerFactory.setConcurrency(customKafkaProperties.getTopics()
                .get(KafkaConstants.TOPIC_PAYMENT_RESPONSE)
                .getPartitions());
        containerFactory.getContainerProperties().setIdleEventInterval(60000L);
        containerFactory.getContainerProperties().setAckMode(ContainerProperties.AckMode.BATCH);

        return containerFactory;
    }

}