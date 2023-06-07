package com.company.akh.order;

import com.company.akh.order.config.kafka.properties.CustomKafkaProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
@EnableConfigurationProperties(CustomKafkaProperties.class)
public class MsOrderApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MsOrderApplication.class, args);
    }

    @Bean
    public CommandLineRunner run() {
        return args -> {
        };
    }

    @Override
    public void run(String... args) {
    }

}