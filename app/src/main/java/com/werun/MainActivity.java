package com.werun;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;


public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private RunFragment mRunFragment;
    private HistoryFragment mHistoryFragment;
    private UserFragment mUserFragment;

    private Button mBTN_History;
    private Button mBTN_Run;
    private Button mBTN_User;

    private boolean is_History ;
    private boolean is_Run;
    private boolean is_User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        //配置RunFragment为默认碎片
        replaceFragment(new RunFragment());
        is_Run = true;
        mBTN_Run.setBackgroundResource(R.mipmap.isrun);//将Run按钮设置为选中状态的颜色


    }

    //配置底部三个按钮
    private void initView() {
        mBTN_History = (Button) findViewById(R.id.BTN_History);
        mBTN_Run = (Button) findViewById(R.id.BTN_Run);
        mBTN_User = (Button) findViewById(R.id.BTN_User);
    }

    //为三个按钮绑定监听器
    private void initData() {
        mBTN_History.setOnClickListener(this);
        mBTN_Run.setOnClickListener(this);
        mBTN_User.setOnClickListener(this);
    }

    //复写onClick方法
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.BTN_History:
                click_History();
                break;
            case R.id.BTN_Run:
                click_Run();
                break;
            case R.id.BTN_User:
                click_User();
                break;
            default:
                break;
        }
    }

    //配置History按钮点击事件
    private void click_History() {
        if (is_History == false) {
            replaceFragment(new HistoryFragment());
            is_History = true;
            is_Run = false;
            is_User = false;
            //设置按钮是否能点击
            mBTN_History.setEnabled(false);
            mBTN_Run.setEnabled(true);
            mBTN_User.setEnabled(true);
            //设置按钮的背景色
            mBTN_History.setBackgroundResource(R.mipmap.ishistory);
            mBTN_Run.setBackgroundResource(R.mipmap.unrun);
            mBTN_User.setBackgroundResource(R.mipmap.unuser);
        }
    }

    //配置Run按钮点击事件
    private void click_Run() {
        if (is_Run == false) {
            replaceFragment(new RunFragment());
            is_History = false;
            is_Run = true;
            is_User = false;
            mBTN_History.setEnabled(true);
            mBTN_Run.setEnabled(false);
            mBTN_User.setEnabled(true);
            //设置按钮的背景色
            mBTN_History.setBackgroundResource(R.mipmap.unhistory);
            mBTN_Run.setBackgroundResource(R.mipmap.isrun);
            mBTN_User.setBackgroundResource(R.mipmap.unuser);

        }
    }

    //配置User按钮点击事件
    private void click_User() {
        if (is_User == false) {
            replaceFragment(new UserFragment());
            is_History = false;
            is_Run = false;
            is_User = true;
            mBTN_History.setEnabled(true);
            mBTN_Run.setEnabled(true);
            mBTN_User.setEnabled(false);
            //设置按钮的背景色
            mBTN_History.setBackgroundResource(R.mipmap.unhistory);
            mBTN_Run.setBackgroundResource(R.mipmap.unrun);
            mBTN_User.setBackgroundResource(R.mipmap.isuser);
        }
    }


    //配置Fragment的转换
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_content, fragment);
        transaction.commit();
    }
}
