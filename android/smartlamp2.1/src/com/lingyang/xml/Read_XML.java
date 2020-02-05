package com.lingyang.xml;

import java.util.ArrayList;

public class Read_XML {
	
	public Read_XML() {
		// TODO Auto-generated constructor stub
	}
	
	public ArrayList<String> read_xml_all(int v){
		ArrayList<String> sr = new ArrayList<String>();
		sr.clear();
		switch (v) {
		case 1:
			sr = XmlSaveUtil.GetInstance().readXML_data(10, "break_MS_1.xml");
			break;
		case 2:
			sr = XmlSaveUtil.GetInstance().readXML_data(10, "break_MS_2.xml");
			break;
		case 3:
			sr = XmlSaveUtil.GetInstance().readXML_data(10, "break_MS_3.xml");
			break;

		default:
			break;
		}
		return sr;
	}
	public String read_xml_D_1(){
		ArrayList<String> sr = new ArrayList<String>();
		sr = read_xml_all(1);
		if (sr.size() != 0) {
			return sr.get(2);
		}else{
		    return "20";
		}
	}
	 
	public void write_xml_D_1(int a){
		ArrayList<String> s = new ArrayList<String>();
        s.clear();
		switch (a) {
		case 1:
			s.add("1");
			XmlSaveUtil.GetInstance().writeXML(2, s, "wPN_1.xml");
			break;
		case 2:
			s.add("2");
			XmlSaveUtil.GetInstance().writeXML(2, s, "wPN_1.xml");
			break;
		case 3:
			s.add("3");
			XmlSaveUtil.GetInstance().writeXML(2, s, "wPN_1.xml");
			break;
		case 4:
			s.add("4");
			XmlSaveUtil.GetInstance().writeXML(2, s, "wPN_1.xml");
			break;
		case 5:
			s.add("5");
			XmlSaveUtil.GetInstance().writeXML(2, s, "wPN_1.xml");
			break;
		case 6:
			s.add("6");
			XmlSaveUtil.GetInstance().writeXML(2, s, "wPN_1.xml");
			break;
		case 7:
			s.add("7");
			XmlSaveUtil.GetInstance().writeXML(2, s, "wPN_1.xml");
			break;
		case 8:
			s.add("8");
			XmlSaveUtil.GetInstance().writeXML(2, s, "wPN_1.xml");
			break;
			
		default:
			break;
		}
		 
	}
	public void write_xml_D_2(int a){
		ArrayList<String> s = new ArrayList<String>();
        s.clear();
		switch (a) {
		case 1:
			s.add("1");
			XmlSaveUtil.GetInstance().writeXML(2, s, "wPN_2.xml");
			break;
		case 2:
			s.add("2");
			XmlSaveUtil.GetInstance().writeXML(2, s, "wPN_2.xml");
			break;
		case 3:
			s.add("3");
			XmlSaveUtil.GetInstance().writeXML(2, s, "wPN_2.xml");
			break;
		case 4:
			s.add("4");
			XmlSaveUtil.GetInstance().writeXML(2, s, "wPN_2.xml");
			break;
		case 5:
			s.add("5");
			XmlSaveUtil.GetInstance().writeXML(2, s, "wPN_2.xml");
			break;
		case 6:
			s.add("6");
			XmlSaveUtil.GetInstance().writeXML(2, s, "wPN_2.xml");
			break;
		case 7:
			s.add("7");
			XmlSaveUtil.GetInstance().writeXML(2, s, "wPN_2.xml");
			break;
		case 8:
			s.add("8");
			XmlSaveUtil.GetInstance().writeXML(2, s, "wPN_2.xml");
			break;
			
		default:
			break;
		}
		 
	}
	public String read_xml_D_2(){
		ArrayList<String> sr = new ArrayList<String>();
		sr = read_xml_all(2);
		if (sr.size() != 0) {
			return sr.get(2);
			
		}else{
		
		return "20";
		
		}
	}
	public String read_xml_D_3(){
		
		return "20";
	}

}
