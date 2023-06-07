package com.company.akh.order.error;

import com.company.akh.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUidValidator implements ConstraintValidator<UniqueUid, String> {

    @Autowired
    public OrderRepository orderRepository;

    @Override
    public boolean isValid(String uid, ConstraintValidatorContext context) {
        return orderRepository.checkByUid(uid);
    }

}