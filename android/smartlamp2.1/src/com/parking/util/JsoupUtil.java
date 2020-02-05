package com.parking.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.parking.model.Movie;




/**
 * 
 * @author wwj_748
 * @date 2014/8/10
 */
public class JsoupUtil {
	public static boolean contentFirstPage = true; // 第一页
	public static boolean contentLastPage = true; // 最后一页
	public static boolean multiPages = false; // 多页

	public static void resetPages() {
		contentFirstPage = true;
		contentLastPage = true;
		multiPages = false;
	}



	public static List<Movie> getBlogCommentList2(String str, int pageIndex,
			int pageSize) {
		List<Movie> list = new ArrayList<Movie>();
		try {
			// 创建一个json对象
			JSONObject jsonObject = new JSONObject(str);
			JSONArray jsonArray = jsonObject.getJSONArray("data");

			int index = 0;
			int len = jsonArray.length();

			// 如果评论数大于20
			if (len > 20) {
				index = (pageIndex * pageSize) - 20;
			}

			if (len < pageSize && pageIndex > 1) {
				return list;
			}

			if ((pageIndex * pageSize) < len) {
				len = pageIndex * pageSize;
			}

			for (int i = index; i < len; i++) {
				JSONObject item = jsonArray.getJSONObject(i);
				Movie movie = new Movie();
				movie.setMovietupian(item.getString("userpic"));
				movie.setId(item.getString("topicid"));

				movie.setFabiaotumu(item.getString("title"));
				movie.setShijian(item.getString("time"));
				movie.setHufushu(item.getString("pcount"));
				movie.setFabiaoren(item.getString("name"));
				movie.setNeirong(item.getString("content"));

				list.add(movie);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}



	public static List<Movie> getBlogCommentList3(String str, int pageIndex,
			int pageSize) {
		List<Movie> list = new ArrayList<Movie>();
		try {
			// 创建一个json对象
			JSONObject jsonObject = new JSONObject(str);
			JSONArray jsonArray = jsonObject.getJSONArray("data");

			int index = 0;
			int len = jsonArray.length();

			// 如果评论数大于20
			if (len > 20) {
				index = (pageIndex * pageSize) - 20;
			}

			if (len < pageSize && pageIndex > 1) {
				return list;
			}

			if ((pageIndex * pageSize) < len) {
				len = pageIndex * pageSize;
			}

			for (int i = index; i < len; i++) {
				JSONObject item = jsonArray.getJSONObject(i);

				// content评论的内容，commentid评论的id,time时间，name用户呢称，userpic

				Movie movie = new Movie();
				movie.setMovietupian(Common.tupian_url1
						+ item.getString("userpic"));
				movie.setId(item.getString("commentid"));

				movie.setShijian(item.getString("time"));

				movie.setFabiaoren(item.getString("name"));
				movie.setNeirong(item.getString("content"));

				list.add(movie);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

	
}
