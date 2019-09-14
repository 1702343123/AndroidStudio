package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.utils.MD5Utils;
import com.example.myapplication.utils.SharedUtils;
import com.example.myapplication.utils.StatusUtils;


public class PasswordActivity extends AppCompatActivity {
    private EditText etOldPwd, etNewPwd, etNewPwdAgain;
    private Button btnSave;

    private String oldPwd, newPwd, newPwdAgain;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        StatusUtils.initToolbar(PasswordActivity.this, "修改密码", true, false);
        initView();
    }

    private void initView() {
        etOldPwd = findViewById(R.id.et_old_password);
        etNewPwd = findViewById(R.id.et_new_password);
        etNewPwdAgain = findViewById(R.id.et_new_password2);

        btnSave = findViewById(R.id.btn_preserve);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    private void save() {
        oldPwd = etOldPwd.getText().toString();
        newPwd = etNewPwd.getText().toString();
        newPwdAgain = etNewPwdAgain.getText().toString();
        username = SharedUtils.readValue(this, "loginUser");

        if (TextUtils.isEmpty(oldPwd)) {
            Toast.makeText(PasswordActivity.this, "原密码不能为空", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(newPwd) || TextUtils.isEmpty(newPwdAgain)) {
            Toast.makeText(PasswordActivity.this, "新密码不能为空", Toast.LENGTH_SHORT).show();
        } else if (!newPwd.equals(newPwdAgain)) {
            Toast.makeText(PasswordActivity.this, "两次输入的新密码不一致", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(PasswordActivity.this, "新密码设置成功", Toast.LENGTH_SHORT).show();
            SharedUtils.saveStrValue(this, username, MD5Utils.md5(newPwd));
            SharedUtils.clearLoginInfo(this);
            Intent intent = new Intent(PasswordActivity.this, LoginActivity.class);
            startActivity(intent);
            PasswordActivity.this.finish();
        }
    }

}
