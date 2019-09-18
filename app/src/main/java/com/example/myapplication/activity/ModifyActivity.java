package com.example.myapplication.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.utils.StatusUtils;

public class ModifyActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusUtils.setImmersionMode(this);
//        setContentView(R.layout.activity_modify);
        //设置标题栏
//        StatusUtils.initToolbar(this,"修改信息",true,false);

        initData();
        initView();
    }
    private String title;
    private String value;
    private int flag;
    private void initData(){
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null){
            title=bundle.getString("title");
            value=bundle.getString("value");
            flag=bundle.getInt("flag");
        }
    }
    private EditText etContent;
    private ImageView ivDelete;
    private void initView(){
        etContent=findViewById(R.id.et_new_content);
        etContent.setText(value);

        ivDelete=findViewById(R.id.iv_delete);
        ivDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                etContent.setText("");
            }
        });
    }

//加载选项菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_info,menu);
        return true;
    }
//菜单点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_save:
                save();
                break;
            case R.id.item_cancel:
                cancel();
                break;
        }
        return true;
    }
    private void save(){
        Intent intent=new Intent();
        String value=etContent.getText().toString();
        String msg="";
        if(flag==1){
            intent.putExtra("nickname",value);
            msg="昵称不能为空";
        }else{
            intent.putExtra("signature",value);
            msg="签名不能为空";
        }
        if(TextUtils.isEmpty(value)){
            Toast.makeText(ModifyActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
            ModifyActivity.this.finish();
        }
        finish();
    }
    private void cancel(){

    }
}
