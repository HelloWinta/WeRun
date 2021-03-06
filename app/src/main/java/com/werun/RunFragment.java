package com.werun;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.werun.Utils.ChartView;
import com.werun.Utils.RunDataAdapter;
import com.werun.db.Run_Data;
import com.werun.db.User_Data;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RunFragment extends Fragment {

    private int day;
    private int month;
    private int year;
    private int dayDistance;
    private String date;

    //运动的信息
    private String runDate;
    private String beginTime;
    private int time;
    private float speed;
    private float distance;
    private int calorie;

    //值其他页面要用，故设为 public
    public static String userId;//id
    public static double weight;//体重
    public static double height;//高度
    public static String icon;//头像地址
    public static String motto;//宣言
    public static double targetWeight;//目标体重

    private ChartView chartView;//申明图表
    //x轴坐标对应的数据
    private List<String> xValue = new ArrayList<>();
    //y轴坐标对应的数据
    private List<Integer> yValue = new ArrayList<>();
    //折线对应的数据
    private Map<String, Integer> value = new HashMap<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_run, container, false);

        chartView = (ChartView) view.findViewById(R.id.chartview);

        //设置图表值
        setChart();
        //应为主界面直接跳转到这，所以先将sqlite中的值提取出来绑定
        bindUserData();

        //监听当前选择的时间
        chartView.setOnChangeDateListener(new ChartView.OnChangeDateListener() {
            @Override
            public void OnChange(int selectIndex) {
                getRightDate(7-selectIndex);
                bindListViewData(date,view);
            }
        });

        getRightDate(0);//0表示今天，1表示昨天。。。。。。。。。
        bindListViewData(date ,view);
        return view;
    }

    /**
     * 绑定个人信息数据库的值
     */
    private void bindUserData() {
        List<User_Data> userData = DataSupport.findAll(User_Data.class);
        for (User_Data user_data : userData) {
            userId = user_data.getUserId();
            height = user_data.getHeight();
            weight = user_data.getWeight();
            icon = user_data.getIcon_address();
            motto = user_data.getMotto();
            targetWeight = user_data.getTarget_weight();
        }
    }

    /**
     * 设置图标上的值
     */

    private void setChart() {
        for (int i = 6; i >= 0; i--) {
            getRightDate(i);
            xValue.add(day + "日");
            value.put(day + "日", bindChartData(date));//60--240
        }

        for (int i = 0; i < 6; i++) {
            yValue.add(i * 2000);
        }
        chartView.setValue(value, xValue, yValue);

    }

    private String getRightDate(int i) {
        //获取时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -i);//i 天之前的时间
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        if (month > 9 && day >= 10) {
            date = year + "年" + month + "月" + day + "日";
        } else if (month <= 9 && day < 10) {
            date = year + "年" + "0" + month + "月" + "0" + day + "日";
        } else if (month <= 9 && day >= 10) {
            date = year + "年" + "0" + month + "月" + day + "日";
        } else if (month > 9 && day < 10) {
            date = year + "年" + month + "月" + "0" + day + "日";
        }
        return date;
    }

    /**
     * 绑定运动信息数据库
     */
    private int bindChartData(String date) {
        int distance = 0;
        List<Run_Data> runData = DataSupport.select("distance").where("runDate = ?", date).find(Run_Data.class);
        for (Run_Data run_data : runData) {

            distance += (run_data.getDistance()*1000);

        }
        return distance;

    }

    //更新图表信息
    public void updateChart() {
        setChart();
    }

    /**
     * 绑定listView数据
     */

    private List<Run_Data> runDataList = new ArrayList<>();

    private void bindListViewData(String date ,View view) {
        runDataList = DataSupport.where("runDate = ?", date).find(Run_Data.class);
        RunDataAdapter adapter = new RunDataAdapter(getContext(), R.layout.history_listview, runDataList);
        ListView listView = (ListView) view.findViewById(R.id.LV_history);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Run_Data run_data = runDataList.get(i);
                Intent intent = new Intent(getActivity(), TraceActivity.class);
                intent.putExtra("RunBegin",run_data.getBeginTime());
                startActivity(intent);
            }
        });
    }


}
