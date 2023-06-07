package com.company.akh.order.dto.request;

import com.company.akh.order.error.UniqueUid;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class OrderRequest {

    @NotEmpty
    @UniqueUid
    private String uid;

    @NotNull
    private Integer userId;

    @NotNull
    private Integer productId;

    @NotNull
    @Positive
    private Integer quantity;

}