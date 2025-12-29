package com.fsk.ecommerce.repository;

import com.fsk.ecommerce.entity.Account;
import com.fsk.ecommerce.entity.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findByUserId(UUID userId);
    Optional<Account> findByAccountNumber(String accountNumber);
    List<Account> findByStatus(AccountStatus status);
    boolean existsByAccountNumber(String accountNumber);
}


