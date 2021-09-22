package com.robinzhou.websec.controller;

import com.robinzhou.websec.entity.Stock;
import com.robinzhou.websec.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/")
    public List<Stock> index() {
        return stockService.getAll();
    }
}
