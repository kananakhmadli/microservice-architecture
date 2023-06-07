package com.company.akh.order.controller;

import com.company.akh.order.dto.OrderDetail;
import com.company.akh.order.dto.OrderResponse;
import com.company.akh.order.dto.request.OrderRequest;
import com.company.akh.order.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Order service", description = "This order service used for ordering process")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderResponse create(@Valid @RequestBody OrderRequest request) {
        return orderService.create(request);
    }

    @GetMapping("/{uid}")
    public OrderResponse getStatusByUid(@PathVariable String uid) {
        return orderService.getStatusByUid(uid);
    }

    @GetMapping("/{uid}/detail")
    public OrderDetail getDetailByUid(@PathVariable String uid) {
        return orderService.getDetailByUid(uid);
    }

}