package com.example.order.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Order {
    private long productId;
    private int status;
    private long totalAmount;
}
