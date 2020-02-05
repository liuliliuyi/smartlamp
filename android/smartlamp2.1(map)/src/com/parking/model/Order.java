package com.parking.model;

public class Order {
    private String id;
	private String id1;

	public String getId1() {
		return id1;
	}

	public void setId1(String id1) {
		this.id1 = id1;
	}

	private String name;
	private String price;
	private String date;
	private String sdatus;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getSdatus() {
		return sdatus;
	}

	public void setSdatus(String sdatus) {
		this.sdatus = sdatus;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
