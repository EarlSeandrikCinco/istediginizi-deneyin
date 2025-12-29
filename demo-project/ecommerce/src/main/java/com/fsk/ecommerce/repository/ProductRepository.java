package com.fsk.ecommerce.repository;

import com.fsk.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findBySku(String sku);
    List<Product> findByCategory(String category);
    List<Product> findByBrand(String brand);
    List<Product> findByStockQuantityGreaterThan(Integer quantity);
    boolean existsBySku(String sku);
}


