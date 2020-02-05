package com.parking.model;

/**
 * 页面实体�?
 * 
 * @author wwj_748
 * @date 2014/8/9
 */
public class Page {
	private int page = 1; // 记录页面�?
	private int contentMutiPages; // 
	public boolean contentFirstPage = true; // 内容第一�?

	// 设置�?��页面
	public void setPageStart() {
		page = 2;
	}

	// 设置�?
	public void setPage(int num) {
		page = num;
	}

	// 获取当前�?
	public String getCurrentPage() {
		return page + "";
	}

	// 添加页面
	public void addPage() {
		page++;
	}
}
