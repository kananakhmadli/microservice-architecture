package com.company.akh.inventory.client;

import az.kapitalbank.atlas.lib.common.error.CommonFeignErrorHandling;
import com.company.akh.inventory.client.model.OrderDetail;
import com.company.akh.inventory.config.CustomHeaderInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import feign.error.AnnotationErrorDecoder;
import feign.jackson.JacksonDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "order",
        url = "${service.order.url}",
        configuration = OrderClient.FeignConfiguration.class)
@CommonFeignErrorHandling
public interface OrderClient {

    @GetMapping("/{uid}/detail")
    OrderDetail getDetailByUid(@PathVariable String uid);

    class FeignConfiguration {

        @Bean
        public Logger.Level feignLoggerLevel() {
            return Logger.Level.FULL;
        }

        @Bean
        public ErrorDecoder feignErrorDecoder(ObjectMapper objectMapper) {
            return AnnotationErrorDecoder.builderFor(OrderClient.class)
                    .withResponseBodyDecoder(new JacksonDecoder(objectMapper)).build();
        }

        @Bean
        public RequestInterceptor requestInterceptor() {
            return new CustomHeaderInterceptor();
        }
    }

}