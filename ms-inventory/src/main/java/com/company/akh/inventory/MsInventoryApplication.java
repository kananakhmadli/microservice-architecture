package com.company.akh.inventory;

import com.company.akh.inventory.config.kafka.CustomKafkaProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
@EnableConfigurationProperties(CustomKafkaProperties.class)
public class MsInventoryApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MsInventoryApplication.class, args);
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