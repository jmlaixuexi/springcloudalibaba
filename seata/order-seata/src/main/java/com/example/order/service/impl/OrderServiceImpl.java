package com.example.order.service.impl;

import com.example.order.mapper.OrderMapper;
import com.example.order.pojo.Order;
import com.example.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    @Qualifier("restTemplate10")
    RestTemplate restTemplate;

    @Transactional
    @Override
    public Order create(Order order) {
        orderMapper.insert(order);

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("productId",order.getProductId());

        String msg = restTemplate.postForObject("http://localhost:8182/stock/reduct",params,String.class);

        //异常
        int i = 1/0;

        return order;
    }
}
