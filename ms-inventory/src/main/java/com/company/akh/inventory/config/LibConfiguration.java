package com.company.akh.inventory.config;

import az.kapitalbank.atlas.lib.common.config.annotation.EnableContextFilter;
import az.kapitalbank.atlas.lib.common.config.annotation.EnableErrorHandler;
import az.kapitalbank.atlas.lib.common.config.annotation.EnableLogInterceptor;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableErrorHandler
@EnableContextFilter
@EnableLogInterceptor
public class LibConfiguration {
}