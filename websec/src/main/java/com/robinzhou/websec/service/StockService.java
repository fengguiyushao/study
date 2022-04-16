package com.robinzhou.websec.service;

import com.robinzhou.websec.entity.Stock;
import com.robinzhou.websec.entity.StockExample;
import com.robinzhou.websec.mapper.StockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    @Autowired
    private StockMapper stockMapper;

    public List<Stock> selectForSqlMap(String code) {
        return stockMapper.selectForSqlMap(code);
    }
}
