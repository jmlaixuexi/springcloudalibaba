package com.example.order.mapper;

import com.example.order.pojo.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {

    void insert(Order order);

    List<Order> all();

    Order get(long id);
}
