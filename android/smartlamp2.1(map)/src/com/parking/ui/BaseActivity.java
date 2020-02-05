package com.parking.ui;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.model.AMapCalcRouteResult;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapModelCross;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviRouteNotifyData;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;

import com.autonavi.tbt.TrafficFacilityInfo;
import com.parking.util.ErrorInfo;
import com.parking.util.TTSController;

import java.util.ArrayList;
import java.util.List;


public class BaseActivity extends Activity implements AMapNaviListener, AMapNaviViewListener {

    protected AMapNaviView mAMapNaviView;
    protected AMapNavi mAMapNavi;
    protected TTSController mTtsManager;
//    protected NaviLatLng mEndLatlng = new NaviLatLng(40.084894,116.603039);
//    protected NaviLatLng mStartLatlng = new NaviLatLng(39.825934,116.342972);
    List<NaviLatLng> mStartList = new ArrayList<NaviLatLng>();
	List<NaviLatLng> mEndList = new ArrayList<NaviLatLng>();
	NaviLatLng mEndLatlng;
	// = new NaviLatLng(39.925846, 116.432765);
	NaviLatLng mStartLatlng;
    protected List<NaviLatLng> mWayPointList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mAMapNavi = AMapNavi.getInstance(getApplicationContext());
        mAMapNavi.addAMapNaviListener(this);
        mAMapNavi.setUseInnerVoice(true);

        //����ģ�⵼�����г��ٶ�
        mAMapNavi.setEmulatorNaviSpeed(75);
//        sList.add(mStartLatlng);
//        eList.add(mEndLatlng);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAMapNaviView.onResume();
        SharedPreferences sp = getSharedPreferences("mrsoft", MODE_PRIVATE); // ���Preferences
		String jing = sp.getString("jing", "jing");
		String wei = sp.getString("wei", "wei");
		String geoLat = sp.getString("geoLat", "geoLat");
		String geoLng = sp.getString("geoLng", "geoLng");
		mStartLatlng = new NaviLatLng(Double.parseDouble(geoLat),
				Double.parseDouble(geoLng));
		mEndLatlng = new NaviLatLng(Double.parseDouble(jing),
				Double.parseDouble(wei));
		mStartList.add(mStartLatlng);
		mEndList.add(mEndLatlng);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAMapNaviView.onPause();

//
//        ֹͣ����֮�󣬻ᴥ���ײ�stop��Ȼ��Ͳ������лص��ˣ�����Ѷ�ɵ�ǰ����û��˵��İ�仰���ǻ�˵��
//        mAMapNavi.stopNavi();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAMapNaviView.onDestroy();
        //since 1.6.0 ������naviview destroy��ʱ���Զ�ִ��AMapNavi.stopNavi();������ִ��
        mAMapNavi.stopNavi();
        mAMapNavi.destroy();
    }

    @Override
    public void onInitNaviFailure() {
        Toast.makeText(this, "init navi Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInitNaviSuccess() {
        //��ʼ���ɹ�
    }

    @Override
    public void onStartNavi(int type) {
        //��ʼ�����ص�
    }

    @Override
    public void onTrafficStatusUpdate() {
        //
    }

    @Override
    public void onLocationChange(AMapNaviLocation location) {
        //��ǰλ�ûص�
    }

    @Override
    public void onGetNavigationText(int type, String text) {
        //�������ͺͲ������ֻص�
    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void onEndEmulatorNavi() {
        //����ģ�⵼��
    }

    @Override
    public void onArriveDestination() {
        //����Ŀ�ĵ�
    }

    @Override
    public void onCalculateRouteFailure(int errorInfo) {
        //·�߼���ʧ��
        Log.e("dm", "--------------------------------------------");
        Log.i("dm", "·�߼���ʧ�ܣ�������=" + errorInfo + ",Error Message= " + ErrorInfo.getError(errorInfo));
        Log.i("dm", "��������ϸ���Ӽ���http://lbs.amap.com/api/android-navi-sdk/guide/tools/errorcode/");
        Log.e("dm", "--------------------------------------------");
        Toast.makeText(this, "errorInfo��" + errorInfo + ",Message��" + ErrorInfo.getError(errorInfo), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onReCalculateRouteForYaw() {
        //ƫ�������¼���·�߻ص�
    }

    @Override
    public void onReCalculateRouteForTrafficJam() {
        //ӵ�º����¼���·�߻ص�
    }

    @Override
    public void onArrivedWayPoint(int wayID) {
        //����;����
    }

    @Override
    public void onGpsOpenStatus(boolean enabled) {
        //GPS����״̬�ص�
    }

    @Override
    public void onNaviSetting() {
        //�ײ��������õ���ص�
    }

    @Override
    public void onNaviMapMode(int isLock) {
        //��ͼ��ģʽ������������
    }

    @Override
    public void onNaviCancel() {
        finish();
    }


    @Override
    public void onNaviTurnClick() {
        //ת��view�ĵ���ص�
    }

    @Override
    public void onNextRoadClick() {
        //��һ����·View����ص�
    }


    @Override
    public void onScanViewButtonClick() {
        //ȫ����ť����ص�
    }

    @Deprecated
    @Override
    public void onNaviInfoUpdated(AMapNaviInfo naviInfo) {
        //��ʱ
    }

    @Override
    public void updateCameraInfo(AMapNaviCameraInfo[] aMapCameraInfos) {

    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] amapServiceAreaInfos) {

    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviinfo) {
        //���������е���Ϣ���£��뿴NaviInfo�ľ���˵��
    }

    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {
        //�ѹ�ʱ
    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {
        //�ѹ�ʱ
    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {
        //��ʾת��ص�
    }

    @Override
    public void hideCross() {
        //����ת��ص�
    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] laneInfos, byte[] laneBackgroundInfo, byte[] laneRecommendedInfo) {
        //��ʾ������Ϣ

    }

    @Override
    public void hideLaneInfo() {
        //���س�����Ϣ
    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {
        //��·����·�ɹ��ص�
    }

    @Override
    public void notifyParallelRoad(int i) {
        if (i == 0) {
//            Toast.makeText(this, "��ǰ������·����", Toast.LENGTH_SHORT).show();
            Log.d("wlx", "��ǰ������·����");
            return;
        }
        if (i == 1) {
//            Toast.makeText(this, "��ǰ����·", Toast.LENGTH_SHORT).show();

            Log.d("wlx", "��ǰ����·");
            return;
        }
        if (i == 2) {
//            Toast.makeText(this, "��ǰ�ڸ�·", Toast.LENGTH_SHORT).show();

            Log.d("wlx", "��ǰ�ڸ�·");
        }
    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {
        //���½�ͨ��ʩ��Ϣ
    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {
        //����Ѳ��ģʽ��ͳ����Ϣ
    }


    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {
        //����Ѳ��ģʽ��ӵ����Ϣ
    }

    @Override
    public void onPlayRing(int i) {

    }


    @Override
    public void onLockMap(boolean isLock) {
        //����ͼ״̬�����仯ʱ�ص�
    }

    @Override
    public void onNaviViewLoaded() {
        Log.d("wlx", "����ҳ����سɹ�");
        Log.d("wlx", "�벻Ҫʹ��AMapNaviView.getMap().setOnMapLoadedListener();��overwrite����SDK�ڲ������߼�");
    }

    @Override
    public void onMapTypeChanged(int i) {

    }

    @Override
    public void onNaviViewShowMode(int i) {

    }

    @Override
    public boolean onNaviBackClick() {
        return false;
    }


    @Override
    public void showModeCross(AMapModelCross aMapModelCross) {

    }

    @Override
    public void hideModeCross() {

    }

    @Override
    public void updateIntervalCameraInfo(AMapNaviCameraInfo aMapNaviCameraInfo, AMapNaviCameraInfo aMapNaviCameraInfo1, int i) {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo aMapLaneInfo) {

    }

    @Override
    public void onCalculateRouteSuccess(AMapCalcRouteResult aMapCalcRouteResult) {

    }

    @Override
    public void onCalculateRouteFailure(AMapCalcRouteResult aMapCalcRouteResult) {

    }

    @Override
    public void onNaviRouteNotify(AMapNaviRouteNotifyData aMapNaviRouteNotifyData) {

    }
}
