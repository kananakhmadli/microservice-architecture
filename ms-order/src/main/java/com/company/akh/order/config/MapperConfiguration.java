package com.company.akh.order.config;

import com.company.akh.order.mapper.OrderMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {

    @Bean
    public OrderMapper orderMapper() {
        return OrderMapper.INSTANCE;
    }

}