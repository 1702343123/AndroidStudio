package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.utils.MD5Utils;
import com.example.myapplication.utils.SharedUtils;
import com.example.myapplication.utils.StatusUtils;


public class RegisterActivity extends AppCompatActivity
        implements View.OnClickListener {
    private static final String TAG = "RegisterActivity";
    // 1. 获取界面上的控件
    // 2. button的点击事件的监听
    // 3. 处理点击事件
    // 3.1 获取控件的值
    // 3.2 检查数据的有效性
    // 3.3 将注册信息存储
    // 3.4 跳转到登录界面
    private EditText etUsername, etPassword, etPwdAgain;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusUtils.setImmersionMode(this);
        setContentView(R.layout.activity_register);

        // 初始化标题栏
        StatusUtils.initToolbar(this, "注册", true, false);
        // 1. 获取界面上的控件
        initView();
        // 2. button的点击事件的监听
        btnRegister.setOnClickListener(this);
    }

    private void initView() {
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        etPwdAgain = findViewById(R.id.et_pwd_again);
        btnRegister = findViewById(R.id.btn_register);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                register();
                break;
            case R.id.btn_login:
                break;
        }
    }

    private void register() {
        // 3.1
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String pwdAgain = etPwdAgain.getText().toString();
        // 3.2
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(RegisterActivity.this, "用户名不能为空",
                    Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password) || TextUtils.isEmpty(pwdAgain)) {
            Toast.makeText(RegisterActivity.this, "密码不能为空",
                    Toast.LENGTH_SHORT).show();
        } else if (!password.equals(pwdAgain)) {
            Toast.makeText(RegisterActivity.this, "两次密码必须一致",
                    Toast.LENGTH_SHORT).show();
        } else if (SharedUtils.isExist(this,username)) {
            Toast.makeText(RegisterActivity.this, "此用户已存在",
                    Toast.LENGTH_SHORT).show();
        } else {
            //注册成功后
//            savePref(username, MD5Utils.md5(password));
            SharedUtils.saveStrValue(this, username, MD5Utils.md5(password));
            Intent intent = new Intent();
            intent.putExtra("username", username);
            setResult(RESULT_OK,intent);
            finish();
        }
    }
///**
// * 保存注册的用户名和密码
// * */
//    private void savePref(String username, String password) {
//        SharedPreferences sp = getSharedPreferences("userInfo", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
////        editor.putString("username", username);
////        editor.putString("password", password);
//        editor.putString(username, password);
//        editor.apply();
//        Log.d(TAG,password);
//    }
///**
// * 判断用户是否存在
// * */
//    private boolean isExist(String username) {
//        SharedPreferences sp = getSharedPreferences("userInfo", MODE_PRIVATE);
//        String pwd = sp.getString(username, "");
//        return !TextUtils.isEmpty(pwd);
//    }
}
