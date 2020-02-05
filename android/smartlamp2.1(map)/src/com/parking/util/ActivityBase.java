package com.parking.util;

import com.parking.smarthome.R;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityBase extends Activity {
   private static ActivityBase instance;
   /** Notification���� */
	public NotificationManager mNotificationManager;

   @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		instance=this;
		initService();
   }
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}
	
	public void hideSoftInputView() {
		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);  
		   imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);  	
	}
	
	/** ��ʾ�Զ���Toast��ʾ(����String) **/
	public static  void showCustomToast(String text) {
		
		View toastRoot = LayoutInflater.from(instance).inflate(R.layout.common_toast, null);
	
		((TextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
		Toast toast = new Toast(instance);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(toastRoot);
		toast.show();
	
	}	


	/**
	 * ��ʼ��Ҫ�õ���ϵͳ����
	 */
	private void initService() {
		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	}
	
	/** 
	 * �����ǰ������֪ͨ�� 
	 */
	public void clearNotify(int notifyId){
		mNotificationManager.cancel(notifyId);//ɾ��һ���ض���֪ͨID��Ӧ��֪ͨ
//		mNotification.cancel(getResources().getString(R.string.app_name));
	}
	
	/**
	 * �������֪ͨ��
	 * */
	public void clearAllNotify() {
		mNotificationManager.cancelAll();// ɾ���㷢������֪ͨ
	}
	
	/**
	 * @��ȡĬ�ϵ�pendingIntent,Ϊ�˷�ֹ2.3�����°汾����
	 * @flags����:  
	 * �ڶ�����פ:Notification.FLAG_ONGOING_EVENT  
	 * ���ȥ���� Notification.FLAG_AUTO_CANCEL 
	 */
	public PendingIntent getDefalutIntent(int flags){
		PendingIntent pendingIntent= PendingIntent.getActivity(this, 1, new Intent(), flags);
		return pendingIntent;
	}


}