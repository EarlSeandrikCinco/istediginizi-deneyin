package com.fsk.ecommerce.repository;

import com.fsk.ecommerce.entity.Order;
import com.fsk.ecommerce.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByUserId(UUID userId);
    List<Order> findByStatus(OrderStatus status);
    List<Order> findByUserIdAndStatus(UUID userId, OrderStatus status);
    List<Order> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    long countByUserId(UUID userId);
    long countByStatus(OrderStatus status);
}


