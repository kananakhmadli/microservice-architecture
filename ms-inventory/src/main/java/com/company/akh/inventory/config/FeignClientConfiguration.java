package com.company.akh.inventory.config;

import com.company.akh.inventory.client.OrderClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = OrderClient.class)
public class FeignClientConfiguration {
}