package com.otj.salesorderservice.model;

import org.springframework.stereotype.Component;

@Component
public class Item {
	
	private long id;
	private String name;
	private String desc;
	private long price;
	
	public Item() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Item(long id, String name, String desc, long price) {
		super();
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.price = price;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", desc=" + desc + ", price=" + price + "]";
	}
	
	
	

}

