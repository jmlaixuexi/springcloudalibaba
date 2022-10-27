package com.example.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableFeignClients
public class AlibabaOrderApplication {
    //-javaagent:D:\work\tool\install_package\apache-skywalking-java-agent-8.12.0\skywalking-agent\skywalking-agent.jar -Dskywalking.agent.service_name=alibaba-order-seata -Dskywalking.collector.backend_service=127.0.0.1:11800
    public static void main(String[] args) {
        SpringApplication.run(AlibabaOrderApplication.class,args);
    }

}
