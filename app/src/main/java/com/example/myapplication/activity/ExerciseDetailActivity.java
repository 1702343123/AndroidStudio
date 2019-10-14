package com.example.myapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ExerciseAdapter;
import com.example.myapplication.adapter.ExerciseDetailAdapter;
import com.example.myapplication.entity.ExerciseDetail;
import com.example.myapplication.utils.IOUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExerciseDetailActivity extends AppCompatActivity implements ExerciseDetailAdapter.OnSelectListener {
    //获取ExerciseFragment传来的数据
    private int id;
    private String title;
    //从xml文件中获取
    private List<ExerciseDetail> details;
    //控件及Adapter
    private RecyclerView lvDetail;
    private ExerciseAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail);

        initData();
//        initView();

    }
    private void initData(){
        //1.获取从ExerciseFragment界面传递过来的章节id和章节标题
        id=getIntent().getIntExtra("id",0);
        title=getIntent().getStringExtra("title");
        //2.从xml获取习题内容
        details=new ArrayList<>();
        try{
            InputStream is=getResources().getAssets().open("chapter"+id+".xml");
            //pull方式读取xml
            details= IOUtils.getXmlContents(is);
            //sax方式读取
//            details=IOUtils.getXmlBySAX(is)
            is.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onSelectA(int position, ImageView ivA, ImageView ivB, ImageView ivC, ImageView ivD) {
        ExerciseDetail detail=details.get(position);
        //判断答案不是1，则为A选项
        if(detail.getAnswer()!=1){
            detail.setSelect(1);
        }else{
            detail.setSelect(0);
        }
        switch(detail.getAnswer()){
            case 1:
                ivA.setImageResource(R.drawable.right);
                break;
            case 2:
                ivB.setImageResource(R.drawable.right);
                ivA.setImageResource(R.drawable.error);
                break;
            case 3:
                ivC.setImageResource(R.drawable.right);
                ivA.setImageResource(R.drawable.error);
                break;
            case 4:
                ivD.setImageResource(R.drawable.right);
                ivA.setImageResource(R.drawable.error);
                break;
        }
    }

    @Override
    public void onSelectB(int position, ImageView ivA, ImageView ivB, ImageView ivC, ImageView ivD) {
        ExerciseDetail detail=details.get(position);
        //判断答案不是1，则为A选项
        if(detail.getAnswer()!=1){
            detail.setSelect(1);
        }else{
            detail.setSelect(0);
        }
        switch(detail.getAnswer()){
            case 1:
                ivA.setImageResource(R.drawable.right);
                ivB.setImageResource(R.drawable.error);
                break;
            case 2:
                ivB.setImageResource(R.drawable.right);
                break;
            case 3:
                ivC.setImageResource(R.drawable.right);
                ivB.setImageResource(R.drawable.error);
                break;
            case 4:
                ivD.setImageResource(R.drawable.right);
                ivB.setImageResource(R.drawable.error);
                break;
        }
    }

    @Override
    public void onSelectC(int position, ImageView ivA, ImageView ivB, ImageView ivC, ImageView ivD) {
        ExerciseDetail detail=details.get(position);
        //判断答案不是1，则为A选项
        if(detail.getAnswer()!=1){
            detail.setSelect(1);
        }else{
            detail.setSelect(0);
        }
        switch(detail.getAnswer()){
            case 1:
                ivA.setImageResource(R.drawable.right);
                ivC.setImageResource(R.drawable.error);
                break;
            case 2:
                ivB.setImageResource(R.drawable.right);
                ivC.setImageResource(R.drawable.error);
                break;
            case 3:
                ivC.setImageResource(R.drawable.right);
                break;
            case 4:
                ivD.setImageResource(R.drawable.right);
                ivC.setImageResource(R.drawable.error);
                break;
        }
    }

    @Override
    public void onSelectD(int position, ImageView ivA, ImageView ivB, ImageView ivC, ImageView ivD) {
        ExerciseDetail detail=details.get(position);
        //判断答案不是1，则为A选项
        if(detail.getAnswer()!=1){
            detail.setSelect(1);
        }else{
            detail.setSelect(0);
        }
        switch(detail.getAnswer()){
            case 1:
                ivA.setImageResource(R.drawable.right);
                ivD.setImageResource(R.drawable.error);
                break;
            case 2:
                ivB.setImageResource(R.drawable.right);
                ivD.setImageResource(R.drawable.error);
                break;
            case 3:
                ivC.setImageResource(R.drawable.right);
                ivD.setImageResource(R.drawable.error);
                break;
            case 4:
                ivD.setImageResource(R.drawable.right);
                break;
        }
    }

}
