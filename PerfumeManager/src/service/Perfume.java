package service;

import java.io.Serializable;

public class Perfume implements Serializable {
    private static final long serialVersionUID = 1L; 
    private String name;
    private String brand;
    private double price;
    private int quantity;
//constructor
    public Perfume(String name, String brand, double price, int quantity) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
    }
//getters setters
    public String getName() { 
    	return name; 
    	}
    public String getBrand() {
    	return brand; 
    	}
    public double getPrice() {
    	return price; 
    	}
    public int getQuantity() { 
    	return quantity; 
    	}

    public String toString() {
        return name + " - " + brand + " - $" + price + " - Qty: " + quantity;
    }
}
	