package com.example.bestbuy_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BestBuyService {

    private final String apiKey = "7v5ACymuwyRImxnKD73u3piX";
    private final String baseUrl = "https://api.bestbuy.com/v1";

    private final RestTemplate restTemplate;
    private final PurchaseRepository purchaseRepository;

    @Autowired
    public BestBuyService(RestTemplate restTemplate, PurchaseRepository purchaseRepository) {
        this.restTemplate = restTemplate;
        this.purchaseRepository = purchaseRepository;
    }

    // Método para buscar productos
    public String searchProducts(String query) {
        String url = String.format("%s/products(search=%s)?apiKey=%s&format=json", baseUrl, query, apiKey);
        return restTemplate.getForObject(url, String.class);
    }

    // Método para obtener todos los productos
    public String getAllProducts() {
        String url = String.format("%s/products?apiKey=%s&format=json", baseUrl, apiKey);
        return restTemplate.getForObject(url, String.class);
    }

    public void purchaseProducts(List<PurchaseRequest> purchaseRequests) {
        for (PurchaseRequest request : purchaseRequests) {
            Purchase purchase = new Purchase(
                request.getProductId(),
                request.getProductName(),
                request.getProductDescription(),
                request.getQuantity(),
                request.getPrice(), // Ahora esto debería funcionar
                LocalDateTime.now()
            );
            purchaseRepository.save(purchase);
        }
    }


    //Nuevo metodo para consultar las compras realizadas
    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll(); // Asumiendo que tienes un método en el repositorio para obtener todas las compras
    }
}

