package com.subhajit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.subhajit.modal.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);
}
