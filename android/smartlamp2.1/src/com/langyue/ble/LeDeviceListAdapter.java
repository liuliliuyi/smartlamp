package com.langyue.ble;

import java.util.ArrayList;

import com.parking.smarthome.R;


 
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LeDeviceListAdapter extends BaseAdapter {
	
	private ArrayList<BluetoothDevice> mLeDevices;
	private LayoutInflater inflater;
	public View view ;
	
	public LeDeviceListAdapter(Context context,Activity activity) {
		super();
		mLeDevices = new ArrayList<BluetoothDevice>();
		inflater = activity.getLayoutInflater();
	}
	public void addDevice(BluetoothDevice device) {
		if (!mLeDevices.contains(device)) {
			if (device.getName() != null) {
				if (device.getName().substring(0, 3).toString().trim().equals("BLE")) {
					mLeDevices.add(device);
				}
			}
		}
	}
	public BluetoothDevice getDevice(int position) {
		return mLeDevices.get(position);
	}

	public void clear() {
		mLeDevices.clear();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mLeDevices.size();
	}

	@Override
	public Object getItem(int i) {
		// TODO Auto-generated method stub
		return mLeDevices.get(i);
	}

	@Override
	public long getItemId(int i) {
		// TODO Auto-generated method stub
		return i;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		// General ListView optimization code.

		if (view == null) {
			view = inflater.inflate(R.layout.listitem_device, null);
			viewHolder = new ViewHolder();
			viewHolder.deviceAddress = (TextView) view.findViewById(R.id.device_address);
			viewHolder.deviceName = (TextView) view.findViewById(R.id.device_name);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		BluetoothDevice device = mLeDevices.get(i);

		final String deviceName = device.getName();
		if (deviceName != null && deviceName.length() > 0)
			viewHolder.deviceName.setText(deviceName);

		else
			viewHolder.deviceName.setText(R.string.unknown_device);

		    viewHolder.deviceAddress.setText(device.getAddress());
		return view;
	}
 
static class ViewHolder {
	TextView deviceName;
	TextView deviceAddress;

}
}
