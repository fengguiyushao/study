package com.robinzhou.websec.mapper;

import com.robinzhou.websec.entity.Stock;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockMapper {
    List<Stock> getAll();
}
