package com.wanquan.mlmmx.mlmm.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.wanquan.mlmmx.mlmm.R;

/**
 * 描述：启动页
 * 作者：薛昌峰
 * 时间：2018.03.21
 */
public class SplashActivity extends AppCompatActivity {
    View activity_mine_splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activity_mine_splash = findViewById(R.id.activity_mine_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                jumpNextPage();
            }
        }, 2000);
    }

    private void jumpNextPage() {
//        //判断之前有没有显示过新手引导页
//        SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
//        boolean userGuide = sp.getBoolean("activity_splash", false);
//        if (!userGuide) {
//            //跳到新手引导页
//            startActivity(new Intent(SplashActivity.this, GuideActivity.class));
//        } else {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
//        }
        finish();
    }
}
