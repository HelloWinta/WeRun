package com.werun;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.WindowManager;

public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏以及状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        handler.sendEmptyMessageDelayed(0, 1000);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

//            //存在count则打开此preferences 不存在则创建新的preference命名为count
//            SharedPreferences preferences = getSharedPreferences("count", MODE_PRIVATE);
//            int count = preferences.getInt("count", 0);//第一个值是键，第二个是参数默认值。找不到相应值则返回默认值。
//
//            /**
//             * 如果用户不是第一次使用则直接跳转到显示界面，否则就跳转到引导界面
//             */
//
//            if (count == 0) {
//                SharedPreferences.Editor editor = preferences.edit();
//                editor.putInt("count", 1);
//                editor.commit();
//                getGuideActivity();
//            } else {
//                getMainActivity();
//            }
            getGuideActivity();
        }
    };

    public void getMainActivity() {
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void getGuideActivity(){
        Intent intent = new Intent(WelcomeActivity.this, GuideActivity.class);
        startActivity(intent);
        finish();
    }
}
