package com.fsk.ecommerce.service;

import com.fsk.ecommerce.entity.Order;
import com.fsk.ecommerce.entity.OrderItem;
import com.fsk.ecommerce.entity.OrderStatus;
import com.fsk.ecommerce.entity.Product;
import com.fsk.ecommerce.entity.User;
import com.fsk.ecommerce.mapper.dto.OrderRequestDTO;
import com.fsk.ecommerce.repository.OrderItemRepository;
import com.fsk.ecommerce.repository.OrderRepository;
import com.fsk.ecommerce.repository.ProductRepository;
import com.fsk.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public UUID createOrderSync(OrderRequestDTO request) {
        return null;
    }
}
