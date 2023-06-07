package com.company.akh.order.entity;

import com.company.akh.order.dto.InventoryStatus;
import com.company.akh.order.dto.OrderStatus;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @SequenceGenerator(name = "seq_order", allocationSize = 1)
    @GeneratedValue(generator = "seq_order", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String uid;
    private Long productId;
    private Integer quantity;
    private Long userId;
    private String declinedReason;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    private InventoryStatus inventoryStatus;

    @CreationTimestamp
    @Column(name = "create_date", nullable = false, updatable = false)
    private LocalDateTime createDate;

    @UpdateTimestamp
    @Column(name = "update_date", nullable = false)
    private LocalDateTime updateDate;

}