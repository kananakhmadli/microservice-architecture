package com.company.akh.payment;

import com.company.akh.payment.config.kafka.CustomKafkaProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
@EnableConfigurationProperties(CustomKafkaProperties.class)
public class MsPaymentApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MsPaymentApplication.class, args);
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