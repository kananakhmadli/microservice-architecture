package com.company.akh.order.repository;

import com.company.akh.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT u from Order u where u.uid=:uid")
    Order findByUid(@Param("uid") String uid);

    @Query("SELECT u from Order u where u.uid=:uid")
    Optional<Order> findOptionalOrderByUid(@Param("uid") String uid);

    @Query("SELECT CASE WHEN count (o.uid)> 0 THEN false ELSE true END FROM Order o WHERE o.uid=:e")
    boolean checkByUid(@Param("e") String email);

}