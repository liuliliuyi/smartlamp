package com.lingyang.xml;

public class Data_Toast_Server {

	private static Data_Toast_Server mData_Toast_Server = null; 
	public int DATA_TOAST=0;
	public static Data_Toast_Server GetInstance() {  
        if (mData_Toast_Server == null) {  
        	mData_Toast_Server = new Data_Toast_Server();  
        }  
        return mData_Toast_Server;  
    }  
	
}
