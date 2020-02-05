package com.example.publics;

//import com.lichfaker.log.Logger;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.greenrobot.eventbus.EventBus;
import com.example.publics.MqttManager;

import android.bluetooth.BluetoothGattCharacteristic;
import android.widget.Toast;
import static com.example.publics.Contants.Connect;
import static com.example.publics.Contants.ConnextFailed;
import static com.example.publics.Contants.DisConnect;
import static com.example.publics.Contants.PublicFailed;
import static com.example.publics.Contants.PublicSuccess;
import static com.example.publics.Contants.ReceData;
import static com.example.publics.Contants.TOPIC_Clent_Send;
 

/**
 * 使用EventBus分发事件
 *
 * @author LichFaker on 16/3/25.
 * @Email lichfaker@gmail.com
 */
public class MqttCallbackBus implements MqttCallback {
 
 
    @Override
    public void connectionLost(Throwable cause) {
//        Logger.e(cause.getMessage());
        EventBus.getDefault().post(new MyMsg(cause, DisConnect));
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
//        Logger.d(topic + "====" + message.toString());
     
    	EventBus.getDefault().post(new MyMsg(message, ReceData));
    	String ss = new String(message.getPayload());
    	
        
//    	MqttManager.getInstance().publish(TOPIC_Clent_Send, 2,("ss" + "����7" ).getBytes());
    }
   
    public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}
	
	private static byte charToByte(char c) {

		return (byte) "0123456789ABCDEF".indexOf(c);

	}
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        if (token.isComplete()) {
            EventBus.getDefault().post(new MyMsg(token, PublicSuccess));
        } else {
            EventBus.getDefault().post(new MyMsg(token, PublicFailed));

        }

    }


}
