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
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.werun.db.User_Data;

import org.litepal.crud.DataSupport;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */

public class UserFragment extends Fragment {

    private Button BTN_setRemind;
    private RoundedImageView RIV_User;
    private TextView TV_motto;
    private TextView TV_height;
    private TextView TV_weight;
    private TextView TV_targetWeight;

    private double height;//高度
    private double weight;//体重
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
        return view;
    }

    //初始化控件
    private void initView(View view) {
        RIV_User = (RoundedImageView) view.findViewById(R.id.RIV_User);
        TV_motto = (TextView) view.findViewById(R.id.TV_motto);
        TV_height = (TextView) view.findViewById(R.id.TV_height);
        TV_weight = (TextView) view.findViewById(R.id.TV_weight);
        TV_targetWeight = (TextView) view.findViewById(R.id.TV_targetWeight);
        BTN_setRemind = (Button) view.findViewById(R.id.BTN_Setremind);
    }

    //初始化控件的默认值
    private void initData() {
        bindData();
        if (icon!=null) {
            Bitmap bitmap= BitmapFactory.decodeFile(icon);
            RIV_User.setImageBitmap(bitmap);
        }
        TV_motto.setText(motto);
        TV_height.setText(String.valueOf(height));
        TV_weight.setText(String.valueOf(weight));
        TV_targetWeight.setText(String.valueOf(targetWeight));

    }

    //绑定数据库的值
    private void bindData() {
        List<User_Data> userData = DataSupport.findAll(User_Data.class);
        for (User_Data user_data : userData) {
            height = user_data.getHeight();
            weight = user_data.getWeight();
            icon = user_data.getIcon_address();
            motto = user_data.getMotto();
            targetWeight = user_data.getTarget_weight();
        }
    }

}
