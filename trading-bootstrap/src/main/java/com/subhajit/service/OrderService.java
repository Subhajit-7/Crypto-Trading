package com.subhajit.service;

import java.util.List;

import com.subhajit.domain.OrderType;
import com.subhajit.modal.Coin;
import com.subhajit.modal.Order;
import com.subhajit.modal.OrderItem;
import com.subhajit.modal.User;

public interface OrderService {

    Order createOrder(User user, OrderItem orderItem, OrderType orderType);

    Order getOrderById(Long orderId) throws Exception;

    List<Order> getAllOrdersOfUser(Long userId, OrderType orderType, String assetSymbol);

    Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception;
}
