package com.example.productWebF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")

public class ProductController {
	
	 @Autowired
	    private ProductRepository productRepository;

	 @PostMapping
	    public ResponseEntity<List<Product>> addProducts(@RequestBody List<Product> products) {
	        if (products.isEmpty() || products.stream().anyMatch(p -> p.getName() == null || p.getPrice() == null)) {
	            return ResponseEntity.badRequest().body(null);
	        }
	        List<Product> savedProducts = productRepository.saveAll(products);
	        return ResponseEntity.ok(savedProducts);
	    }

	    @GetMapping
	    public List<Product> getAllProducts() {
	        return productRepository.findAll();
	    }
	    
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
	        if (productRepository.existsById(id)) {
	            productRepository.deleteById(id);
	            return ResponseEntity.noContent().build();
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	    
	    @PutMapping("/{id}")
	    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
	        if (!productRepository.existsById(id)) {
	            return ResponseEntity.notFound().build();
	        }
	        product.setId(id);
	        Product updatedProduct = productRepository.save(product);
	        return ResponseEntity.ok(updatedProduct);
	    }

}
