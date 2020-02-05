package com.lingyang.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
 

public class XmlSaveUtil {

	private static XmlSaveUtil mXmlSaveUtil = null;  
	     
	    private  String mstrConfigPath;  
	    private  String[]  mDianChi={"TYPE","OPPtion","LC","OV","RV","LV","DV","FCV","ODBV","ODV","LTR","HTR","RPVV","TLC"};
	    private  String[]  mPattern={"pattern"};  
	    private  String[]  mGK={"T1","T2","T3","T4","T5","T6","T7","T8"}; 
	    private  String[]  mGKong={"DJ","GY","XM"};
	    private  String[]  mGB ={"OCV","OCD"};
	    private  String[]  mRA ={"RPVV"};
	    private  String[]  lanya = {"initial"};
	    private  String[]  wMS={"MS_1","MS_2","MS_3","MS_4","MS_5","MS_6","MS_7","MS_8","MS_9","MS_10","MS_11","MS_12","MS_13","MS_14","MS_15","MS_16","MS_17","MS_18","MS_19","MS_20",};
	    public static XmlSaveUtil GetInstance() {  
	        if (mXmlSaveUtil == null) {  
	            mXmlSaveUtil = new XmlSaveUtil();  
	        }  
	        return mXmlSaveUtil;  
	    }  
	      
	    //设置保存位置  
	    public void setConfigPath(String strPath) {  
	        mstrConfigPath = strPath;  
	    }  
	      
	    //写如xml  
	    public boolean writeXML(int flag,ArrayList<String> strContent ,String strFileName)  
	    {  
	        File pathFile = new File(mstrConfigPath);  
	        if (mstrConfigPath.length() <= 0 || !pathFile.exists()) {  
	            return false;  
	        }  
	          
	        DocumentBuilderFactory dbfFactory = DocumentBuilderFactory.newInstance();  
	        DocumentBuilder dbBuilder = null;  
	        try {  
	            dbBuilder = dbfFactory.newDocumentBuilder();  
	        } catch (Exception e) {  
	            return false;  
	        }  
	          
	        Document doc = dbBuilder.newDocument();  
	          
	        //创建根元素�?�Custom�?  
	        org.w3c.dom.Element root = doc.createElement("Custom");  
	        doc.appendChild(root);  
	          
	        // 名称信息  
	        switch (flag) {
			case 1:
				for (int i = 0; i < mDianChi.length; i++) {
		        	org.w3c.dom.Element configNode = doc.createElement(mDianChi[i]);  
			        root.appendChild(configNode);  
			        Text configValue = doc.createTextNode(String.format("%s", strContent.get(i)));  
			        configNode.appendChild(configValue);
				}
	 
				break;
			case 2:
				for (int i = 0; i < mPattern.length; i++) {
		        	org.w3c.dom.Element configNode = doc.createElement(mPattern[i]);  
			        root.appendChild(configNode);  
			        Text configValue = doc.createTextNode(String.format("%s", strContent.get(i)));  
			        configNode.appendChild(configValue);
				}
				break;
			case 3:
				for (int i = 0; i < mGK.length; i++) {
					org.w3c.dom.Element configNode = doc.createElement(mGK[i]);  
			        root.appendChild(configNode);  
			        Text configValue = doc.createTextNode(String.format("%s", strContent.get(i)));  
			        configNode.appendChild(configValue);
				}
				break;
			case 4:
				for (int i = 0; i < mGKong.length; i++) {
					org.w3c.dom.Element configNode = doc.createElement(mGKong[i]);  
			        root.appendChild(configNode);  
			        Text configValue = doc.createTextNode(String.format("%s", strContent.get(i)));  
			        configNode.appendChild(configValue);
				}
				break;
			case 5:
				for (int i = 0; i < mGB.length; i++) {
					org.w3c.dom.Element configNode = doc.createElement(mGB[i]);  
			        root.appendChild(configNode);  
			        Text configValue = doc.createTextNode(String.format("%s", strContent.get(i)));  
			        configNode.appendChild(configValue);
				}
				break;
			case 6:
				for (int i = 0; i < mRA.length; i++) {
					org.w3c.dom.Element configNode = doc.createElement(mRA[i]);  
			        root.appendChild(configNode);  
			        Text configValue = doc.createTextNode(String.format("%s", strContent.get(i)));  
			        configNode.appendChild(configValue);
				}
				break;
			case 7:
				for (int i = 0; i < lanya.length; i++) {
					org.w3c.dom.Element configNode = doc.createElement(lanya[i]);  
			        root.appendChild(configNode);  
			        Text configValue = doc.createTextNode(String.format("%s", strContent.get(i)));  
			        configNode.appendChild(configValue);
				}
				break;
			case 10:
				for (int i = 0; i < 20; i++) {
					org.w3c.dom.Element configNode = doc.createElement(wMS[i]);  
			        root.appendChild(configNode);  
			        Text configValue = doc.createTextNode(String.format("%s", strContent.get(i)));  
			        configNode.appendChild(configValue);
				}
				break;
			default:
				break;
			}
	        
	        //将xml写成文件  
	        Transformer trans;  
	        try {  
	            trans = TransformerFactory.newInstance().newTransformer();  
	            trans.setOutputProperty("indent", "yes");  
	            trans.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(mstrConfigPath + "/" + strFileName)));  
	              
	            return true;  
	        } catch (TransformerConfigurationException e) {  
	            e.printStackTrace();  
	        } catch (TransformerFactoryConfigurationError e) {  
	            e.printStackTrace();  
	        } catch (FileNotFoundException e) {  
	            e.printStackTrace();  
	        } catch (TransformerException e) {  
	            e.printStackTrace();  
	        }  
	        return false;  
	    }  
	     
	  
	    //读取xml  
	    public ArrayList<String>   readXML_data(int flag,String strFileName)  
	    {  
	        File pathFile = new File(mstrConfigPath);  
	        ArrayList<String> tn = new ArrayList<String>();
	        
	        if (!pathFile.exists()) {  
	            return tn;  
	        }  
	          
	        String strName = "";  
	          
	        DocumentBuilderFactory dbfFactory = DocumentBuilderFactory.newInstance();  
	        DocumentBuilder dbBuilder = null;  
	        try {  
	            dbBuilder = dbfFactory.newDocumentBuilder();  
	        } catch (Exception e) {  
	            return tn;  
	        }  
	                  
	        File inFile = new File(mstrConfigPath, strFileName);  
	        Document doc = null;  
	        try {  
	            inFile.createNewFile();  
	              
	            doc = dbBuilder.parse(inFile);  
	        } catch (SAXException e) {  
	            e.printStackTrace();  
	            return tn;  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	            return tn;  
	        }  
	          
	        org.w3c.dom.Element root = doc.getDocumentElement();  
	          
	        //获取内容  
 
	        switch (flag) {
			case 1:
				for (int i = 0; i < mDianChi.length; i++) {
			         
					tn.add(root.getElementsByTagName(mDianChi[i]).item(0).getFirstChild().getNodeValue());
				}
				break;
			case 2:
				for (int i = 0; i < mPattern.length; i++) {
			         
					tn.add(root.getElementsByTagName(mPattern[i]).item(0).getFirstChild().getNodeValue());
				}
				break;
			case 3:
				for (int i = 0; i < mGK.length; i++) {
					tn.add(root.getElementsByTagName(mGK[i]).item(0).getFirstChild().getNodeValue());
				}
				 
				break;
			case 4:
				for (int i = 0; i < mGKong.length; i++) {
					tn.add(root.getElementsByTagName(mGKong[i]).item(0).getFirstChild().getNodeValue());
				}
				break;
			case 5:
				for (int i = 0; i < mGB.length; i++) {
					tn.add(root.getElementsByTagName(mGB[i]).item(0).getFirstChild().getNodeValue());
				}
				break;
			case 7:
				for (int i = 0; i < lanya.length; i++) {
					tn.add(root.getElementsByTagName(lanya[i]).item(0).getFirstChild().getNodeValue());
				}
				break;
			case 8:
				for (int i = 0; i < mRA.length; i++) {
					tn.add(root.getElementsByTagName(mRA[i]).item(0).getFirstChild().getNodeValue());
				}
				break;
			case 10:
				for (int i = 0; i < 20; i++) {
					tn.add(root.getElementsByTagName(wMS[i]).item(0).getFirstChild().getNodeValue());
				}
				break;
			default:
				break;
			}
 
	        return tn;  
	    }  

	    @SuppressWarnings("unused")
		public ArrayList<String>   readXML(String strFileName)  
	    {  
	        File pathFile = new File(mstrConfigPath);  
	        ArrayList<String> tn = new ArrayList<String>();
	        
	        if (!pathFile.exists()) {  
	            return tn;  
	        }  
	          
	        String strName = "";  
	          
	        DocumentBuilderFactory dbfFactory = DocumentBuilderFactory.newInstance();  
	        DocumentBuilder dbBuilder = null;  
	        try {  
	            dbBuilder = dbfFactory.newDocumentBuilder();  
	        } catch (Exception e) {  
	            return tn;  
	        }  
	                  
	        File inFile = new File(mstrConfigPath, strFileName);  
	        Document doc = null;  
	        try {  
	            inFile.createNewFile();  
	              
	            doc = dbBuilder.parse(inFile);  
	        } catch (SAXException e) {  
	            e.printStackTrace();  
	            return tn;  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	            return tn;  
	        }  
	          
	        org.w3c.dom.Element root = doc.getDocumentElement();  
	          
	        //获取内容  
	        NodeList t1 = root.getElementsByTagName("Name");  
	        NodeList t2 = root.getElementsByTagName("sex"); 
	        Text configValue = null;  
	        if (t1.getLength() == 1) {  
	        	tn.add(t1.item(0).getFirstChild().getNodeValue());
	        	tn.add(t2.item(0).getFirstChild().getNodeValue());

	        }  

	        return tn;  
	    }  
	    
	    @SuppressWarnings("unused")
		public ArrayList<String>   readXML_size(String strFileName)  
	    {  
	        File pathFile = new File(mstrConfigPath);  
	        ArrayList<String> tn = new ArrayList<String>();
	        
	        if (!pathFile.exists()) {  
	            return tn;  
	        }  
	          
	        String strName = "";  
	          
	        DocumentBuilderFactory dbfFactory = DocumentBuilderFactory.newInstance();  
	        DocumentBuilder dbBuilder = null;  
	        try {  
	            dbBuilder = dbfFactory.newDocumentBuilder();  
	        } catch (Exception e) {  
	            return tn;  
	        }  
	                  
	        File inFile = new File(mstrConfigPath, strFileName);  
	        Document doc = null;  
	        try {  
	            inFile.createNewFile();  
	              
	            doc = dbBuilder.parse(inFile);  
	        } catch (SAXException e) {  
	            e.printStackTrace();  
	            return tn;  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	            return tn;  
	        }  
	        

	        return tn;  
	    }  
}
