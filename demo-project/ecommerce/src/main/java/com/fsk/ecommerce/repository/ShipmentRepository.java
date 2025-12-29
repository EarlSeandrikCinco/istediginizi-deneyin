package com.fsk.ecommerce.repository;

import com.fsk.ecommerce.entity.Shipment;
import com.fsk.ecommerce.entity.ShipmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ShipmentRepository extends JpaRepository<Shipment, UUID> {
    Optional<Shipment> findByOrderId(UUID orderId);
    Optional<Shipment> findByTrackingNumber(UUID trackingNumber);
    List<Shipment> findByStatus(ShipmentStatus status);
    List<Shipment> findByCarrier(String carrier);
    List<Shipment> findByEstimatedDeliveryDateBetween(LocalDate startDate, LocalDate endDate);
    List<Shipment> findByActualDeliveryDateBetween(LocalDate startDate, LocalDate endDate);
    long countByStatus(ShipmentStatus status);
}


