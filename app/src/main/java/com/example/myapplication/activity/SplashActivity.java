package com.example.myapplication.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {
    private TextView tvVersion;  //控件对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //  设置启动页全屏
        LinearLayout layout = findViewById(R.id.layout);
        layout.setSystemUiVisibility(View.INVISIBLE);
        //1.获取控件对象
        tvVersion = findViewById(R.id.tv_version);
        //2.获取设置空间的值
        String version = tvVersion .getText().toString();
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(),0);
            tvVersion.setText("版本号："+ info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        //3.跳转界面
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                //不带数据的界面跳转
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        };
        timer.schedule(task,5000);
    }
}
