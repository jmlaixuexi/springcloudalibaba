package com.example.order.service;

import com.example.order.pojo.Order;

import java.util.List;

public interface OrderService {

    Order create(Order order);

    Order create2(Order order);

    List<Order> all();

    Order get(long id);
}
