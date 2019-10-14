package com.example.myapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.activity.ExerciseDetailActivity;
import com.example.myapplication.adapter.RecyclerAdapter;
import com.example.myapplication.entity.Exercise;
import com.example.myapplication.utils.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class RecyclerViewFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "param";
    private String mParam;
//    private ListView lvExercise;
    private List<Exercise> exercises;

    private OnFragmentInteractionListener mListener;

    public RecyclerViewFragment() {
        // Required empty public constructor
    }

    public static RecyclerViewFragment newInstance(String param) {
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getString(ARG_PARAM);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 1.初始化数据
        initData();
        //2.获取控件
        View view=inflater.inflate(R.layout.fragment_blank,container,false);
        RecyclerView recyclerView=view.findViewById(R.id.recycler_view);
        //3.设置布局和分割线
        LinearLayoutManager manager=new LinearLayoutManager(container.getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(container.getContext(),DividerItemDecoration.VERTICAL));
        //4.创建适配器
        RecyclerAdapter adapter=new RecyclerAdapter(exercises);
        //5.创建适配器
        recyclerView.setAdapter(adapter);

        //6.设置监听器
        adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Exercise exercise=exercises.get(position);

                //跳转到相应的章节习题
                Intent intent=new Intent(getContext(), ExerciseDetailActivity.class);
                intent.putExtra("id",exercise.getId());//用于识别是哪个xm文件
                intent.putExtra("title",exercise.getTitle());//用于设置详情的标题栏
                getContext().startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    private void initData(){
        exercises=new ArrayList<>();

        try{
            //1.从assets目录中获取资源的输入流
            InputStream input=getResources().getAssets().open("exercise_title.json");
            //2.将Inputstream转为String
            String json= IOUtils.convert(input, StandardCharsets.UTF_8);
            //3.利用fastjson将字符串转为字符集
            exercises=IOUtils.convert(json, Exercise.class);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
