package de.codecentric.mongoboot;

import org.springframework.data.mongodb.core.mapping.Field;

public class Item {

	int quantity;
	
	double price;
	
	@Field("desc")
	String description;

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
