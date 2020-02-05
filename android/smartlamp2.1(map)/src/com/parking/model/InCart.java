package com.parking.model;

import java.io.Serializable;




public class InCart  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3584698829991048916L;
	String cart_id;
	String code;
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCart_id() {
		return cart_id;
	}

	public void setCart_id(String cart_id) {
		this.cart_id = cart_id;
	}
    String summemoy;
	public String getSummemoy() {
		return summemoy;
	}

	public void setSummemoy(String summemoy) {
		this.summemoy = summemoy;
	}
	String goodsId;	//id
	
	String goodsName;	//名称
	
	String goodsIcon;	//图片

	String goodsType;	//种类
	
	String goodsPrice;	//价格
	
	String goodsPercent;	//好评
	
	int goodsComment;	//评论人数
	
	int isPhone;	//是否手机专享

	int isFavor;	//是否已关注
	
	int num;	//购物车中数量

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsIcon() {
		return goodsIcon;
	}

	public void setGoodsIcon(String goodsIcon) {
		this.goodsIcon = goodsIcon;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(String goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public String getGoodsPercent() {
		return goodsPercent;
	}

	public void setGoodsPercent(String goodsPercent) {
		this.goodsPercent = goodsPercent;
	}

	public int getGoodsComment() {
		return goodsComment;
	}

	public void setGoodsComment(int goodsComment) {
		this.goodsComment = goodsComment;
	}

	public int getIsPhone() {
		return isPhone;
	}

	public void setIsPhone(int isPhone) {
		this.isPhone = isPhone;
	}

	public int getIsFavor() {
		return isFavor;
	}

	public void setIsFavor(int isFavor) {
		this.isFavor = isFavor;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

	
}
