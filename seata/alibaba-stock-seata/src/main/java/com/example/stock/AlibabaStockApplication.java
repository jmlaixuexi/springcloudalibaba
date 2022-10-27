package com.example.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class AlibabaStockApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlibabaStockApplication.class,args);
    }

}
