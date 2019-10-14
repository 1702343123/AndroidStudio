package com.example.myapplication.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class AdViewPagerAdapter extends PagerAdapter {
    private List<ImageView> imageViews;

    public AdViewPagerAdapter(){
        this(null);
        imageViews = new ArrayList<>();
    }

    public AdViewPagerAdapter(List<ImageView> imageViews){
        super();
        this.imageViews = imageViews;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    //防止刷新时显示缓存数据
    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    //销毁条目
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //warning ：不要在这里removeView
    }

    /*
     * 返回数据集的真实容量大小
     * */
    public int getSize(){
        return imageViews.size();
    }
//指定复用的判断逻辑，固定写法：view==object
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //container容器相当于用来存放imageView
        //从集合中获取图片
        ImageView imageView=imageViews.get(position % imageViews.size());
        //检查imageView是否已经添加到容器中
        ViewParent parent=imageView.getParent();
        if(parent!=null){
            ((ViewGroup) parent).removeView(imageView);
        }
        //把图片添加到container中
        container.addView(imageView);
        //把图片返回给框架。用来缓存
        return imageView;
    }

}
