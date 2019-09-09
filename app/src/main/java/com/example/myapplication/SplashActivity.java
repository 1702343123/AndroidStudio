package com.example.myapplication;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {
    private TextView tvVersion;  //控件对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //1.获取需要的空间
        tvVersion = findViewById(R.id.tv_version);
        //2.获取或设置空间的值
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
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
            }
        };
        timer.schedule(task,3000);
    }
}
