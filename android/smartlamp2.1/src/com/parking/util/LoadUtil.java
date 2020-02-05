package com.parking.util;


import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

//讨论最多赠送几样
public class LoadUtil {

	Context context;
	List<HashMap<String, Object>> list = null;

	List<HashMap<String, Object>> list7 = null;
	List<Map<String, Object>> list8 = null;
	HashMap<String, Object> map = null;

	SQLiteDatabase db = null;

	Cursor c = null;
	String sql = null;

	public LoadUtil(Context context) {
		this.context = context;
		// 把assets里的数据库复制到数据存储位置
		db = WriterDB.openDatabase(context);

	}

	// OrderDesp 删除一个 参数 dishid
	public void deleteOrderDespOne(Object dishid) {
int wzdlid=Integer.parseInt((String) dishid);
		try {

			sql = "delete from wenzhang where wzdlid=?";
			db.execSQL(sql, new Object[] { wzdlid });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// OrderDesp 删除全部
	public void deleteOrderDespAll() {
		sql = "delete from orderdesp ";
		db.execSQL(sql);
	}

	// OrderDesp 获得原总价；
	public double getTotal() {
		sql = "select sum(price*qty) from orderdesp";
		c = db.rawQuery(sql, null);
		c.moveToNext();
		double sum = c.getDouble(0);
		return sum;
	}

	public void closedb() {
		db.close();
		c = null;
		sql = null;
	}

	public List<HashMap<String, Object>> name(String account) {
		list7 = new ArrayList<HashMap<String, Object>>();

		sql = "select permission,url from user where account = ?";
		try {
			c = db.rawQuery(sql, new String[] { account });

			System.out.println("cur " + c.getCount());
			while (c.moveToNext()) {

				map = new HashMap<String, Object>();
				String neirong = c.getString(c.getColumnIndex("url"));
				map.put("neirong", neirong);
				String picurl = c.getString(c.getColumnIndex("permission"));
				map.put("picurl", picurl);

				list7.add(map);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (c != null) {
				try {
					c.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return list7;
	}

	public String wzidfirst(String account) {
		String name = null;
		sql = "select wenzhangurl from wenzhang where wzid = ?";
		try {
			c = db.rawQuery(sql, new String[] {account});

			while (c.moveToNext()) {
				name = c.getString(c.getColumnIndex("wenzhangurl"));
				System.out.println("sql name " + name);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (c != null) {
			try {
				c.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return name;
	}

	// 插入测试 用户数据
	public void insert_name(int id,String longitude,String latitude, String parkingid, String state
			,String vip) {

		sql = "insert into gps (id,longitude,latitude,parkingid,state,vip) values (?,?,?,?,?,?)";
		try {
			Object values[] = {id,longitude,latitude,parkingid,state,vip};
			db.execSQL(sql,values);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 插入测试 用户数据
		public void insert_name1(int id,String longitude,String latitude, String parkingid, String state
				,String vip) {

			sql = "insert into gps1 (id,longitude,latitude,parkingid,state,vip) values (?,?,?,?,?,?)";
			try {
				Object values[] = {id,longitude,latitude,parkingid,state,vip};
				db.execSQL(sql,values);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	
	
	
	// 删除测试 用户数据
		public void delete_name(String parkingid) {
			
			sql = "delete from gps where parkingid=?";
			try {
				Object values[] = {parkingid};
				db.execSQL(sql,values);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 删除测试 用户数据
		public void delete_name1(String parkingid) {
					
					sql = "delete from gps1 where parkingid=?";
					try {
						Object values[] = {parkingid};
						db.execSQL(sql,values);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
	
	
	
	public void update(String wzid, String wzdlid,String oldpid, String wenzhangneirong,
			String wenzhangurl) {
		try {
			sql = "update wenzhang set wenzhangneirong=?,wenzhangurl=?,oldpid=? where wzid =? and wzdlid=? ";
			db.execSQL(sql, new Object[] {wenzhangneirong, wenzhangurl,oldpid,wzid,wzdlid});

		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}
	

	public List<Map<String, Object>> shuaxin(String parkingid) {
		list8 = new ArrayList<Map<String, Object>>();
		try {
		sql = "select longitude,latitude,parkingid,state,vip,id from gps where parkingid=? order by id asc";
	
		c = db.rawQuery(sql,new String[]{parkingid});
		while (c.moveToNext()) {
			map = new HashMap<String, Object>();
			String longitude = c.getString(c.getColumnIndex("longitude"));
		     map.put("longitude", longitude);
			
			String latitude = c.getString(c.getColumnIndex("latitude"));
			map.put("latitude", latitude);
			
			
			String parkingid1 = c.getString(c.getColumnIndex("parkingid"));
			map.put("parkingid", parkingid1);
			
			

			String status = c.getString(c.getColumnIndex("state"));
			map.put("state", status);
			
			String vip = c.getString(c.getColumnIndex("vip"));
			map.put("vip",vip);
			
			
			int id = c.getInt(c.getColumnIndex("id"));
			map.put("id", id);
			
		
			//setState
			//object.getString("status")
			
			list8.add(map);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (c != null) {
			try {
				c.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list8;
	}

	public List<Map<String, Object>> shuaxin1(String parkingid) {
		list8 = new ArrayList<Map<String, Object>>();
		try {
		sql = "select longitude,latitude,parkingid,state,vip,id from gps1 where parkingid=? order by id asc";
	
		c = db.rawQuery(sql,new String[]{parkingid});
		while (c.moveToNext()) {
			map = new HashMap<String, Object>();
			String longitude = c.getString(c.getColumnIndex("longitude"));
		     map.put("longitude", longitude);
			
			String latitude = c.getString(c.getColumnIndex("latitude"));
			map.put("latitude", latitude);
			
			
			String parkingid1 = c.getString(c.getColumnIndex("parkingid"));
			map.put("parkingid", parkingid1);
			
			

			String status = c.getString(c.getColumnIndex("state"));
			map.put("state", status);
			
			String vip = c.getString(c.getColumnIndex("vip"));
			map.put("vip",vip);
			
			
			String id = c.getString(c.getColumnIndex("id"));
			map.put("id", id);
			
		
			//setState
			//object.getString("status")
			
			list8.add(map);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (c != null) {
			try {
				c.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list8;
	}

	
	public List<Map<String, Object>> endxy(String id,String parkingid) {
		list8 = new ArrayList<Map<String, Object>>();
		try {
		sql = "select longitude,latitude from gps where id=? and parkingid=? order by id asc";
	
		c = db.rawQuery(sql,new String[]{id,parkingid});
		while (c.moveToNext()) {
			map = new HashMap<String, Object>();
			String longitude = c.getString(c.getColumnIndex("longitude"));
		     map.put("longitude", longitude);
			
			String latitude = c.getString(c.getColumnIndex("latitude"));
			map.put("latitude", latitude);
			
			list8.add(map);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (c != null) {
			try {
				c.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list8;
	}

	
	
	
	
	
	public void delete(Object dishid){
		int wzdlid1=Integer.parseInt((String) dishid);
		sql = "delete from wenzhang where wzdlid=?";
		db.execSQL(sql, new Object[]{wzdlid1});
	}	
	}


