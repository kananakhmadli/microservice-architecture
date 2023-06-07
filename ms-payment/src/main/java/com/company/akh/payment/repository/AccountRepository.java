package com.company.akh.payment.repository;

import com.company.akh.payment.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    @Query("SELECT a from Account a where a.userId=:userId")
    Optional<Account> findByUserId(@Param("userId") Long userId);

}