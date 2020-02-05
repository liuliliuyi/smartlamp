package com.parking.model;

public class City {
	public String name;
	public String id;
	public String distance;
	public String price;
    public  String freePlace;
    public String dealStatus;
	
//	"id": "eb05f82807a7409aa60d411d63d98242",
//	"remarks": null,
//	"createDate": "2019-06-28 14:03:16",
//	"updateDate": "2019-06-28 14:03:16",
//	"sn": "011161642C",
//	"qrcode": null,
//	"longitude": "109.456099",
//	"latitude": "24.988586",
//	"img": null,
//	"dealStatus": "0",
//	"imei": "0867726037567056",
//	"faultType": "2",
//	"description": "����״̬:������������Ϊ0",
//	"latelyMonth": 0
    
    public String getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}

	private String sn;
    public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	private String longitude;
    private String  latitude;
    private String description;
    private String createDate;
  

	public City(String name, String id, String distance, String price) {
		super();
		this.name = name;
		this.id = id;
		this.distance = distance;
		this.price = price;
	}

	public City() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	public String getFreePlace() {
		return freePlace;
	}

	public void setFreePlace(String freePlace) {
		this.freePlace = freePlace;
	}
}
