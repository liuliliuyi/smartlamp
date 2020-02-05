package com.parking.lib;

import java.util.ArrayList;

import com.amap.api.maps.Projection;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.MarkerOptions;
import com.parking.smarthome.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MarkerClusterYellow {
    private Activity activity;
    private MarkerOptions options;
    private ArrayList<MarkerOptions> includeMarkers;
    private LatLngBounds bounds;// 创建区域
 
/**
*
* @param activity
* @param firstMarkers
* @param projection
* @param gridSize
* 区域大小参数
*/
   public MarkerClusterYellow(Activity activity, MarkerOptions firstMarkers,
         Projection projection, int gridSize) {
         options = new MarkerOptions();
         this.activity = activity;
         Point point = projection.toScreenLocation(firstMarkers.getPosition());
         Point southwestPoint = new Point(point.x - gridSize, point.y + gridSize);
         Point northeastPoint = new Point(point.x + gridSize, point.y - gridSize);
         bounds = new LatLngBounds(
         projection.fromScreenLocation(southwestPoint),
         projection.fromScreenLocation(northeastPoint));
         options.anchor(0.5f, 0.5f).title(firstMarkers.getTitle())
         .position(firstMarkers.getPosition())
         .icon(firstMarkers.getIcon())
         .snippet(firstMarkers.getSnippet());
         includeMarkers = new ArrayList<MarkerOptions>();
         includeMarkers.add(firstMarkers);
     }
 
/**
* 添加marker
*/
  public void addMarker(MarkerOptions markerOptions) {
      includeMarkers.add(markerOptions);// 添加到列表中
  }
 
/**
* 设置聚合点的中心位置以及图标
*/
public void setpositionAndIcon(String text) {
    String id="";
    int size = includeMarkers.size();
    if (size == 1) {
         return;
     }
   double lat = 0.0;
   double lng = 0.0;
   String snippet = "";
   for (MarkerOptions op : includeMarkers) {
       lat += op.getPosition().latitude;
       lng += op.getPosition().longitude;
       snippet += op.getTitle() + "\n";
       id=id+op.getTitle()+",";
    }
   options.position(new LatLng(lat / size, lng / size));// 设置中心位置为聚集点的平均距离
   options.title(id);
   options.snippet(snippet);
   int iconType = size / 2;
 
   switch (iconType) {
 
          default:
//               options.icon(BitmapDescriptorFactory
//               .fromBitmap(getViewBitmap(getView(size,text,
//               R.mipmap.content_icon_positions_yellow))));
//          break;
         }
    }
 
   public LatLngBounds getBounds() {
       return bounds;
   }
 
   public MarkerOptions getOptions() {
        return options;
   }
 
   public void setOptions(MarkerOptions options) {
          this.options = options;
   }
 
//   public View getView(int carNum,String text,int resourceId) {
//       View view = activity.getLayoutInflater().inflate(R.layout.my_car_cluster_view, null);
//        TextView carNumTextView = (TextView) view.findViewById(R.id.my_car_num);
//        TextView tv_price = (TextView) view.findViewById(R.id.tv_price);
//        TextView tv_price_status = (TextView) view.findViewById(R.id.tv_price_status);
//        tv_price.setText(text);
//        tv_price_status.setText("元/天");
//        tv_price.setTextColor(Color.parseColor("#FFBB18"));
//        tv_price_status.setTextColor(Color.parseColor("#FFBB18"));
//        LinearLayout myCarLayout = (LinearLayout) view.findViewById(R.id.my_car_bg);
//        myCarLayout.setBackgroundResource(resourceId);
//        carNumTextView.setText(String.valueOf(carNum));
//       return view;
//  }
 
/**
* 把一个view转化成bitmap对象
*/
public static Bitmap getViewBitmap(View view) {view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
         MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
     }
}