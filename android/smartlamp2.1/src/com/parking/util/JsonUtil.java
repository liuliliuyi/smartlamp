package com.parking.util;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.an.cityselect.MyRegion;
import com.parking.model.City;
import com.parking.model.InCart;
import com.parking.model.NBData;
import com.parking.model.Order;
import com.parking.model.Parking;

import android.util.Log;

public class JsonUtil {
	/**
	 * 订单
	 */
	private static JSONArray jArray;
	private static JSONObject object;
	private static Order bean;
	private static City bean1;
	private static Parking bean3; 
	
	
	public static void getHot(String json, ArrayList<Order> beans) {

		if (json == null) {
			return;
		}
		
//		"body": {
//			"data": [{
//				"id": "a589830fd007407ba769bfcc9bd5f7d5",
//				"remarks": "",
//				"createDate": "2019-07-06 07:40:20",
//				"updateDate": "2019-07-06 22:50:49",
//				"name": "村屯智慧路灯1期",
		try {
			object = new JSONObject(json);
			JSONObject  object1= object.getJSONObject("body");
			jArray = object1.getJSONArray("data");
			if (jArray == null) {
				return;
			}
			for (int i = 0; i < jArray.length(); i++) {
				object = jArray.getJSONObject(i);
				if (object != null) {
					bean = new Order();
					bean.setId(object.getString("id"));
					bean.setName(object.getString("name"));
					JSONObject area =object.getJSONObject("area");
					bean.setId1(area.getString("id"));
					beans.add(bean);
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public static void getHot1(String json, ArrayList<InCart> beans) {

		if (json == null) {
			return;
		}
		
//		"body": {
//			"data": [{
//				"id": "a589830fd007407ba769bfcc9bd5f7d5",
//				"remarks": "",
//				"createDate": "2019-07-06 07:40:20",
//				"updateDate": "2019-07-06 22:50:49",
//				"name": "村屯智慧路灯1期",
		try {
			object = new JSONObject(json);
			JSONObject  object1= object.getJSONObject("body");
			jArray = object1.getJSONArray("data");
			if (jArray == null) {
				return;
			}
			for (int i = 0; i < jArray.length(); i++) {
				object = jArray.getJSONObject(i);
				if (object != null) {
					InCart bean = new InCart();
					bean.setGoodsId(object.getString("id"));
					bean.setGoodsName(object.getString("name"));
					bean.setCode(object.getString("code"));
					beans.add(bean);
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public static void getCity(String json, ArrayList<City> beans) {

		if (json == null) {
			return;
		}
		try {
			object = new JSONObject(json);
			jArray = object.getJSONArray("entity");
			if (jArray == null) {
				return;
			}
			for (int i = 0; i < jArray.length(); i++) {
				object = jArray.getJSONObject(i);
				if (object != null) {
					bean1 = new City();
					bean1.setId(String.valueOf(object.getInt("id")));
					bean1.setName(object.getString("name"));
					bean1.setPrice(String.valueOf(object.getInt("price")));
					bean1.setFreePlace(object.getString("freePlace"));

					bean1.setDistance(object.getString("latitude") + ","
							+ object.getString("longitude"));

					beans.add(bean1);
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void getfanxiangcar(String json, ArrayList<Parking> beans) {

		if (json == null) {
			return;
		}
		try {
			object = new JSONObject(json);
		    JSONObject	jArray1 = object.getJSONObject("entity");
			
//			for (int i = 0; i < jArray1.length(); i++) {
				//object = jArray.getJSONObject(i);
//				if (object != null) {
					bean3 = new Parking();
					bean3.setId(jArray1.getInt("id"));
//					bean1.setName(object.getString("name"));
//					bean1.setPrice(String.valueOf(object.getInt("price")));
//					bean1.setFreePlace(object.getString("freePlace"));

					bean3.setLongitude(jArray1.getString("longitude"));
					bean3.setLatitude(jArray1.getString("latitude"));
//							+ ","
//							+ object.getString("longitude"));

					beans.add(bean3);
//				}
//			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public static void getnav(String json, ArrayList<City> beans, String flag,
			String geoLat, String geoLng) {

		if (json == null) {
			return;
		}
		try {
			object = new JSONObject(json);
			jArray = object.getJSONArray("entity");
			if (jArray == null) {
				return;
			}
			for (int i = 0; i < jArray.length(); i++) {
				object = jArray.getJSONObject(i);
				if (object != null) {
					bean1 = new City();
					if(flag.length()>3){
//					if(flag.equals(object.getString("name"))||flag.equals(object.getString("name").substring(2))||flag.equals(object.getString("name").substring(3))||flag.equals(object.getString("name").substring(4))
//							||flag.substring(0,flag.length()-1).equals(object.getString("name"))
//							||flag.substring(0,flag.length()-2).equals(object.getString("name"))
//							||flag.substring(1,flag.length()-1).equals(object.getString("name"))
//							||flag.substring(2,flag.length()-2).equals(object.getString("name"))
//							||flag.substring(3,flag.length()-1).equals(object.getString("name"))
//							||flag.substring(4,flag.length()-2).equals(object.getString("name"))
//							||flag.substring(0,flag.length()-1).equals(object.getString("name").substring(2))
//							||flag.substring(0,flag.length()-1).equals(object.getString("name").substring(3))
//							||flag.substring(0,flag.length()-2).equals(object.getString("name").substring(2))
//							||flag.substring(0,flag.length()-2).equals(object.getString("name").substring(3))
						if(object.getString("name").contains(flag)||object.getString("name").contains(flag.substring(0))||object.getString("name").contains(flag.substring(1))||object.getString("name").contains(flag.substring(2))
								||object.getString("name").contains(flag.substring(flag.length()-1))||object.getString("name").contains(flag.substring(1))||object.getString("name").contains(flag.substring(flag.length()-2)
								)
							){
						bean1.setId(String.valueOf(object.getInt("id")));
						bean1.setName(object.getString("name"));
						bean1.setPrice(String.valueOf(object.getInt("price")));
						bean1.setFreePlace(object.getString("freePlace"));
						bean1.setDistance(object.getString("latitude") + ","
								+ object.getString("longitude"));
						beans.add(bean1);
					}
					
					else if ((!(object.getString("name").contains(flag)||object.getString("name").contains(flag.substring(0))||object.getString("name").contains(flag.substring(1))||object.getString("name").contains(flag.substring(2))
							||object.getString("name").contains(flag.substring(flag.length()-1))||object.getString("name").contains(flag.substring(1))||object.getString("name").contains(flag.substring(flag.length()-2)
							)))&&(flag.contains("附近") || flag.contains("最近"))) {
						String distance = MapDistance.getDistance(geoLat,
								geoLng, object.getString("latitude"),
								object.getString("longitude"));

						if (Integer.parseInt(distance) <= 10) {
							bean1.setId(String.valueOf(object.getInt("id")));
							bean1.setName(object.getString("name"));
							bean1.setPrice(String.valueOf(object
									.getInt("price")));
							bean1.setFreePlace(object.getString("freePlace"));
							bean1.setDistance(object.getString("latitude")
									+ "," + object.getString("longitude"));
							beans.add(bean1);
						}

					} 
					else if ((!(object.getString("name").contains(flag)||object.getString("name").contains(flag.substring(0))||object.getString("name").contains(flag.substring(1))||object.getString("name").contains(flag.substring(2))
							||object.getString("name").contains(flag.substring(flag.length()-1))||object.getString("name").contains(flag.substring(1))||object.getString("name").contains(flag.substring(flag.length()-2)
							)))&&(flag.contains("车位多"))) {
//						String distance = MapDistance.getDistance(geoLat,
//								geoLng, object.getString("latitude"),
//								object.getString("longitude"));
						
						if (Integer.parseInt(object.getString("freePlace")) >100) {
							bean1.setId(String.valueOf(object.getInt("id")));
							bean1.setName(object.getString("name"));
							bean1.setPrice(String.valueOf(object
									.getInt("price")));
							bean1.setFreePlace(object.getString("freePlace"));
							bean1.setDistance(object.getString("latitude")
									+ "," + object.getString("longitude"));
							beans.add(bean1);
						}
					}
					}else{
						if (flag.contains("北京")
								&& object.getString("name").contains("北京")) {
							bean1.setId(String.valueOf(object.getInt("id")));
							bean1.setName(object.getString("name"));
							bean1.setPrice(String.valueOf(object.getInt("price")));
							bean1.setFreePlace(object.getString("freePlace"));
							bean1.setDistance(object.getString("latitude") + ","
									+ object.getString("longitude"));
							beans.add(bean1);
						}
						else if(object.getString("name").contains(flag.trim())){
							bean1.setId(String.valueOf(object.getInt("id")));
							bean1.setName(object.getString("name"));
							bean1.setPrice(String.valueOf(object.getInt("price")));
							bean1.setFreePlace(object.getString("freePlace"));
							bean1.setDistance(object.getString("latitude") + ","
									+ object.getString("longitude"));
							beans.add(bean1);
						}
					
					
					
					}
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public static void getParking(String json, ArrayList<Parking> datas,String id) {
		if (json == null) {
			return;
		}
		try {
			object = new JSONObject(json);
			jArray = object.getJSONArray("entity");
			if (jArray == null) {
				return;
			}
			for (int i = 0; i < jArray.length(); i++) {
				object = jArray.getJSONObject(i);
				if (object != null) {
					bean3 = new Parking();
				 //bean3.setLatitude(object.getString(""));
					//"specificationValues":[{"id":19,"value":"9号车位"}]
					JSONArray specificationValues=object.getJSONArray("specificationValues");
					JSONObject value1=specificationValues.getJSONObject(0);
					String value=value1.getString("value");
					//sequence
					//bean3.setId(value.substring(0,value.indexOf("号")));
					bean3.setId(object.getInt("sequence"));
					bean3.setLatitude(object.getString("latitude"));
					bean3.setLongitude(object.getString("longitude"));
					bean3.setParkingid(id);
					bean3.setState(object.getString("terminalType"));
					bean3.setVip(object.getString("status"));
					
					datas.add(bean3);
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
}

	public static void getnewalarm(JSONObject json, ArrayList<City> datas, LatLng latLng) {

		if (json == null) {
			return;
		}
		try {
//            JSONObject json1=json.getJSONObject("body");
			jArray = json.getJSONArray("data");
			if (jArray == null) {
				return;
			}
		//	float distance = AMapUtils.calculateLineDistance(latLng1,latLng2);
			for (int i = 0; i < jArray.length(); i++) {
				object = jArray.getJSONObject(i);
				if (object != null) {
					City bean = new City();
				    if(object.getString("latitude").equals("")||object.getString("latitude")==null||object.getString("longitude").equals("")||object.getString("longitude")==null){
				    	bean.setDistance("未知");	
				    }else{
				    	LatLng latLng1 = new LatLng(Double.parseDouble(object.getString("latitude")),Double.parseDouble(object.getString("longitude")));	
				    	bean.setDistance(String.valueOf(AMapUtils.calculateLineDistance(latLng,latLng1)/1000));
				    }
					
				   // dealStatus
				    bean.setDealStatus(object.getString("dealStatus"));
					bean.setLatitude(object.getString("latitude"));
					bean.setLongitude(object.getString("longitude"));
					bean.setCreateDate(object.getString("updateTime"));
					bean.setSn(object.getString("sn"));
					bean.setDescription(object.getString("description"));
					
					datas.add(bean);
				}
			}


		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void getSnStaus(JSONObject json, ArrayList<NBData> datas) {

		if (json == null) {
			return;
		}
		try {

			jArray = json.getJSONArray("data");
			if (jArray == null) {
				return;
			}
			// {"code":200,"success":true,"data":["0211A0005E"],"msg":"操作成功"}
			for (int i = 0; i < jArray.length(); i++) {
//				object = jArray.getJSONObject(i);
				
					NBData bean = new NBData();
				    
				    bean.setSn(jArray.get(i).toString());
				
					
					datas.add(bean);
				
			}


		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void getnewalarm1(JSONObject json, ArrayList<City> datas, LatLng latLng,String flag) {

		if (json == null) {
			return;
		}
		try {
            JSONObject json1=json.getJSONObject("body");
			jArray = json1.getJSONArray("data");
			if (jArray == null) {
				return;
			}
		//	float distance = AMapUtils.calculateLineDistance(latLng1,latLng2);
			for (int i = 0; i < jArray.length(); i++) {
				object = jArray.getJSONObject(i);
				if (object != null) {
					
					
					if(object.getString("sn").contains(flag)||object.getString("sn").contains(flag.substring(0))||object.getString("sn").contains(flag.substring(1))||object.getString("sn").contains(flag.substring(2))
							||object.getString("sn").contains(flag.substring(flag.length()-1))||object.getString("sn").contains(flag.substring(1))||object.getString("sn").contains(flag.substring(flag.length()-2)
							)
						){
					
					
					
					City bean = new City();
				    if(object.getString("latitude").equals("")||object.getString("latitude")==null||object.getString("longitude").equals("")||object.getString("longitude")==null){
				    	bean.setDistance("未知");	
				    }else{
				    	LatLng latLng1 = new LatLng(Double.parseDouble(object.getString("latitude")),Double.parseDouble(object.getString("longitude")));	
				    	bean.setDistance(String.valueOf(AMapUtils.calculateLineDistance(latLng,latLng1)/1000));
				    }
					
					
					bean.setLatitude(object.getString("latitude"));
					bean.setLongitude(object.getString("longitude"));
					bean.setCreateDate(object.getString("createDate"));
					bean.setSn(object.getString("sn"));
					bean.setDescription(object.getString("description"));
					
					datas.add(bean);
				}
			}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	//省份解析
		public static void getcity1(JSONObject json, ArrayList<MyRegion> datas) {

			if (json == null) {
				return;
			}
			try {
//				object = new JSONObject(json);
				//
				jArray = json.getJSONArray("data");
				if (jArray == null) {
					return;
				}

				for (int i = 0; i < jArray.length(); i++) {
					object = jArray.getJSONObject(i);
					if (object != null) {
						MyRegion course = new MyRegion();
						course.setId(object.getString("id"));
						course.setName(object.getString("title"));
						
						datas.add(course);
					}
				}
			} catch (JSONException e) {

				e.printStackTrace();
			}

		}
}