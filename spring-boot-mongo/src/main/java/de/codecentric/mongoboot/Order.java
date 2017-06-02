package de.codecentric.mongoboot;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class Order {

	@Id String id;
	
	@Field("custInfo")
	String text;
	
	@Field("date")
	Date ordered;
	
	List<Item> items;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getOrdered() {
		return ordered;
	}

	public void setOrdered(Date ordered) {
		this.ordered = ordered;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
}
