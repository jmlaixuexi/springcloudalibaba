package com.example.order.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/order")
public class OrderController {


    @Autowired
    @Qualifier("restTemplate1")
    RestTemplate restTemplate;

    @RequestMapping("/add")
    public String add(){
        System.out.println("下单成功！");
        String msg = restTemplate.getForObject("http://localhost:8080/stock/reduct",String.class);
        return "Hello world" + msg;
    }

}
