package com.werun;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.PolylineOptions;
import com.werun.Utils.SaveArrayListUtil;

import java.util.ArrayList;

public class TraceActivity extends AppCompatActivity {
    private MapView mapView;
    private ArrayList<LatLng> latLngs;
    AMap aMap;
    private String runBeignTime;



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trace);
        //设置mapView
        mapView = (MapView) findViewById(R.id.trace_map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        aMap = mapView.getMap();
        runBeignTime = getIntent().getStringExtra("RunBegin");
        SaveArrayListUtil saveArrayListUtil = new SaveArrayListUtil();
        latLngs = saveArrayListUtil.getSearchArrayList(getApplicationContext(), runBeignTime);
        printTrace();
    }

    //画轨迹
    private void printTrace() {
        if(latLngs.size()>0) {
            aMap.addPolyline((new PolylineOptions()).
                    addAll(latLngs).width(10).color(Color.argb(255, 1, 1, 1)));
        }
    }
}
