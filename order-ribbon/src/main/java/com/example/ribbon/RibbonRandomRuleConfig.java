package com.example.ribbon;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RibbonRandomRuleConfig {

    @Bean//ribbonRule
    public IRule iRule(){
        return new RandomRule();
    }

}
