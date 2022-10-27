package com.example.order.config;

import com.example.order.intercptor.feign.CustomFeignIntercptor;
import feign.Contract;
import feign.Logger;
import feign.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 全局配置：当使用@Configuration会将配置作用所有的服务提供方
 * 局部配置：如果只想针对某一个服务进行配置，就不要加@Configuration
 */
//@Configuration
public class FeignConfig {

    //@Bean
    public Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }

    /**
     * 修改契约配置，支持feign原生的注解
     * @return
     */
    //@Bean
    public Contract feignContract(){
        return new Contract.Default();
    }

    /**
     * 超时时间设置
     * @return
     */
    //@Bean
    public Request.Options options(){
        return new Request.Options(5000,10000);
    }

    /**
     * 自定义拦截器
     * @return
     */
    //@Bean
    public CustomFeignIntercptor customFeignIntercptor(){
        return new CustomFeignIntercptor();
    }
}
