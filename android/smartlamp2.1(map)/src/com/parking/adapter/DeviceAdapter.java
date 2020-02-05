package com.parking.adapter;


import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;

import com.parking.smarthome.R;

public class DeviceAdapter extends BaseAdapter {

    private Context context;
    private List<BluetoothDevice> bleDeviceList;

    public DeviceAdapter(Context context) {
        this.context = context;
        bleDeviceList = new ArrayList<>();
    }


    @Override
    public int getCount() {
        return bleDeviceList.size();
    }

    @Override
    public BluetoothDevice getItem(int position) {
        if (position > bleDeviceList.size())
            return null;
        return bleDeviceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = View.inflate(context, R.layout.adapter_device, null);
            holder = new ViewHolder();
            convertView.setTag(holder);
            holder.img_blue = (ImageView) convertView.findViewById(R.id.img_blue);
            holder.txt_name = (TextView) convertView.findViewById(R.id.txt_name);
            holder.txt_mac = (TextView) convertView.findViewById(R.id.txt_mac);
            
           
        }

        final BluetoothDevice bleDevice = getItem(position);
        if (bleDevice != null) {
           
            String name = bleDevice.getName();
            holder.txt_name.setText(name);
           
          
//            if (isConnected) {
//                holder.img_blue.setImageResource(R.drawable.ic_blue_connected);
//                holder.txt_name.setTextColor(0xFF1DE9B6);
//                holder.txt_mac.setTextColor(0xFF1DE9B6);
//              
//                
//            } else {
//                holder.img_blue.setImageResource(R.drawable.ic_blue_remote);
//                holder.txt_name.setTextColor(0xFF000000);
//                holder.txt_mac.setTextColor(0xFF000000);
//               
//                
//            }
        }

        return convertView;
    }

    class ViewHolder {
        ImageView img_blue;
        TextView txt_name;
        TextView txt_mac;
        
        
    }



}
