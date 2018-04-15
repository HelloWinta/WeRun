package com.werun;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class GuideActivity extends FragmentActivity{

    private CheckPermission checkPermission;
    private Button BTN_To_Main;

    private Guide_Fragment_1 mGuideFragment_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏以及状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide);
        BTN_To_Main = (Button) findViewById(R.id.BTN_To_Main);
        BTN_To_Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"稍后可在 我 中设置",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        replaceFragment(new Guide_Fragment_1());
        //申请权限
        checkPermission = new CheckPermission(this)
        {
            @Override
            public void permissionSuccess()
            {
            }

            @Override
            public void negativeButton()
            {
                //如果不重写，默认是finish
                //super.negativeButton();
            }
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkPermission.permission(CheckPermission.REQUEST_CODE_PERMISSION_CAMERA);
        }
    }

    //配置Fragment的转换
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_guide_content, fragment);
        transaction.commit();
    }


}
