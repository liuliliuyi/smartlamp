package com.lingyang.xml;

import java.util.ArrayList;

public class DefaultValue {
	private ArrayList<String> sm = new ArrayList<String>();
    private int a=0;
	public DefaultValue() {
		// TODO Auto-generated constructor stub
	}

	private void ChoiceDataPattern() {
		switch (a) {
		case 1:
		sm.clear();
		sm.add("13");
		sm.add("18");
		sm.add("0");
		sm.add("0");
		sm.add("0");
		sm.add("0");
		sm.add("0");
		sm.add("0");
		XmlSaveUtil.GetInstance().writeXML(3, sm, "mguangKong_shi_1.xml");
		sm.clear();
		sm.add("10");
		sm.add("10");
		sm.add("0");
		sm.add("0");
		sm.add("0");
		sm.add("0");
		sm.add("0");
		sm.add("0");
		XmlSaveUtil.GetInstance().writeXML(3, sm, "mguangKong_liang_1.xml");
		sm.clear();
		sm.add("0");
		sm.add("0");
		sm.add("0");
		sm.add("0");
		sm.add("0");
		sm.add("0");
		sm.add("0");
		sm.add("0");
		XmlSaveUtil.GetInstance().writeXML(3, sm, "mguangKong_on_1.xml");
		sm.clear();
		sm.add("10");
		sm.add("10");
		sm.add("10");
		sm.add("10");
		sm.add("10");
		sm.add("10");
		sm.add("10");
		sm.add("10");
		XmlSaveUtil.GetInstance().writeXML(3, sm, "mguangKong_xiu_1.xml");
		break;
		case 2:
		sm.clear();
		sm.add("12");
		sm.add("18");
		sm.add("0");
		sm.add("0");
		sm.add("0");
		sm.add("0");
		sm.add("0");
		sm.add("0");
		XmlSaveUtil.GetInstance().writeXML(3, sm, "mguangKong_shi_2.xml");
		sm.clear();
		sm.add("10");
		sm.add("10");
		sm.add("0");
		sm.add("0");
		sm.add("0");
		sm.add("0");
		sm.add("0");
		sm.add("0");
		XmlSaveUtil.GetInstance().writeXML(3, sm, "mguangKong_liang_2.xml");
		sm.clear();
		sm.add("0");
		sm.add("0");
		sm.add("0");
		sm.add("0");
		sm.add("0");
		sm.add("0");
		sm.add("0");
		sm.add("0");
		XmlSaveUtil.GetInstance().writeXML(3, sm, "mguangKong_on_2.xml");
		sm.clear();
		sm.add("10");
		sm.add("10");
		sm.add("10");
		sm.add("10");
		sm.add("10");
		sm.add("10");
		sm.add("10");
		sm.add("10");
		XmlSaveUtil.GetInstance().writeXML(3, sm, "mguangKong_xiu_2.xml");
		break;
		case 3:
		sm.clear();
		sm.add("10");
		sm.add("0");
		sm.add("2");
		XmlSaveUtil.GetInstance().writeXML(4, sm, "mgkong_3.xml");
		break;
		case 4:
		sm.clear();
		sm.add("10");
		sm.add("0");
		sm.add("2");
		XmlSaveUtil.GetInstance().writeXML(4, sm, "mrenGong_4.xml");
		break;
		default:
			break;
		}
	}

	private void ChoiceVoltagePower(int b) {
		switch (b) {
		case 1:
			sm.clear();
			sm.add("65");
			sm.add("0");
			XmlSaveUtil.GetInstance().writeXML(5, sm, "mgb_1.xml");
			sm.clear();
			sm.add("2");
			XmlSaveUtil.GetInstance().writeXML(6, sm, "mRA_1.xml");
			break;
		case 2:
			sm.clear();
			sm.add("65");
			sm.add("0");
			XmlSaveUtil.GetInstance().writeXML(5, sm, "mgb_2.xml");
			sm.clear();
			sm.add("2");
			XmlSaveUtil.GetInstance().writeXML(6, sm, "mRA_2.xml");
			break;
		case 3:
			sm.clear();
			sm.add("65");
			sm.add("0");
			XmlSaveUtil.GetInstance().writeXML(5, sm, "mgb_3.xml");
			sm.clear();
			sm.add("2");
			XmlSaveUtil.GetInstance().writeXML(6, sm, "mRA_3.xml");
			break;
		case 4:
			sm.clear();
			sm.add("65");
			sm.add("0");
			XmlSaveUtil.GetInstance().writeXML(5, sm, "mgb_4.xml");
			sm.clear();
			sm.add("2");
			XmlSaveUtil.GetInstance().writeXML(6, sm, "mRA_4.xml");
			break;

		default:
			break;
		}
 
	}
	private void Battery(){
		sm.clear();
		sm.add("1");
		sm.add("16.6");
		sm.add("15.6");
		sm.add("14.6");
		sm.add("13.6");
		sm.add("0");
		sm.add("0");
		sm.add("0");
		sm.add("12.0");
		sm.add("11.0");
		sm.add("-20");
		sm.add("60");
		sm.add("13.0");
		sm.add("0");
		XmlSaveUtil.GetInstance().writeXML(1, sm, "mTieli_1.xml");
		XmlSaveUtil.GetInstance().writeXML(1, sm, "mTieli_4.xml");
		sm.clear();
		sm.add("2");
		sm.add("14.6");
		sm.add("13.6");
		sm.add("12.6");
		sm.add("12.1");
		sm.add("0");
		sm.add("0");
		sm.add("0");
		sm.add("10.5");
		sm.add("9.5");
		sm.add("-20");
		sm.add("60");
		sm.add("11.5");
		sm.add("0");
		XmlSaveUtil.GetInstance().writeXML(1, sm, "mTieli_2.xml");
		XmlSaveUtil.GetInstance().writeXML(1, sm, "mTieli_5.xml");
		sm.clear();
		sm.add("3");
		sm.add("17.0");
		sm.add("14.8");
		sm.add("0");
		sm.add("13.2");
		sm.add("14.7");
		sm.add("14.4");
		sm.add("13.7");
		sm.add("12.6");
		sm.add("11.0");
		sm.add("-20");
		sm.add("60");
		sm.add("12.0");
		sm.add("0");
		XmlSaveUtil.GetInstance().writeXML(1, sm, "mTieli_3.xml");
		XmlSaveUtil.GetInstance().writeXML(1, sm, "mTieli_6.xml");
	}
	public void FZdl(){
		sm.clear();
		sm.add("0");
		XmlSaveUtil.GetInstance().writeXML(6, sm, "mm_1.xml"); 
	}
	public void BZ(){
		
		sm.clear();
		sm.add("1");
		sm.add("42");
		sm.add("0");
		XmlSaveUtil.GetInstance().writeXML(4, sm, "mSet.xml");
	}
	
	public void yjmss(){
		a=1;
		ChoiceDataPattern();
	}
	public void init(){
		a=1;
		ChoiceDataPattern();
		ChoiceVoltagePower(a);
		a=2;
		ChoiceDataPattern();
		ChoiceVoltagePower(a);
		a=3;
		ChoiceDataPattern();
		ChoiceVoltagePower(a);
		a=4;
		ChoiceDataPattern();
		ChoiceVoltagePower(a);
		
		Battery();
		FZdl();
		BZ();
		
	}

}
