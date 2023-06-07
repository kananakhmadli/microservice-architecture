package com.company.akh.order.mapper;

import com.company.akh.order.dto.OrderDetail;
import com.company.akh.order.dto.OrderResponse;
import com.company.akh.order.dto.request.OrderRequest;
import com.company.akh.order.entity.Order;
import com.company.akh.order.messaging.event.OrderRequestEvent;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    Order toOrder(OrderRequest request);

    OrderResponse toOrderResponse(Order order);

    OrderDetail toOrderDetail(Order order);

    OrderRequestEvent toOrderRequestEvent(OrderRequest request);

}