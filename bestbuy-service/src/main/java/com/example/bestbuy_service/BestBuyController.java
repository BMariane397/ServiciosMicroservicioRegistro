package com.example.bestbuy_service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bestbuy")
public class BestBuyController {
    
    private final BestBuyService bestBuyService;

    @Autowired
    public BestBuyController(BestBuyService bestBuyService) {
        this.bestBuyService = bestBuyService;
    }

    // Otros endpoints...

    @GetMapping("/purchases")
    public List<Purchase> getPurchases() {
        return bestBuyService.getAllPurchases();
    }
}

