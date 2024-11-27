package com.example.bestbuy_service;

public class PurchaseRequest {
    private String productId;
    private String productName;
    private String productDescription;
    private int quantity;
    private double price; 

    public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	// Getters y Setters
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
