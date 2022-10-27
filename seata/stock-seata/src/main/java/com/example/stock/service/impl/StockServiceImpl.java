package com.example.stock.service.impl;

import com.example.stock.mapper.StockMapper;
import com.example.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    StockMapper stockMapper;

    @Transactional
    @Override
    public void reduct(long productId) {
        System.out.println("更新商品：" + productId);
        stockMapper.reduct(productId);
    }
}
