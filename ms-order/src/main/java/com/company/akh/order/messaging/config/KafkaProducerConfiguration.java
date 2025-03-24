package com.company.akh.order.messaging.config;

import com.company.akh.order.config.kafka.properties.CustomKafkaProperties;
import com.company.akh.order.config.kafka.properties.KafkaConstants;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@AllArgsConstructor
public class KafkaProducerConfiguration {

    private final KafkaProperties kafkaProperties;
    private final CustomKafkaProperties customKafkaProperties;

    @Bean(KafkaConstants.ORDER_REQUEST_KAFKA_TEMPLATE)
    public KafkaTemplate<String, Object> orderRequestKafkaTemplate() {
        return new KafkaTemplate<>(orderRequestProducerFactory());
    }

    @Bean(KafkaConstants.ORDER_REQUEST_PRODUCER_FACTORY)
    public ProducerFactory<String, Object> orderRequestProducerFactory() {
        Map<String, Object> props = new HashMap<>();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                kafkaProperties.getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.MIN, "all");
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 500);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(JsonSerializer.TYPE_MAPPINGS,
                String.join(",", customKafkaProperties.getTypeMappings()));

        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean(KafkaConstants.PREPARE_ORDER_REQUEST_KAFKA_TEMPLATE)
    public KafkaTemplate<String, Object> prepareOrderRequestKafkaTemplate() {
        return new KafkaTemplate<>(prepareOrderRequestProducerFactory());
    }

    @Bean(KafkaConstants.PREPARE_REQUEST_PRODUCER_FACTORY)
    public ProducerFactory<String, Object> prepareOrderRequestProducerFactory() {
        Map<String, Object> props = new HashMap<>();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                kafkaProperties.getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 500);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(JsonSerializer.TYPE_MAPPINGS,
                String.join(",", customKafkaProperties.getTypeMappings()));

        return new DefaultKafkaProducerFactory<>(props);
    }

}