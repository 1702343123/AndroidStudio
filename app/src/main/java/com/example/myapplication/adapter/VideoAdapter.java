package com.example.myapplication.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.entity.Video;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private List<Video> videos;

    private OnItemClickListener itemClickListener;

    public VideoAdapter(List<Video> videos) {
        this.videos = videos;
//        this.selected = -1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Video video = videos.get(position);
        holder.ivIcon.setImageResource(R.drawable.play);
        holder.tvTitle.setText(video.getVideoTitle());

        // 改变选中项的图标和文本颜色
        if(selected == position) {
            holder.ivIcon.setImageResource(R.drawable.play2);
            holder.tvTitle.setTextColor(Color.parseColor("#009958"));
        } else {
            holder.ivIcon.setImageResource(R.drawable.play);
            holder.tvTitle.setTextColor(Color.parseColor("#333333"));
//            holder.tvTitle.setTextColor(context.getResources().getColor(R.color.cardview_dark_background));
        }

        // 设置选项监听
        if (itemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    private int selected=-1;//选中项的序号
    public void setSelected(int selected) {
        this.selected = selected;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView ivIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            ivIcon = itemView.findViewById(R.id.iv_play);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
