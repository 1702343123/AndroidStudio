package com.example.myapplication.activity;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.VideoAdapter;
import com.example.myapplication.entity.Course;
import com.example.myapplication.entity.Video;
import com.example.myapplication.utils.IOUtils;
import com.example.myapplication.utils.SharedUtils;
import com.example.myapplication.utils.StatusUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CourseVideoActivity extends AppCompatActivity {

    private VideoView videoView;//视频播放器
    private ImageView ivVideo;//视频第一帧图像显示
    private TextView tvIntro;//课程介绍，来自上个页面的Course对象的intro属性
    private RecyclerView rvVideo;//视频列表

    //数据对象
    private Course course;
    private List<Video> videos;
    private VideoAdapter adapter;

    private MediaController controller;  //多媒体播放进度条控制

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusUtils.setImmersionMode(this);
        setContentView(R.layout.activity_course_video);

        StatusUtils.initToolbar(this, "视频资源", true, false);

        //1.获取初始化界面的数据
        initData();
        //2.初始化界面的控件，并填充数据
        initView();
        loadFirstFrame();
    }

    // 加载视频的首帧图像
    private void  loadFirstFrame() {
        Bitmap bitmap = null;

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(this, Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video101));
        bitmap = retriever.getFrameAtTime();
        ivVideo.setImageBitmap(bitmap);
    }

    private void initView() {
        videoView = findViewById(R.id.video_view);
        videoView.setMediaController(new MediaController(this));
        controller = new MediaController(this);
        videoView.setMediaController(controller);

        ivVideo = findViewById(R.id.iv_video);
        tvIntro = findViewById(R.id.tv_intro);
        rvVideo = findViewById(R.id.rv_video);

        tvIntro.setText(course.getIntro());

        adapter=new VideoAdapter(videos);
        rvVideo.setLayoutManager(new LinearLayoutManager(this));
        rvVideo.setAdapter(adapter);

        adapter.setOnItemClickListener(new VideoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // 设置选中项，并通过notifyDataSetChanged()更新UI
                adapter.setSelected(position);
                adapter.notifyDataSetChanged();//更新UI

                //获取Video对向的数据，并初始化VideoView
                Video video = videos.get(position);
                if(videoView.isPlaying()) {
                    videoView.stopPlayback();
                }

                if(TextUtils.isEmpty(video.getVideoPath())) {
                    Toast.makeText(CourseVideoActivity.this, "本地没有此视频，暂时无法播放", Toast.LENGTH_SHORT).show();
                    return;
                }
                //播放视频
                videoView.setVisibility(View.VISIBLE);
                ivVideo.setVisibility(View.GONE);
//                play();
                String uri = "android.resource://" + getPackageName() + "/" + R.raw.video101;
                videoView.setVideoPath(uri);
                videoView.start();
                //存储播放历史到数据库
//                if(SharedUtils.isLogin(CourseVideoActivity.this, "isLogin")) {
//                    String username = SharedUtils.readValue(CourseVideoActivity.this, "loginUser");
//                    PlayListDao.getInstance(CourseVideoActivity.this).save(video, username);
//                }
            }
        });
    }

    // 播放视频
//    private void play() {
//        String uri = "android.resource://" + getPackageName() + "/" + R.raw.video101;
//        videoView.setVideoPath(uri);
//        videoView.start();
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(videoView != null) {
            videoView.stopPlayback();
            videoView = null;
        }
    }

    private void initData() {
        //1.接收从上个页面传递的bundle对象
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            course = (Course) bundle.getSerializable("course");
        }

        //2.从json文件中获取视频的描述数据
        videos = new ArrayList<>();
        try {
            //2.1获取json文件中所有数据的集合
            InputStream is = getResources().getAssets().open("course.json");
            String json = IOUtils.convert(is, StandardCharsets.UTF_8);
            videos = IOUtils.convert(json, Video.class);

            //2.2筛选出course的id对应的视频集合
            Iterator<Video> it = videos.iterator();
            while(it.hasNext()) {
                Video video = it.next();
                if(video.getChapterId() != course.getId()) {
                    it.remove();
                }
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
