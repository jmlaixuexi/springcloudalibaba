package com.example.ribbon.rule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CustomRule extends AbstractLoadBalancerRule {
    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {

    }

    @Override
    public Server choose(Object key) {
        ILoadBalancer lb = this.getLoadBalancer();

        //获取当前请求的服务实例
        List<Server> reachableServers = lb.getReachableServers();

        int random = ThreadLocalRandom.current().nextInt(reachableServers.size());

        Server server = reachableServers.get(random);

        return server;
    }
}
