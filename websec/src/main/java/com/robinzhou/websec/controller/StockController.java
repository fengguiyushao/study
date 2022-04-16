package com.robinzhou.websec.controller;

import com.robinzhou.websec.entity.Stock;
import com.robinzhou.websec.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StockController {

    private Logger logger = LoggerFactory.getLogger(StockController.class);

    @Autowired
    private StockService stockService;

    @GetMapping("/select")
    public List<Stock> selectForSqlMap(@RequestParam String code) {
        logger.info("[selectForSqlMap] param code = {}", code);
        try {
            return stockService.selectForSqlMap(code);
        } catch (Exception e) {
            return null;
        }
    }
}
