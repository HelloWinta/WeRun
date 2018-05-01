package com.werun.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.werun.R;
import com.werun.db.Run_Data;
import com.werun.db.User_Data;

import java.util.List;

public class RunDataAdapter extends ArrayAdapter<Run_Data> {

    private int resourceId;

    public RunDataAdapter(Context context, int textViewResourceId, List<Run_Data> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Run_Data runData = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TextView TV_runDate = (TextView) view.findViewById(R.id.TV_runDate);
        TextView TV_runCalorie = (TextView) view.findViewById(R.id.TV_runCalorie);
        TextView TV_runDistance = (TextView) view.findViewById(R.id.TV_runDistance);
        TextView TV_runSpeed = (TextView) view.findViewById(R.id.TV_runSpeed);

        TV_runDate.setText(runData.getBeginTime());
        TV_runCalorie.setText("消耗的卡路里:"+runData.getCalorie()+"kcal");
        TV_runDistance.setText("运动距离:"+String.valueOf(runData.getDistance())+"km");
        TV_runSpeed.setText("速度:"+String.valueOf(runData.getSpeed())+"km/h");
        return view;
    }
}
