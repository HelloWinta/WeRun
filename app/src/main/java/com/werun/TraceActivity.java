package com.werun;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.werun.Utils.SaveArrayListUtil;

import java.util.ArrayList;

public class TraceActivity extends AppCompatActivity implements View.OnClickListener{
    private MapView mapView;
    private ArrayList<LatLng> latLngs;
    AMap aMap;
    private String runBeignTime;

    private Button BTN_before;
    private Button BTN_share;



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trace);
        //设置mapView
        mapView = (MapView) findViewById(R.id.trace_map);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
        runBeignTime = getIntent().getStringExtra("RunBegin");
        SaveArrayListUtil saveArrayListUtil = new SaveArrayListUtil();
        latLngs = saveArrayListUtil.getSearchArrayList(getApplicationContext(), runBeignTime);
        //将默认位置设置为轨迹开始的位置
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngs.get(0),16));
        //画轨迹
        printTrace();


        BTN_before = (Button) findViewById(R.id.BTN_before);
        BTN_share = (Button) findViewById(R.id.BTN_share);
        BTN_before.setOnClickListener(this);
        BTN_share.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.BTN_before:
                this.finish();
                break;
            case R.id.BTN_share:
                break;
        }
    }

    //画轨迹
    private void printTrace() {
        if(latLngs.size()>0) {
            aMap.addPolyline((new PolylineOptions()).
                    addAll(latLngs).width(15).color(Color.argb(255, 51, 153, 51)));
            LatLng startLatLng = latLngs.get(0);
            LatLng endLatLng = latLngs.get(latLngs.size() - 1);
            Marker markerStart = aMap.addMarker(new MarkerOptions()
                    .position(startLatLng)
                    .icon(BitmapDescriptorFactory
                            .fromBitmap(BitmapFactory
                                    .decodeResource(getResources(), R.mipmap.startpoint))));

            Marker markerEnd = aMap.addMarker(new MarkerOptions()
                    .position(endLatLng)
                    .icon(BitmapDescriptorFactory
                            .fromBitmap(BitmapFactory
                                    .decodeResource(getResources(), R.mipmap.endpoint))));

        }
    }
}
