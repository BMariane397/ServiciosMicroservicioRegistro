package com.example.bestbuy_service;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/bestbuy")  // Añadido para que la ruta coincida con la URL del frontend
public class ProductController {

    private final BestBuyService bestBuyService;

    public ProductController(BestBuyService bestBuyService) {
        this.bestBuyService = bestBuyService;
    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam String query) {
        return bestBuyService.searchProducts(query);
    }
    
    // Nuevo endpoint para obtener todos los productos
    @GetMapping("/all")
    public String getAllProducts() {
        return bestBuyService.getAllProducts();
    }

    @PostMapping("/purchase")
    public ResponseEntity<String> purchaseProducts(@RequestBody List<PurchaseRequest> purchaseRequests) {
        bestBuyService.purchaseProducts(purchaseRequests);
        return ResponseEntity.ok("Purchase successful"); // Devuelve una respuesta adecuada
    }

}
