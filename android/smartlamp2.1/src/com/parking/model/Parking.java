package com.parking.model;

public class Parking {
	private String longitude;
	private String latitude;
    private String parkingid;
    private String state;
    private String vip;
	private int id;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getParkingid() {
		return parkingid;
	}
	public void setParkingid(String parkingid) {
		this.parkingid = parkingid;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getVip() {
		return vip;
	}
	public void setVip(String vip) {
		this.vip = vip;
	}

}
