package com.werun;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.PolylineOptions;
import com.werun.Utils.MapFixUtil;
import com.werun.Utils.PositionUtil;
import com.werun.db.Run_Data;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Run extends Activity implements LocationSource, AMapLocationListener, View.OnClickListener {

    private Chronometer CM_runTime;
    private TextView TV_runDistance;
    private TextView TV_runSpeed;
    private TextView TV_consumeCalorie;
    private Button BTN_stop;

    private float saveSpeed;
    private int runTime;
    private float totalDistance = 0;
    private float latDistance = 0;//两个坐标间的距离
    private float saveDistance;
    private int saveCalorie;
    private String beginTime;
    private String runDate;


    private AMap aMap;
    private MapView mapView;
    //以前的定位点
    private LatLng oldFixLatLng;
    //是否是第一次定位
    private boolean isFirstLatLng;

    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private List<LatLng> latLngs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_run);

        //设置mapView
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写

        isFirstLatLng = true;//初次打开第一次获取坐标
        latLngs = new ArrayList<>();
        init();

        //获取开始运动的时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        beginTime = simpleDateFormat.format(date).toString();
        //截取前11位，结果为yyyy年MM月dd日
        runDate = beginTime.substring(0, 11);

        //配置计时器
        CM_runTime = (Chronometer) findViewById(R.id.CM_runTime);
        CM_runTime.start();

    }

    private void init() {

        if (aMap == null) {
            aMap = mapView.getMap();
        }
        //定位
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);
        //画线
        // 缩放级别（zoom）：地图缩放级别范围为【4-20级】，值越大地图越详细
        aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
        //使用 aMap.setMapTextZIndex(2) 可以将地图底图文字设置在添加的覆盖物之上
        aMap.setMapTextZIndex(2);

        TV_runDistance = (TextView) findViewById(R.id.TV_runDistance);
        TV_runSpeed = (TextView) findViewById(R.id.TV_runSpeed);
        TV_consumeCalorie = (TextView) findViewById(R.id.TV_consumeCalorie);
        // 暂停按钮
        BTN_stop = (Button) findViewById(R.id.BTN_stop);
        BTN_stop.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.BTN_stop:
                LitePal.getDatabase();
                setRunData();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 运动数据通过litepal绑定到sqlite
     */

    public void setRunData() {
        Run_Data run_data = new Run_Data();
        run_data.setUserId(RunFragment.userId);
        //当前运动的开始时间为RunId
        run_data.setRunDate(runDate);
        run_data.setCalorie(saveCalorie);
        run_data.setSpeed(saveSpeed);
        run_data.setDistance(saveDistance);
        run_data.setBeginTime(beginTime);
        run_data.setTime(runTime);
        run_data.save();
    }


    /**
     * 绘制两个坐标点之间的线段,从以前位置到现在位置
     */
    private void setUpMap(LatLng oldData, LatLng newData) {

        if (AMapUtils.calculateLineDistance(oldData, newData) < 50) {
            // 绘制一个大地曲线
            aMap.addPolyline((new PolylineOptions())
                    .add(oldData, newData)
                    .geodesic(true).color(Color.GREEN));

            latDistance = AMapUtils.calculateLineDistance(oldData, newData);
            totalDistance += (latDistance / 1000);
            saveDistance = (float) (Math.round(totalDistance * 100)) / 100;//将米转化为公里数保持两位小数
            TV_runDistance.setText(saveDistance + "公里");

            //更新速度
            updateTime();
            saveSpeed = saveDistance/(runTime/3600);
            TV_runSpeed.setText(saveSpeed + "km/h");

            //更新消耗的卡路里
            //体重（kg）* 距离（km）* 运动系数（k）；健走：k=0.8214；跑步：k=1.036；自行车：k=0.6142；轮滑、溜冰：k=0.518；室外滑雪：k=0.888
            saveCalorie = (int) (RunFragment.weight * totalDistance * 1.306);
            TV_consumeCalorie.setText(saveCalorie + "Kcal");

        }

    }

    /**
     * 转换时间
     */
    private void updateTime() {
        String time = CM_runTime.getText().toString();// 00:00
        String[] split = time.split(":");
        int totals = Integer.parseInt(split[1]) + Integer.parseInt(split[0]) * 60;
        runTime = totals;


    }


    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                //定位成功
                LatLng newLatLng = PositionUtil.getLocationLatLng(amapLocation);
                double lon = newLatLng.longitude;
                double lat = newLatLng.latitude;
                double point[] = MapFixUtil.transform(lat, lon);
                LatLng newFixLatLng = new LatLng(point[0], point[1]);
                //添加起点坐标
                latLngs.add(newFixLatLng);
                if (isFirstLatLng) {
                    //记录第一次的定位信息
                    oldFixLatLng = newFixLatLng;
                    isFirstLatLng = false;
                }
                //位置有变化
                if (oldFixLatLng != newLatLng) {
                    Log.e("Amap", amapLocation.getLatitude() + "," + amapLocation.getLongitude());
                    setUpMap(oldFixLatLng, newLatLng);
                    oldFixLatLng = newLatLng;
                    //跑步过程中的坐标
                    latLngs.add(newLatLng);
                }

            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
                if (isFirstLatLng) {
                    Toast.makeText(this, errText, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mLocationOption.setOnceLocation(false);
            mLocationOption.setOnceLocationLatest(true);
            /**
             * 设置是否优先返回GPS定位结果，如果30秒内GPS没有返回定位结果则进行网络定位
             * 注意：只有在高精度模式下的单次定位有效，其他方式无效
             */
            mLocationOption.setGpsFirst(true);
            // 设置发送定位请求的时间间隔,最小值为1000ms,1秒更新一次定位信息
            mLocationOption.setInterval(2000);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.setLocationOption(mLocationOption);


            MyLocationStyle myLocationStyle = new MyLocationStyle();
            myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));
            myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));
            // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
            // 设置定位的类型为定位模式 ，可以由定位 LOCATION_TYPE_LOCATE、跟随 LOCATION_TYPE_MAP_FOLLOW 或地图根据面向方向旋转 LOCATION_TYPE_MAP_ROTATE
            //aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_ROTATE);

            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
            aMap.setMyLocationStyle(myLocationStyle);
            mlocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }

}
