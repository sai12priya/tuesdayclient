package com.example.ims.Entity;

import org.springframework.stereotype.Component;


@Component
public class Product {
	
	int id;
	String name;
	String description;
	int qty;
	double price;
	public Product() {}
	public Product(int id, String name, String description, int qty, double price) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.qty = qty;
		this.price = price;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

}
