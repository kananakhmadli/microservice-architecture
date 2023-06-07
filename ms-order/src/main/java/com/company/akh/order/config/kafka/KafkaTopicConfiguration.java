package com.company.akh.order.config.kafka;

import com.company.akh.order.config.kafka.properties.CustomKafkaProperties;
import com.company.akh.order.config.kafka.properties.KafkaConstants;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@RequiredArgsConstructor
public class KafkaTopicConfiguration {

    private final CustomKafkaProperties kafkaProperties;

    @Bean
    public NewTopic orderRequest() {
        CustomKafkaProperties.TopicConfig config = kafkaProperties.getTopics()
                .get(KafkaConstants.TOPIC_ORDER_REQUEST);

        return TopicBuilder.name(KafkaConstants.TOPIC_ORDER_REQUEST)
                .partitions(config.getPartitions())
                .replicas(config.getReplicas())
                .build();
    }

    @Bean
    public NewTopic inventoryRequest() {
        CustomKafkaProperties.TopicConfig config = kafkaProperties.getTopics()
                .get(KafkaConstants.TOPIC_INVENTORY_REQUEST);

        return TopicBuilder.name(KafkaConstants.TOPIC_INVENTORY_REQUEST)
                .partitions(config.getPartitions())
                .replicas(config.getReplicas())
                .build();
    }

    @Bean
    public NewTopic paymentResponse() {
        CustomKafkaProperties.TopicConfig config = kafkaProperties.getTopics()
                .get(KafkaConstants.TOPIC_PAYMENT_RESPONSE);

        return TopicBuilder.name(KafkaConstants.TOPIC_PAYMENT_RESPONSE)
                .partitions(config.getPartitions())
                .replicas(config.getReplicas())
                .build();
    }

    @Bean
    public NewTopic inventoryResponse() {
        CustomKafkaProperties.TopicConfig config = kafkaProperties.getTopics()
                .get(KafkaConstants.TOPIC_INVENTORY_RESPONSE);

        return TopicBuilder.name(KafkaConstants.TOPIC_INVENTORY_RESPONSE)
                .partitions(config.getPartitions())
                .replicas(config.getReplicas())
                .build();
    }

    @Bean
    public NewTopic prepareOrderRequest() {
        CustomKafkaProperties.TopicConfig config = kafkaProperties.getTopics()
                .get(KafkaConstants.TOPIC_PREPARE_ORDER_REQUEST);

        return TopicBuilder.name(KafkaConstants.TOPIC_PREPARE_ORDER_REQUEST)
                .partitions(config.getPartitions())
                .replicas(config.getReplicas())
                .build();
    }

}