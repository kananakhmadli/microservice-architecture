package com.company.akh.payment.messaging.inventory;

import com.company.akh.payment.config.kafka.KafkaConstants;
import com.company.akh.payment.dto.PaymentStatus;
import com.company.akh.payment.entity.Account;
import com.company.akh.payment.messaging.event.InventoryRequest;
import com.company.akh.payment.messaging.event.PaymentResponse;
import com.company.akh.payment.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryHandler {

    private final AccountRepository accountRepository;
    private final KafkaTemplate<String, Object> inventoryResponseKafkaTemplate;

    @Transactional
    public void onInventoryReceivedEvent(InventoryRequest event) {
        log.info("onInventoryReceivedEvent: {}", event.getUid());
        Optional<Account> optionalAccount = accountRepository.findByUserId(event.getUserId());
        var paymentResponse = new PaymentResponse();
        paymentResponse.setUid(event.getUid());

        optionalAccount.ifPresentOrElse(
                account -> {
                    BigDecimal totalAmount =
                            event.getAmount().multiply(BigDecimal.valueOf(event.getQuantity()));

                    if (hasEnoughBalance(totalAmount, account.getAmount())) {
                        BigDecimal newBalance = account.getAmount().subtract(totalAmount);
                        account.setAmount(newBalance);
                        accountRepository.save(account);

                        paymentResponse.setStatus(PaymentStatus.PAYMENT_COMPLETED);
                    } else {
                        paymentResponse.setStatus(PaymentStatus.PAYMENT_FAILED);
                        paymentResponse.setDescription("Not enough money in account");
                    }
                },
                () -> {
                    paymentResponse.setStatus(PaymentStatus.PAYMENT_FAILED);
                    paymentResponse.setDescription("Account not found: id " + event.getUserId());
                }
        );

        inventoryResponseKafkaTemplate.send(KafkaConstants.TOPIC_PAYMENT_RESPONSE, paymentResponse);

        log.info("onInventoryReceivedEvent processed successfully");
    }

    private boolean hasEnoughBalance(BigDecimal totalAmount, BigDecimal amountInAccount) {
        return amountInAccount.compareTo(totalAmount) >= 0;
    }

}