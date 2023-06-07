package com.company.akh.order.error;

import az.kapitalbank.atlas.lib.common.error.ErrorCode;

public enum ErrorCodes implements ErrorCode {

    ORDER_NOT_FOUND("Order not found, uid: {}");

    private final String message;

    ErrorCodes(String message) {
        this.message = message;
    }

    @Override
    public String code() {
        return this.name();
    }

    @Override
    public String message() {
        return message;
    }

}
