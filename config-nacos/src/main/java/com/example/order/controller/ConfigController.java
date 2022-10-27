package com.example.order.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
@RefreshScope //开启动态获取配置
public class ConfigController {

    @Value("${user.name}")
    private String userName;

    @RequestMapping("/userName")
    public String userName(){
        return userName;
    }
}
