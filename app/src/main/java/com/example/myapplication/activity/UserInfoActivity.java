package com.example.myapplication.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.R;
import com.example.myapplication.entity.User;
import com.example.myapplication.service.UserService;
import com.example.myapplication.service.UserServiceImpl;
import com.example.myapplication.utils.SharedUtils;
import com.example.myapplication.utils.StatusUtils;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener {
    //1.定义界面上的控件对象
    private TextView tvNickname,tvSignature,tvUsername,tvSex;
    private RelativeLayout nicknameLayout,signatureLayout,sexLayout;

    //2.定义所需的变量
    private String spUsername;
    private User userInfo;
    private UserService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusUtils.setImmersionMode(this);
        setContentView(R.layout.activity_user_info);

        //3.设置标题栏
        StatusUtils.initToolbar(this,"个人信息",true,false);
        //4.从数据库、网络、intent或存储中获取数据，初始化界面控件（可选）
        initData();
        //5.获取所有控件对象，设置监听器（必选）
        initView();
    }
    private void initData(){
        spUsername= SharedUtils.readValue(this,"loginUser");

        service=new UserServiceImpl(this);
        userInfo=service.get(spUsername);//从数据库读取
        userInfo=readFromInternal();//从内部存储读取
        userInfo=readPrivateExStorage();//从外部的私有存储读取
        userInfo=readPublicExStorage();//从外部公有存储读取
        if(userInfo==null){
            userInfo=new User();
            userInfo.setUsername(spUsername);
            userInfo.setNickname("课程助手");
            userInfo.setSignature("课程助手");
            userInfo.setSex("男");

            service.save(userInfo);
            saveToInternal(userInfo);
            savePrivateExStorage(userInfo);
            savePublicExStorage(userInfo);
        }
    }

    private void initView(){
        //1.获取控件对象
        tvUsername=findViewById(R.id.tv_username);
        tvNickname=findViewById(R.id.tv_nickname);
        tvSex=findViewById(R.id.tv_sex);
        tvSignature=findViewById(R.id.tv_signature);

        nicknameLayout=findViewById(R.id.rl_nickname);
        sexLayout=findViewById(R.id.rl_sex);
        signatureLayout=findViewById(R.id.rl_signature);
        //2.设置数据库获取的数据
        tvUsername.setText(userInfo.getUsername());
        tvNickname.setText(userInfo.getNickname());
        tvSex.setText(userInfo.getSex());
        tvSignature.setText(userInfo.getSignature());
        //3.设置监听器
        nicknameLayout.setOnClickListener(this);
        sexLayout.setOnClickListener(this);
        signatureLayout.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.rl_nickname:
                modifyNickname();
                break;
            case R.id.rl_sex:
                modifySex();
                break;
            case  R.id.rl_signature:
                modifySignature();
                break;
        }
    }
    private void modifyNickname(){
        //1.获取已有内容
        String nickname=tvNickname.getText().toString();
        //2.根据需要传递数据到下一个Activity
        Intent intent=new Intent(UserInfoActivity.this,ModifyActivity.class);
        //Bundle对象用于传递有明确类型的简单类型和复杂数据类型的数据(简单数据类型也可以用Intent传入)
        //Bundle需要加载到Intent中才能传递
        Bundle bundle=new Bundle();
        bundle.putString("title","设置昵称");//标题栏的标题
        bundle.putString("value",nickname);//内容
        bundle.putInt("flag",1);//用于修改昵称还是签名
        intent.putExtras(bundle);
        //3.启动下一个界面
        startActivityForResult(intent,1);
    }
    private void modifySex(){
        final  String[] datas={"男","女"};
        String sex = tvSex.getText().toString();
        //获取性别所在的索引
        final List<String> sexs = Arrays.asList(datas);
        int selected = sexs.indexOf(sex);
        new AlertDialog.Builder(this)
                .setTitle("性别")
                .setSingleChoiceItems(datas, selected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String sex = datas[which];
                        tvSex.setText(sex);
                        userInfo.setSex(sex);

                        service.modify(userInfo);
                        saveToInternal(userInfo);
                        savePrivateExStorage(userInfo);
                        savePublicExStorage(userInfo);

                        dialogInterface.dismiss();
                    }
                }).show();
    }
    private void modifySignature(){
        //1.获取已有内容
        String signature=tvSignature.getText().toString();
        //2.根据需要传递数据到下一个Activity
        Intent intent=new Intent(UserInfoActivity.this,ModifyActivity.class);
        //Bundle对象用于传递有明确类型的简单类型和复杂数据类型的数据(简单数据类型也可以用Intent传入)
        //Bundle需要加载到Intent中才能传递
        Bundle bundle=new Bundle();
        bundle.putString("title","设置签名");//标题栏的标题
        bundle.putString("value",signature);//内容
        bundle.putInt("flag",2);//用于修改昵称还是签名
        intent.putExtras(bundle);
        //3.启动下一个界面
        startActivityForResult(intent,2);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        //1.对空数据、返回异常做判断
        if(data==null||resultCode!=RESULT_OK){
            Toast.makeText(this, "未知错误", Toast.LENGTH_SHORT).show();
            return;
        }
        //2.根据resquestCode进行对应的保护
        //2.1获取data数据
        if(requestCode==1){
            //2.2设置userInfo对应的属性值，更新界面的控件内容
            String value=data.getStringExtra("nickname");
            tvNickname.setText(value);
            userInfo.setNickname(value);
        }else if (requestCode==2){
            String value=data.getStringExtra("signature");
            tvSignature.setText(value);
            userInfo.setNickname(value);
        }
        //2.3保存到数据库
        service.modify(userInfo);
        //内部存储
        saveToInternal(userInfo);
        //外部私有存储
        savePrivateExStorage(userInfo);
    }

    private static final String FILE_NAME="userinfo.txt";
    //内部存储
    //保存
    private void saveToInternal(User userInfo){
        //内部存储目录：data/data/包名/files/
        try{
            //1.打开文件输出流
            FileOutputStream out=this.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            //2.创建BufferedWriter对象
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(out));
            //3.写入数据
            writer.write(JSON.toJSONString(userInfo));
            //4.关闭输出流
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    //读取
    private User readFromInternal(){
        User userInfo=null;
        try{
            FileInputStream in=this.openFileInput(FILE_NAME);
            BufferedReader reader=new BufferedReader(new InputStreamReader(in));
            String data=reader.readLine();
            userInfo= JSON.parseObject(data,User.class);
            reader.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return userInfo;
    }

    //外部私有存储
    private void savePrivateExStorage(User userInfo){
        //外部私有存储目录：/storage/emulated/0/Android/data/包名/files/
        try{
            //1.打开文件输出流
            File file=new File(getExternalFilesDir(""),FILE_NAME);
            FileOutputStream out=new FileOutputStream(file);
            //2.创建BufferedWriter对象
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(out));
            //3.写入数据
            writer.write(JSON.toJSONString(userInfo));
            //4.关闭输出流
            writer.close();
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    //读取
    private User readPrivateExStorage(){
        User userInfo=null;
        try{
            File file=new File(getExternalFilesDir(""),FILE_NAME);
            if(!file.exists()){
                return null;
            }
            FileInputStream in=new FileInputStream(file);
            BufferedReader reader=new BufferedReader(new InputStreamReader(in));
            String data=reader.readLine();
            userInfo= JSON.parseObject(data,User.class);
            reader.close();
            in.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return userInfo;
    }

    //Android 6.0的权限申请回调标识
    private static final int REQUEST_READ_USERINFO=101;
    private static final int REQUEST_WRITE_USERINFO=102;
    private void savePublicExStorage(User userInfo){
        //外部私有存储目录：/storage/emulated/0/
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE )!= PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_WRITE_USERINFO);
                return;
            }
        }
        saveUserInfo(userInfo);
    }

    private void saveUserInfo(User userInfo) {
        try{
            //1.打开文件输出流
            File file=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),FILE_NAME);
            FileOutputStream out=new FileOutputStream(file);
            //2.创建BufferedWriter对象
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(out));
            //3.写入数据
            writer.write(JSON.toJSONString(userInfo));
            writer.flush();
            //4.关闭输出流
            writer.close();
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //读取
    private User readPublicExStorage(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_READ_USERINFO);
                return null;
            }
        }
        return readUserInfo();
    }
    private User readUserInfo() {
        User userInfo = null;
        File file=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),FILE_NAME);
        try{
            FileInputStream in=new FileInputStream(file);
            int length=in.available();
            byte[] data=new byte[length];
            int len=in.read(data);
            userInfo= JSON.parseObject(data,User.class);
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(this,"读取失败",Toast.LENGTH_SHORT).show();
        }
        return userInfo;
    }

    //权限请求的回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //1.判断申请结果
        if(grantResults.length==0||grantResults[0]!=PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,"申请权限被拒绝，无法之星操作",Toast.LENGTH_SHORT).show();
            return;
        }
        //2.如果被允许，根据requestCode进行相应处理
        if(requestCode==REQUEST_READ_USERINFO){
            userInfo=readUserInfo();
        }else if(requestCode==REQUEST_WRITE_USERINFO){
            saveUserInfo(userInfo);
        }
    }

    private static final int PICK_IMAGE = 3;
    void choosePhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, PICK_IMAGE);
    }


//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//
//    }
}
