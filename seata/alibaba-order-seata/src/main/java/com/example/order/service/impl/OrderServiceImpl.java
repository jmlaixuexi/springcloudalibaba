package com.example.order.service.impl;

import com.example.order.api.StockService;
import com.example.order.mapper.OrderMapper;
import com.example.order.pojo.Order;
import com.example.order.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.skywalking.apm.toolkit.trace.Tag;
import org.apache.skywalking.apm.toolkit.trace.Tags;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    StockService stockService;
    @GlobalTransactional
    @Override
    public Order create(Order order) {
        orderMapper.insert(order);

        stockService.reduct(order.getProductId());

        int i = 1/0;

        return order;
    }

    public Order create2(Order order) {
        orderMapper.insert(order);

        stockService.reduct(order.getProductId());

        return order;
    }

    @Trace
    @Tag(key="all",value = "returnedObj")
    @Override
    public List<Order> all() {
        return orderMapper.all();
    }

    @Trace
    @Tags({
            @Tag(key="get",value = "returnedObj"),
            @Tag(key="get",value = "arg[0]")
    })
    @Override
    public Order get(long id) {
        return orderMapper.get(id);
    }
}
