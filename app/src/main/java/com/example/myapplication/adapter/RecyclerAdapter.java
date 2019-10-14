package com.example.myapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.entity.Exercise;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<Exercise> exercises;
    private Context context;

    public RecyclerAdapter(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        Exercise exercise=exercises.get(position);
//        holder.tvOrder.setText(String.valueOf(position+1));
//        holder.tvTitle.setText(exercise.getTitle());
//        holder.tvTotal.setText(exercise.getSubTitle());
//    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Exercise exercise=exercises.get(position);
        holder.tvOrder.setText(String.valueOf(exercise.getId()));
        holder.tvTitle.setText(exercise.getTitle());
        holder.tvTotal.setText(exercise.getSubTitle());

        //设置监听器
        if (itemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    itemClickListener.onItemClick(holder.itemView,position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v){
                    itemClickListener.onItemLongClick(holder.itemView,position);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvOrder,tvTitle,tvTotal;
        public ViewHolder(@Nullable View itemView){
            super(itemView);
            tvOrder=itemView.findViewById(R.id.tv_order);
            tvTitle=itemView.findViewById(R.id.tv_title);
            tvTotal=itemView.findViewById(R.id.tv_total);
        }
    }
    //回调事件的接口
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }
    //设置事件接口对象
    private OnItemClickListener itemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.itemClickListener=listener;
    }
}
