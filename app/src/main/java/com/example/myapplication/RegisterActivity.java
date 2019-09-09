package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.myapplication.utils.MD5Utils;

public class RegisterActivity extends AppCompatActivity {
    //1.获取界面上的控件
    //2.button的点击事件的监听
    //3.处理点击事件
       //3.1获取控件的值
       //3.2检查数据的有效性
       //3.3将注册信息存储
       //3.4跳转到登陆界面
    private EditText etUsername,etPassword,etPwdAgain;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //1.获取界面上的控件
        initView();
        //button的点击事件的监听
        initToolBar();

        //2.
        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //3.
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String pwdAgain = etPwdAgain.getText().toString();
                //3.2
                if(TextUtils.isEmpty(username)){
                    Toast.makeText(RegisterActivity.this,"用户不能为空",
                            Toast.LENGTH_LONG).show();
                }else if(TextUtils.isEmpty(password)|| TextUtils.isEmpty(pwdAgain)){
                    Toast.makeText(RegisterActivity.this,"密码不能为空",
                            Toast.LENGTH_LONG).show();
                }else if (!password.equals(pwdAgain)){
                    Toast.makeText(RegisterActivity.this,"两次密码必须一致",
                            Toast.LENGTH_LONG).show();
                }else if (isExist(username)){
                    Toast.makeText(RegisterActivity.this,"用户名已存在",
                            Toast.LENGTH_LONG).show();
                }else{
                    //注册成功后
                    savePref(username, MD5Utils.md5(password));
                    Intent intent = new Intent();
                    intent.putExtra("username",username);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }

    private void initToolBar() {
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.title_toolbar);
        toolbar.setTitle("注册");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
              actionBar.setDisplayHomeAsUpEnabled(true);//设置返回键
//            actionBar.setHomeButtonEnabled(true);//设置是否是首页
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                RegisterActivity.this.finish();
            }
        });
    }

    private void savePref(String username, String password) {
        SharedPreferences sp = getSharedPreferences("userInfo",MODE_PRIVATE);
        SharedPreferences .Editor editor = sp.edit();
//        editor.putString("username",username);
//        editor.putString("password",password);
        editor.putString(username,password);
        editor.apply();
        Log.d("RegisterActivity",password);
    }

    /**
     * 判断用户是否存在
     * 保存注册的用户名和密码
     * @param username 用户名，类型String
     * @param password 密码，类型
     */
    private boolean isExist(String username){
        SharedPreferences sp = getSharedPreferences("userInfo",MODE_PRIVATE);
        String pwd = sp.getString(username,"");
        return !TextUtils.isEmpty(pwd);
    }

    private void initView() {
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        etPwdAgain = findViewById(R.id.et_pwd_again);
        btnRegister = findViewById(R.id.btn_register);

    }
//    private saveLogiStatus(String username,boolean isLogin){
//        getSharedPreferences("userInfo",MODE_PRIVATE)
//    }
}
