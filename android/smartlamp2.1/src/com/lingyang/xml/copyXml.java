package com.lingyang.xml;

import java.util.ArrayList;

public class copyXml {

	private ArrayList<String> str = new ArrayList<String>();
	public copyXml() {
		// TODO Auto-generated constructor stub
	}
	public copyXml(int a) {
		// TODO Auto-generated constructor stub
		ms(a);
	 
	}
	public void gbData(int a){
		gb(a);
	}
	public void jgData(int a){
		jg(a);
	}
	public void msData(int a){
		ms(a);
	}
	public void BatteryData(){
		Battery();
	}
	public void xmData(){
		dormancy();
	}
	private void ms(int a){
		switch (a) {
		case 1:
			str.clear();
			str = XmlSaveUtil.GetInstance().readXML_data(3, "mguangKong_shi_1.xml");
			XmlSaveUtil.GetInstance().writeXML(3, str, "mguangKong_shi.xml");
			//亮度
			str.clear();
			str = XmlSaveUtil.GetInstance().readXML_data(3, "mguangKong_liang_1.xml");
			XmlSaveUtil.GetInstance().writeXML(3, str, "mguangKong_liang.xml");
			//休眠�?�?
			str.clear();
			str = XmlSaveUtil.GetInstance().readXML_data(3, "mguangKong_on_1.xml");
			XmlSaveUtil.GetInstance().writeXML(3, str, "mguangKong_on.xml");
			//休眠亮度
			str.clear();
			str = XmlSaveUtil.GetInstance().readXML_data(3, "mguangKong_xiu_1.xml");
			XmlSaveUtil.GetInstance().writeXML(3, str, "mguangKong_xiu.xml");
			break;
		case 2:
			//时间
			str.clear();
			str = XmlSaveUtil.GetInstance().readXML_data(3, "mguangKong_shi_2.xml");
			XmlSaveUtil.GetInstance().writeXML(3, str, "mguangKong_shi.xml");
			//亮度
			str.clear();
			str = XmlSaveUtil.GetInstance().readXML_data(3, "mguangKong_liang_2.xml");
			XmlSaveUtil.GetInstance().writeXML(3, str, "mguangKong_liang.xml");
			//休眠�?�?
			str.clear();
			str = XmlSaveUtil.GetInstance().readXML_data(3, "mguangKong_on_2.xml");
			XmlSaveUtil.GetInstance().writeXML(3, str, "mguangKong_on.xml");
			//休眠亮度
			str.clear();
			str = XmlSaveUtil.GetInstance().readXML_data(3, "mguangKong_xiu_2.xml");
			XmlSaveUtil.GetInstance().writeXML(3, str, "mguangKong_xiu.xml");
			break;
		case 3:
			//光控模式
		 
			str.clear();
			str = XmlSaveUtil.GetInstance().readXML_data(4, "mgkong_3.xml");
			XmlSaveUtil.GetInstance().writeXML(4, str, "mgkong.xml");
			 
			break;
		case 4:
			//人工模式
			 
			str.clear();
			str = XmlSaveUtil.GetInstance().readXML_data(4, "mrenGong_4.xml");
			XmlSaveUtil.GetInstance().writeXML(4, str, "mrenGong.xml");
			 
			break;
		

		default:
			break;
		}
		
	}
	private void gb(int a){
		switch (a) {
		case 1:
			str.clear();
			str = XmlSaveUtil.GetInstance().readXML_data(5, "mgb_1.xml");
			XmlSaveUtil.GetInstance().writeXML(5, str, "mgb.xml");
			 
			break;
		case 2:
			str.clear();
			str = XmlSaveUtil.GetInstance().readXML_data(5, "mgb_2.xml");
			XmlSaveUtil.GetInstance().writeXML(5, str, "mgb.xml");
			 
			break;
		case 3:
			str.clear();
			str = XmlSaveUtil.GetInstance().readXML_data(5, "mgb_3.xml");
			XmlSaveUtil.GetInstance().writeXML(5, str, "mgb.xml");
			 
			break;
		case 4:
			str.clear();
			str = XmlSaveUtil.GetInstance().readXML_data(5, "mgb_4.xml");
			XmlSaveUtil.GetInstance().writeXML(5, str, "mgb.xml");
			 
			break;

		default:
			break;
		}
	}
	private void jg(int a){
		switch (a) {
		case 1:
		 
			str.clear();
			str = XmlSaveUtil.GetInstance().readXML_data(8, "mRA_1.xml");
			XmlSaveUtil.GetInstance().writeXML(6, str, "mRA.xml");
			break;
		case 2:
			 
			str.clear();
			str = XmlSaveUtil.GetInstance().readXML_data(8, "mRA_2.xml");
			XmlSaveUtil.GetInstance().writeXML(6, str, "mRA.xml");
			break;
		case 3:
		 
			str.clear();
			str = XmlSaveUtil.GetInstance().readXML_data(8, "mRA_3.xml");
			XmlSaveUtil.GetInstance().writeXML(6, str, "mRA.xml");
			break;
		case 4:
		 
			str.clear();
			str = XmlSaveUtil.GetInstance().readXML_data(8, "mRA_4.xml");
			XmlSaveUtil.GetInstance().writeXML(6, str, "mRA.xml");
			break;

		default:
			break;
		}
	}
	private void Battery(){
		str.clear();
		str=XmlSaveUtil.GetInstance().readXML_data(1, "mTieli_1.xml");	
		XmlSaveUtil.GetInstance().writeXML(1, str, "mm.xml");
	}
	private void dormancy(){
		str.clear();
		str.add("10");
		XmlSaveUtil.GetInstance().writeXML(2, str, "mPn_1.xml");
		  
		XmlSaveUtil.GetInstance().writeXML(2, str, "mPn_2.xml");
		
		XmlSaveUtil.GetInstance().writeXML(2, str, "mPn_3.xml");
	}
	
}
