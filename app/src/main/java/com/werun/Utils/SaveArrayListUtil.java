package com.werun.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.amap.api.maps.model.LatLng;

import java.util.ArrayList;

public class SaveArrayListUtil {
    /**
     * 将ArrayList的内容保存到sp
     */

    public void saveArrayList(Context context, ArrayList<LatLng> latlngList, String runBegin) {
        //定义SP.Editor和文件名称
        SharedPreferences.Editor editor = context.getSharedPreferences("MapData", context.MODE_PRIVATE).edit();
        //将结果放入文件，关键是把集合大小放入，为了后面的取出判断大小。
        editor.putInt(runBegin, latlngList.size());
        for ( int i = 0;i < latlngList.size();i++) {
            //用条目+i,代表键，
            editor.putString("item_" + i, latlngList.get(i).latitude + "|" + latlngList.get(i).longitude);
        }
        editor.commit();
    }

    public ArrayList<LatLng> getSearchArrayList(Context context , String runBegin) {
        //先定位到文件
        SharedPreferences preferDataList = context.getSharedPreferences(
                "MapData", context.MODE_PRIVATE);
        ArrayList<LatLng> latLngs = new ArrayList<>();
        //刚才存的大小
        int searchNums = preferDataList.getInt(runBegin, 0);
        for (int i = 0;i < searchNums;i++) {
            String [] data = preferDataList.getString("item_" + i, null).split("\\|");
            Log.e("dsadfasdfasdfasd", Double.valueOf(data[0]) + "," + Double.valueOf(data[1]));
            LatLng latLng = new LatLng(Double.valueOf(data[0]), Double.valueOf(data[1]));
            latLngs.add(latLng);

        }
        return latLngs;
    }

}
