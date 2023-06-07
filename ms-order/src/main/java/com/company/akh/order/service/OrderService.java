package com.company.akh.order.service;

import az.kapitalbank.atlas.lib.common.error.ServiceException;
import com.company.akh.order.config.kafka.properties.KafkaConstants;
import com.company.akh.order.dto.InventoryStatus;
import com.company.akh.order.dto.OrderDetail;
import com.company.akh.order.dto.OrderResponse;
import com.company.akh.order.dto.OrderStatus;
import com.company.akh.order.dto.request.OrderRequest;
import com.company.akh.order.error.ErrorCodes;
import com.company.akh.order.mapper.OrderMapper;
import com.company.akh.order.repository.OrderRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, Object> orderRequestKafkaTemplate;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository,
                        KafkaTemplate<String, Object> orderRequestKafkaTemplate,
                        OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderRequestKafkaTemplate = orderRequestKafkaTemplate;
        this.orderMapper = orderMapper;
    }

    @Transactional
    public OrderResponse create(OrderRequest request) {
        var order = orderMapper.toOrder(request);
        // updating in database
        order.setStatus(OrderStatus.PENDING);
        order.setInventoryStatus(InventoryStatus.PENDING);
        orderRepository.save(order);

        // sending to kafka topic
        var orderRequestEvent = orderMapper.toOrderRequestEvent(request);
        orderRequestKafkaTemplate.send(KafkaConstants.TOPIC_ORDER_REQUEST, orderRequestEvent);

        return orderMapper.toOrderResponse(order);
    }

    public OrderResponse getStatusByUid(String uid) {
        return orderRepository.findOptionalOrderByUid(uid)
                .map(orderMapper::toOrderResponse)
                .orElseThrow(() -> ServiceException.of(
                        ErrorCodes.ORDER_NOT_FOUND, new Object[]{uid}));
    }

    public OrderDetail getDetailByUid(String uid) {
        return orderRepository.findOptionalOrderByUid(uid)
                .map(orderMapper::toOrderDetail)
                .orElseThrow(() -> ServiceException.of(
                        ErrorCodes.ORDER_NOT_FOUND, new Object[]{uid}));
    }

}