package com.subhajit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.subhajit.modal.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    
}
