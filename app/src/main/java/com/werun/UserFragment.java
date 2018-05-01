package com.werun;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.werun.db.Run_Data;
import com.werun.db.User_Data;

import org.litepal.crud.DataSupport;

import java.util.List;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */

public class UserFragment extends Fragment implements View.OnClickListener {

    private RoundedImageView RIV_User;
    private EditText ET_motto;
    private EditText ET_height;
    private EditText ET_weight;
    private EditText ET_targetWeight;
    private TextView TV_User_Name;
    private TextView TV_totalDistance;
    private TextView TV_totalCalorie;
    private Button BTN_Edit;

    private boolean isEnable = false;

    private String userId;//id
    private double weight;//体重
    private double height;//高度
    private String icon;//头像地址
    private String motto;//宣言
    private double targetWeight;//目标体重

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        initView(view);
        initData();
        bindRun();
        return view;
    }

    //初始化控件
    private void initView(View view) {
        BTN_Edit = (Button) view.findViewById(R.id.BTN_Edit);
        RIV_User = (RoundedImageView) view.findViewById(R.id.RIV_User);
        TV_User_Name = (TextView) view.findViewById(R.id.TV_User_Name);
        ET_motto = (EditText) view.findViewById(R.id.ET_motto);
        ET_height = (EditText) view.findViewById(R.id.ET_height);
        ET_weight = (EditText) view.findViewById(R.id.ET_weight);
        ET_targetWeight = (EditText) view.findViewById(R.id.ET_targetWeight);
        TV_totalDistance = (TextView) view.findViewById(R.id.TV_totalDistance);
        TV_totalCalorie = (TextView) view.findViewById(R.id.TV_totalCalorie);
        setEnable(isEnable);

    }

    //初始化控件的默认值
    private void initData() {

        userId = RunFragment.userId;
        weight = RunFragment.weight;
        height = RunFragment.height;
        icon = RunFragment.icon;
        motto = RunFragment.motto;
        targetWeight = RunFragment.targetWeight;

        if (icon!=null) {
            Bitmap bitmap= BitmapFactory.decodeFile(icon);
            RIV_User.setImageBitmap(bitmap);
        }
        BTN_Edit.setOnClickListener(this);
        TV_User_Name.setText("id:"+userId);
        ET_motto.setText(motto);
        ET_height.setText(String.valueOf(height));
        ET_weight.setText(String.valueOf(weight));
        ET_targetWeight.setText(String.valueOf(targetWeight));

    }

    @Override
    public void onClick(View view) {
        if (!isEnable) {
            isEnable = true;
            BTN_Edit.setBackgroundResource(R.mipmap.reset);
            setEnable(isEnable);
        } else {
            isEnable = false;
            BTN_Edit.setBackgroundResource(R.mipmap.edit);
            setEnable(false);
            User_Data userData = new User_Data();
            userData.setMotto(ET_motto.getText().toString());
            userData.setWeight(Double.valueOf(ET_weight.getText().toString()));
            userData.setHeight(Double.valueOf(ET_height.getText().toString()));
            userData.setTarget_weight(Double.valueOf(ET_targetWeight.getText().toString()));
            userData.updateAll("userId = ?", userId);
        }
    }

    //设置editText是否可以编辑
    private void setEnable(boolean isEnable) {
        ET_motto.setEnabled(isEnable);
        ET_height.setEnabled(isEnable);
        ET_weight.setEnabled(isEnable);
        ET_targetWeight.setEnabled(isEnable);
    }

    //绑定全距离和总卡路里
    private void bindRun() {
        float totalDistance = 0;
        int totalCalorie = 0;

        if(!userId.equals("")) {
            List<Run_Data> runData = DataSupport.select("distance", "calorie").where("userId = ?", userId).find(Run_Data.class);
            for (Run_Data run_data : runData) {

                totalDistance += run_data.getDistance();
                totalCalorie += run_data.getCalorie();

            }

            TV_totalDistance.setText((Math.round(totalDistance * 100)) / 100 + "km");
            TV_totalCalorie.setText(totalCalorie + "kcal");
        }
    }




}
