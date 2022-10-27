package com.example.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
@Component
public class StockFeignServiceFallback implements  StockFeignService{

    @Override
    public String reduct() {
        return "降级了！";
    }

    @Override
    public String reduct2() {
        return "降级了！";
    }
}
