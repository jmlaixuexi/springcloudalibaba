package com.example.order.intercptor.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomFeignIntercptor implements RequestInterceptor {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("xxx","xxx");
        requestTemplate.query("111","111");
        requestTemplate.uri("/9");
        logger.info("feign拦截器！");
    }
}
