package com.lingyang.xml;

import java.util.ArrayList;
import java.util.Random;

import android.R.xml;
import android.content.Context;
import android.widget.Toast;

public class sendServer {
	
	private ArrayList<String> a1 = new ArrayList<String>();
	private ArrayList<String> a2 = new ArrayList<String>();
	private ArrayList<String> a3 = new ArrayList<String>();
	private ArrayList<String> a4 = new ArrayList<String>();
	private ArrayList<String> a5 = new ArrayList<String>();
	private ArrayList<String> a6 = new ArrayList<String>();
	private ArrayList<String> a7 = new ArrayList<String>();
	private ArrayList<String> a8 = new ArrayList<String>();
	private ArrayList<String> a9 = new ArrayList<String>();
	private ArrayList<String> a10 = new ArrayList<String>();
	private Context context;
	private String name;
	private int flag = 0;
	private int tag = 0;
	public int ND=0;
	 

	public sendServer(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		 
		ND = randomData();
	}

	public String sendData() {
        a1.clear();
		a2.clear();
        a1 = XmlSaveUtil.GetInstance().readXML_data(1, "mm.xml");
		a2 = XmlSaveUtil.GetInstance().readXML_data(8, "mm_1.xml");
		
		String n = "";
		String s = "";
		s = String.valueOf(zheng(1));
		n = n + s;
		s = String.valueOf(zheng(ND));
		n = n + s;
 
		if (a1.size() != 0) {
		 
			for (int i = 0; i < a1.size(); i++) {
				s = null; 
				
				if (i>=1 && i < 10) {
					s = zheng((int) (Float.parseFloat(a1.get(i)) * 10));
				} else {
					if (i==0) {
						s=zheng(Integer.valueOf(a1.get(i)));
					}
					if (i==10) {
						s=zheng(Integer.valueOf(a1.get(i)));
					}
					if (i==11) {
						s=zheng(Integer.valueOf(a1.get(i)));
					}
					if(i== 12){
						s = zheng((int) (Float.parseFloat(a1.get(i))*10));
					}
					if (i==13) {
					 
					    s = zheng((int) (Float.parseFloat(a2.get(0))));
					}
				}
				
				n = n + s;
			}
			 
			for (int i = 0; i < 20 - a1.size() - 2; i++) {
				s = String.valueOf(zheng(0));
				n = n + s;
			}
			return n;
		} else {
			for (int i = 0; i < 20 - 2; i++) {
				s = String.valueOf(zheng(0));
				n = n + s;
			}
			if (Data_Toast_Server.GetInstance().DATA_TOAST == 1) {
				Toast.makeText(context, "电池类型缺少,请退出程序!", Toast.LENGTH_SHORT).show();
			}
			return n;
		}

	}
 
	public String sendWord() {
		a1.clear();
		a2.clear();
		a3.clear();
		a4.clear();
		a5.clear();
		a1 = XmlSaveUtil.GetInstance().readXML_data(2, "mPattern.xml");
		a2 = XmlSaveUtil.GetInstance().readXML_data(3, "mguangKong_shi.xml");
		a3 = XmlSaveUtil.GetInstance().readXML_data(3, "mguangKong_liang.xml");
		a4 = XmlSaveUtil.GetInstance().readXML_data(3, "mguangKong_on.xml");
		a5 = XmlSaveUtil.GetInstance().readXML_data(8, "mRA.xml");
		String n = "";
		String s = "";
		if (Data_Toast_Server.GetInstance().DATA_TOAST == 1) {
			  if (a1.size() == 0) {
					Toast.makeText(context, "第二组,第1号", Toast.LENGTH_LONG).show();
				}
			    if (a2.size() == 0) {
					Toast.makeText(context, "第二组,第2号", Toast.LENGTH_LONG).show();
				}
			    if (a3.size() == 0) {
					Toast.makeText(context, "第二组,第3号", Toast.LENGTH_LONG).show();
				}
			    if (a4.size() == 0) {
					Toast.makeText(context, "第二组,第4号", Toast.LENGTH_LONG).show();
				}
		     
		}
		s = String.valueOf(zheng(2));
		n = n + s;
		s = String.valueOf(zheng(ND));
		n = n + s;
		n = n +String.valueOf(a5.get(0).toString().trim()+a1.get(0).toString().trim());
		if (a1.size() != 0 && a2.size() != 0 && a3.size() !=0 && a4.size() != 0) {
			 
				for (int i = 0; i < a2.size(); i++) {
					s = zheng((int) (Float.parseFloat(a2.get(i))));
					n=n+s;
					s = zheng(((int) (Float.parseFloat(a3.get(i))))+(((int) (Float.parseFloat(a4.get(i))))*128));
					n = n+s;

				}
				 for (int i = 0; i < 20-(a2.size()*2)-3; i++) {
					 s = String.valueOf(zheng(0));
						n = n + s;
				}
				 
			 return n;
		} else {
			for (int i = 0; i < 20 - 3; i++) {
				s = String.valueOf(zheng(0));
				n = n + s;
			}
//			Toast.makeText(context, "请选择工作模式", Toast.LENGTH_SHORT).show();
			return n;
		}
	}
 
	public String sendGR(int flag){
		int c=0;
		a1.clear();
		a2.clear();
		a3.clear();
		a4.clear();
		a5.clear();
		a6.clear();
		a7.clear();
		a8.clear();
		a9.clear();
		a10.clear();
		a1 = XmlSaveUtil.GetInstance().readXML_data(2, "mPattern.xml");
		a2 = XmlSaveUtil.GetInstance().readXML_data(3, "mguangKong_xiu.xml");
		a3 = XmlSaveUtil.GetInstance().readXML_data(4, "mgkong.xml");
		a4 = XmlSaveUtil.GetInstance().readXML_data(4, "mrenGong.xml");
		a5 = XmlSaveUtil.GetInstance().readXML_data(5, "mgb.xml");
		a6 = XmlSaveUtil.GetInstance().readXML_data(2, "mPn_1.xml");
		a7 = XmlSaveUtil.GetInstance().readXML_data(2, "mPn_2.xml");
		a8 = XmlSaveUtil.GetInstance().readXML_data(2, "mPn_3.xml");
		a9 = XmlSaveUtil.GetInstance().readXML_data(8, "mRA.xml");
		a10 = XmlSaveUtil.GetInstance().readXML_data(10, "ws_id.xml");
		String n = "";
		String s = "";
		if (Data_Toast_Server.GetInstance().DATA_TOAST == 1) {
			if (a1.size() == 0) {
				Toast.makeText(context, "第三组,第1号", Toast.LENGTH_LONG).show();
			}
		    if (a2.size() == 0) {
				Toast.makeText(context, "第三组,第2号", Toast.LENGTH_LONG).show();
			}
		    if (a3.size() == 0) {
				Toast.makeText(context, "第三组,第3号", Toast.LENGTH_LONG).show();
			}
		    if (a4.size() == 0) {
				Toast.makeText(context, "第三组,第4号", Toast.LENGTH_LONG).show();
			}
		    if (a5.size() == 0) {
				Toast.makeText(context, "第三组,第5号", Toast.LENGTH_LONG).show();
			}
		    if (a6.size() == 0) {
				Toast.makeText(context, "第三组,第6号", Toast.LENGTH_LONG).show();
			}
		    if (a7.size() == 0) {
				Toast.makeText(context, "第三组,第7号", Toast.LENGTH_LONG).show();
			}
		    if (a8.size() == 0) {
				Toast.makeText(context, "第三组,第8号", Toast.LENGTH_LONG).show();
			}
		    if (a9.size() == 0) {
				Toast.makeText(context, "第三组,第9号", Toast.LENGTH_LONG).show();
			}
		    if (a10.size() == 0) {
				Toast.makeText(context, "第三组,第10号", Toast.LENGTH_LONG).show();
			}
		}
			s = String.valueOf(zheng(3));
			n = n + s;
			s = String.valueOf(zheng(ND));
			n = n + s;
			if (a1.size() != 0 && a2.size() != 0 && a3.size() != 0 && a4.size() != 0 && a5.size() != 0 && a6.size() != 0 && a7.size() != 0 && a8.size() != 0 && a9.size() != 0) {
//			n = n + String.valueOf(zheng((Integer.parseInt(a9.get(0))*16)+Integer.parseInt(a1.get(0))));
			n=n+zheng(((int) (Float.parseFloat(a5.get(1)))));
			n=n+zheng(((int) (Float.parseFloat(a5.get(0)))));
		    n=n+zheng(((int) (Float.parseFloat(a3.get(0))))+(((int)(Float.parseFloat(a3.get(1))))*128));
//			 n=n+"11";
			n=n+zheng(((int) (Float.parseFloat(a4.get(0))))+(((int)(Float.parseFloat(a4.get(1))))*128));
			
			c=6;  
			switch (Integer.parseInt(a1.get(0))) {
			case 0:
				c++;
				n=n+zheng(((int) (Float.parseFloat(a6.get(0)))));
				break;
			case 1:
				 
				c++;
				n=n+zheng(((int) (Float.parseFloat(a6.get(0)))));
				break;
			case 2:
				 
				c++;
				n=n+zheng(((int) (Float.parseFloat(a7.get(0)))));
				break;
			case 3:
				 
				c++;
				n=n+zheng(((int) (Float.parseFloat(a4.get(2)))));
				break;
			default:
				break;
			}
			if (flag == 1) {
				n=n+zheng(101);
			}else{
				if (flag == 0) {
					n=n+zheng(Integer.parseInt(a10.get(8), 16));
				}
			}
			
			c++;
			for (int i = 0; i < 20-c; i++) {
				 
				s = String.valueOf(zheng(0));
				n = n + s;	
			 
			}
			return n;
		}else{
			for (int i = 0; i < 20-2; i++) {
				 s = String.valueOf(zheng(0));
					n = n + s;
			}
//			Toast.makeText(context, "请选择工作模式", Toast.LENGTH_SHORT).show();
			 return n;
		}
	}
	
	public String  sendReadTime(){
		String n = "";
		String s = "";
		
		s = String.valueOf(zheng(11));
		n = n + s;
		s = String.valueOf(zheng(ND));
		n = n + s;
		for (int i = 0; i < 20-2; i++) {
			 s = String.valueOf(zheng(0));
				n = n + s;
		}
		return n;
	}
	public String sendRead(){
		String n = "";
		String s = "";
		s = String.valueOf(zheng(10));
		n = n + s;
		s = String.valueOf(zheng(ND));
		n = n + s;
		for (int i = 0; i < 20-2; i++) {
			 s = String.valueOf(zheng(0));
				n = n + s;
		}
		return n;
	}
	public String sendBreakData_1(int a){
		String n = "";
		String s = "";
		s = String.valueOf(zheng(a));
		n = n + s;
		s = String.valueOf(zheng(ND));
		n = n + s;
		for (int i = 0; i < 20-2; i++) {
			 s = String.valueOf(zheng(0));
				n = n + s;
		}
		return n;
	}
    public String sendBreakData_2(){
    	String n = "";
		String s = "";
		s = String.valueOf(zheng(2));
		n = n + s;
		s = String.valueOf(zheng(ND));
		n = n + s;
		for (int i = 0; i < 20-2; i++) {
			 s = String.valueOf(zheng(0));
				n = n + s;
		}
		return n;
	}
    public String sendBreakData_3(){
    	String n = "";
		String s = "";
		s = String.valueOf(zheng(3));
		n = n + s;
		s = String.valueOf(zheng(ND));
		n = n + s;
		for (int i = 0; i < 20-2; i++) {
			 s = String.valueOf(zheng(0));
				n = n + s;
		}
		return n;
	}
 
 
	 
	public String sendXunDetails(int a,int b){
		String s = "";
		String n = "";
		s = String.valueOf(zheng(a));
		n = n + s;
		s = String.valueOf(zheng(ND));
		n = n + s;
		s = String.valueOf(zheng(b));
		n = n + s;
		 
		for (int i = 0; i < 20-3; i++) {
			 s = String.valueOf(zheng(0));
				n = n + s;
		}
		return n;
	}
	
	public String sendXunDetails_L(int a,int b){
		String s = "";
		String n = "";
		s = String.valueOf(zheng(a));
		n = n + s;
		s = String.valueOf(zheng(ND));
		n = n + s;
		s = String.valueOf(zheng(b));
		n = n + s;
		n = n + String.valueOf(zheng(1));
		for (int i = 0; i < 20-4; i++) {
			 s = String.valueOf(zheng(0));
				n = n + s;
		}
		return n;
	}
	public String sendXunDetails_L_1(int a,int b ,int c){
		String s = "";
		String n = "";
		s = String.valueOf(zheng(a));
		n = n + s;
		s = String.valueOf(zheng(12));
		n = n + s;
		s = String.valueOf(zheng(b));
		n = n + s;
		n = n + String.valueOf(zheng(1));
		 
		for (int i = 0; i < 20-4; i++) {
			 s = String.valueOf(zheng(0));
				n = n + s;
		}
		return n;
	}
	public String sendXunDetails_D(int a,int b){
		String s = "";
		String n = "";
		s = String.valueOf(zheng(a));
		n = n + s;
		s = String.valueOf(zheng(ND));
		n = n + s;
		s = String.valueOf(zheng(b));
		n = n + s;
		n = n + String.valueOf(zheng(2));
		for (int i = 0; i < 20-4; i++) {
			 s = String.valueOf(zheng(0));
				n = n + s;
		}
		return n;
	}
	public String sendXunDetails(int a,int b,int c){
		String s = "";
		String n = "";
		s = String.valueOf(zheng(a));
		n = n + s;
		s = String.valueOf(zheng(ND));
		n = n + s;
		s = String.valueOf(zheng(b));
		n = n + s;
		s = String.valueOf(zheng(c));
		n = n + s;
		for (int i = 0; i < 20-4; i++) {
			 s = String.valueOf(zheng(0));
				n = n + s;
		}
		return n;
	}
	public String sendHuanXing_l(int a){
		String s = "";
		String n = "";
		s = String.valueOf(zheng(a));
		n = n + s;
		s = String.valueOf(zheng(ND));
		n = n + s;
		s= String.valueOf(zheng(0));
	    n = n + s;
	    s= String.valueOf(zheng(1));
	    n = n + s;
		for (int i = 0; i < 20-4; i++) {
			 s = String.valueOf(zheng(0));
				n = n + s;
		}
		return n;
	}
	
	public String sendHuanXing_d(int a){
		String s = "";
		String n = "";
		s = String.valueOf(zheng(a));
		n = n + s;
		s = String.valueOf(zheng(ND));
		n = n + s;
	    s= String.valueOf(zheng(0));
	    n = n + s;
	    s= String.valueOf(zheng(2));
	    n = n + s;
		for (int i = 0; i < 20-4; i++) {
			 s = String.valueOf(zheng(0));
				n = n + s;
		}
		return n;
	}
	
	
	public String sendXiuMian_l(int a){
		String s = "";
		String n = "";
		s = String.valueOf(zheng(a));
		n = n + s;
		s = String.valueOf(zheng(ND));
		n = n + s;
		s= String.valueOf(zheng(4));
	    n = n + s;
	    s= String.valueOf(zheng(1));
	    n = n + s;
		for (int i = 0; i < 20-4; i++) {
			 s = String.valueOf(zheng(0));
				n = n + s;
		}
		return n;
	}
	
	public String sendXiuMian_d(int a){
		String s = "";
		String n = "";
		s = String.valueOf(zheng(a));
		n = n + s;
		s = String.valueOf(zheng(ND));
		n = n + s;
	    s= String.valueOf(zheng(4));
	    n = n + s;
	    s= String.valueOf(zheng(2));
	    n = n + s;
		for (int i = 0; i < 20-4; i++) {
			 s = String.valueOf(zheng(0));
				n = n + s;
		}
		return n;
	}
	
	
	public String sendHuiFu(int a){
		
		String s = "";
		String n = "";
		s = String.valueOf(zheng(a));
		n = n + s;
		s = String.valueOf(zheng(ND));
		n = n + s;
	 
		for (int i = 0; i < 20-2; i++) {
			 s = String.valueOf(zheng(0));
				n = n + s;
		}
		return n;
	}
	public String sendNum(int a,int b,int c){
		String s = "";
		String n = "";
		s = String.valueOf(zheng(a));
		n = n + s;
		s = String.valueOf(zheng(ND));
		n = n + s;
		s = String.valueOf(zheng(b));
		n = n + s;
		s = String.valueOf(zheng(c));
		n = n + s;
		for (int i = 0; i < 20-4; i++) {
			 s = String.valueOf(zheng(0));
				n = n + s;
		}
	
		return n;
	}
	public String sendWS(int a){
		String s = "";
		String n = "";
		s = String.valueOf(zheng(a));
		n = n + s;
		s = String.valueOf(zheng(ND));
		n = n + s;
		for (int i = 0; i < 20-2; i++) {
			 s = String.valueOf(zheng(0));
				n = n + s;
		}
		return n;
	}
	
	public String sendName(int a,int b,String sn){
		String s = "";
		String n = "";
		s = String.valueOf(zheng(a));
		n = n + s;
		s = String.valueOf(zheng(ND));
		n = n + s;
		s = String.valueOf(zheng(b));
		n = n + s;
		n = n + sn;
		for (int i = 0; i < 20-2-1-6; i++) {
			 s = String.valueOf(zheng(0));
				n = n + s;
		}
		return n;
	
	}
	
	public String sendXinTiao(int a){
		String s = "";
		String n = "";
		String xn = "";
		String xm = "";
		int in=0;
		int im=0;
		a1.clear();
		a2.clear();
		a3.clear();
		a1=XmlSaveUtil.GetInstance().readXML_data(10, "m_name.xml");
		a2=XmlSaveUtil.GetInstance().readXML_data(10, "m_mm.xml");
		a3=XmlSaveUtil.GetInstance().readXML_data(10, "ws_id.xml");
		s = String.valueOf(zheng(a));
		n = n + s;
		s = String.valueOf(zheng(ND));
		n = n + s;
		
		for (int i = 3; i < 9; i++) {
			if (Integer.valueOf(a1.get(i).toString().trim(), 16) !=0) {
				s = String.valueOf(zheng(Integer.valueOf(a1.get(i).toString().trim(), 16)));
				xn = xn + s;
				in=in+1;
			}else{
				s = String.valueOf(zheng(0));
				xn = xn + s;	
			}
			
		}
		n = n + String.valueOf(zheng(in));
		n = n + xn;
        for (int i = 3; i < 9; i++) {
        	if (Integer.valueOf(a2.get(i).toString().trim(), 16) != 0) {
        		s = String.valueOf(zheng(Integer.valueOf(a2.get(i).toString().trim(), 16)));
				xm = xm + s;
				im=im+1;
			}else{
				s = String.valueOf(zheng(0));
				xm = xm + s;
			}
        	
		}
        n = n + String.valueOf(zheng(im));
		n = n+xm;
		n = n+a3.get(8).toString();
		for (int i = 0; i < 20-16; i++) {
			 s = String.valueOf(zheng(0));
				n = n + s;
		}
	  
		return n;
	}
	public String sendXinTiao_1(int a){
		String s = "";
		String n = "";
		s = String.valueOf(zheng(a));
		n = n + s;
		s = String.valueOf(zheng(ND));
		n = n + s;
		
		for (int i = 2; i < 20; i++) {
			 n=n+"00";
			
		}
		return n;
	}


	private String zheng(int a) {
		if (a >= 16) {
			return String.valueOf(Integer.toHexString(a));
		} else if (a >= 0) {
			return "0" + String.valueOf(Integer.toHexString(a));
		 
		} else if (a < 0) {
			return String.valueOf(Integer.toHexString((-1)*a + 128));
		}
		return "0" + String.valueOf(Integer.toHexString(0));

	}

	public int randomData() {
		int min = 1;
		int max = 100;
		Random random = new Random();
		int num = random.nextInt(max) % (max - min + 1) + min;
		return num;
	}
}
