package com.example.order.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.example.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/order")
public class OrderController {

    @RequestMapping(value = "/flow")
    //@SentinelResource(value = "flow" , blockHandler = "flowBlockHandler")
    public String flow() {
        return "正常访问";
    }

    public String flowBlockHandler(BlockException e){
        return "流控";
    }

    @RequestMapping(value = "/flowThread")
    @SentinelResource(value = "flowThread" , blockHandler = "flowThreadBlockHandler")
    public String flowThread() throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        return "正常访问";
    }

    public String flowThreadBlockHandler(BlockException e){
        return "流控";
    }



    @RequestMapping("/add")
    public String add(){
        System.out.println("下单成功！");
        return "生成订单";
    }
    @RequestMapping("/get")
    public String get(){
        return "查询订单";
    }

    @Autowired
    IOrderService orderService;

    @RequestMapping("/test1")
    public String test1(){
        return orderService.getUser();
    }

    @RequestMapping("/test2")
    public String test2(){
        return orderService.getUser();
    }
}
