package com.example.order.controller;


import com.example.order.api.StockService;
import com.example.order.pojo.Order;
import com.example.order.service.OrderService;
import org.apache.skywalking.apm.toolkit.trace.Tag;
import org.apache.skywalking.apm.toolkit.trace.Tags;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @RequestMapping("/add")
    public String add(){
        Order order = new Order();
        order.setProductId(9);
        order.setStatus(0);
        order.setTotalAmount(100);

        orderService.create(order);
        return "下单成功！";
    }

    @RequestMapping("/add2")
    public String add2(){
        Order order = new Order();
        order.setProductId(9);
        order.setStatus(0);
        order.setTotalAmount(100);

        orderService.create2(order);
        return "下单成功！";
    }

    @RequestMapping("/all")
    public List<Order> all(){
        return orderService.all();
    }

    @RequestMapping("/get/{id}")
    public Order get(@PathVariable long id){
        return orderService.get(id);
    }
}
