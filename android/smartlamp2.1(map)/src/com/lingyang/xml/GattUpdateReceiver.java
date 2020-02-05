package com.lingyang.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
 

import com.langyue.ble.SampleGattAttributes;
import com.parking.smarthome.R;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

public class GattUpdateReceiver extends BroadcastReceiver {

	private main_ly LY_flag  ;
	private int count=0;
	
	private MyApplication app;
	private Context context;
	public ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
	private final String LIST_NAME = "NAME";
	private final String LIST_UUID = "UUID";
	private BluetoothGattCharacteristic mNotifyCharacteristic;
	private boolean flag=true;
	private Handler con_Handler = new Handler();
 
	@Override
	public void onReceive(Context context, final Intent intent) {
		// TODO Auto-generated method stub
		try {
			
	         
		final String action = intent.getAction();
		app = (MyApplication) context.getApplicationContext();
//		Toast.makeText(context, "@@@@@@@@@"+action, Toast.LENGTH_LONG).show();
		this.context = context;
		if (app.getBluetoothLeService().ACTION_GATT_CONNECTED.equals(action)) {
			 LY_flag.setBoolean(true);
		} else if (app.getBluetoothLeService().ACTION_GATT_DISCONNECTED.equals(action)) {
		    LY_flag.setBoolean(false);
		} else if (app.getBluetoothLeService().ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
		 
			displayGattServices(app.getBluetoothLeService().getSupportedGattServices()); 
//			SH();
 
		} else if (app.getBluetoothLeService().ACTION_DATA_AVAILABLE.equals(action)) {
		   LY_flag.setText(new String(intent.getStringExtra(app.getBluetoothLeService().EXTRA_DATA)) ); 
		}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	 Runnable con_Runnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
//			displayGattServices(app.getBluetoothLeService().getSupportedGattServices()); 
//			SH();
			ArrayList<String> ly = new ArrayList<String>();
		
			ly = XmlSaveUtil.GetInstance().readXML_data(5, "lanya_1.xml");
			if (ly.size() != 0) {
				app.getBluetoothLeService().connect(ly.get(1).toString().trim());
			}
		}
	};
	
	 public interface main_ly {        
    	   
    	 public void setBoolean(boolean  flag);
    	 public void setText(String content); 
    	 public void setListBluetoothGattCharacteristic(ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics);
 
    	 }  
	 
     public void setBRInteractionListener(main_ly LY_flag) {       
    	 this.LY_flag = LY_flag;    
    	 
     }
     
     @SuppressLint("NewApi") 
     private void displayGattServices(List<BluetoothGattService> gattServices) {
 		if (gattServices == null)
 			return;
 		String uuid = null;
 		String unknownCharaString = context.getResources().getString(
 				R.string.unknown_characteristic);
 		ArrayList<HashMap<String, String>> gattServiceData = new ArrayList<HashMap<String, String>>();
 		ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData = new ArrayList<ArrayList<HashMap<String, String>>>();
 		mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
 		int dd = 0;
 		// Loops through available GATT Services.
 		for (BluetoothGattService gattService : gattServices) {
 			dd++;
 			if (dd == 3) {
 				dd = 0;
 				HashMap<String, String> currentServiceData = new HashMap<String, String>();
 				uuid = gattService.getUuid().toString();
 				gattServiceData.add(currentServiceData);
 				ArrayList<HashMap<String, String>> gattCharacteristicGroupData = new ArrayList<HashMap<String, String>>();
 				List<BluetoothGattCharacteristic> gattCharacteristics = gattService
 						.getCharacteristics();
 				ArrayList<BluetoothGattCharacteristic> charas = new ArrayList<BluetoothGattCharacteristic>();

 				for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
 					charas.add(gattCharacteristic);
 					HashMap<String, String> currentCharaData = new HashMap<String, String>();
 					uuid = gattCharacteristic.getUuid().toString();
 					currentCharaData.put(LIST_NAME, SampleGattAttributes
 							.lookup(uuid, unknownCharaString));
 					currentCharaData.put(LIST_UUID, uuid);
 					gattCharacteristicGroupData.add(currentCharaData);
 				}
 				mGattCharacteristics.add(charas);
 				gattCharacteristicData.add(gattCharacteristicGroupData);
 			}
 		}
 		LY_flag.setListBluetoothGattCharacteristic(mGattCharacteristics);
 	}
 
 	@SuppressLint("NewApi")
 	public void SH() {
 	 
 		if (mGattCharacteristics != null) {
 			final BluetoothGattCharacteristic characteristic = mGattCharacteristics
 					.get(0).get(0);
 			final int charaProp = characteristic.getProperties();
 			if ((charaProp | BluetoothGattCharacteristic.PERMISSION_WRITE) > 0) {
 				mNotifyCharacteristic = characteristic;
 				app.getBluetoothLeService().setCharacteristicNotification(characteristic, true);
 				app.getBluetoothLeService().wirteCharacteristic(characteristic);
 				app.getBluetoothLeService().readCharacteristic(characteristic);
 			}
 		}
 	}
 	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 	 
}
