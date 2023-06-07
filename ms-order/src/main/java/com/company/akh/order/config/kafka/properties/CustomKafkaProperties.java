package com.company.akh.order.config.kafka.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.List;
import java.util.Map;

@Data
@RefreshScope
@ConfigurationProperties(prefix = "kafka")
public class CustomKafkaProperties {

    private Map<String, TopicConfig> topics;
    private List<String> typeMappings;

    @Data
    public static class TopicConfig {
        private int partitions;
        private int replicas;
    }

}