package com.example.myapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.activity.UserInfoActivity;
import com.example.myapplication.adapter.ExerciseAdapter;
import com.example.myapplication.entity.Exercise;
import com.example.myapplication.utils.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class BlankFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "param";

    private String mParam;
    private ListView lvExercise;

    private OnFragmentInteractionListener mListener;

    public BlankFragment() {
        // Required empty public constructor
    }

    public static BlankFragment newInstance(String param) {
        BlankFragment fragment = new BlankFragment();
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

    String[] data={"软件1721","软件1711","软件1731","软件1741","软件1751","软件1761"};
    List<Exercise> exercises;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_blank,container,false);
        //准备的列表
        initData();
        //1.获取列表控件
        lvExercise=view.findViewById(R.id.list_view);
        //2创建合类控件需要的Adapter数据配适器(作用：UI与ArrayLt数据的桥梁)
//        ArrayAdapter<String> adapter=new ArrayAdapter<String>(
//                getActivity(),android.R.layout.simple_list_item_1,data);
        ExerciseAdapter adapter=new ExerciseAdapter(getActivity(),exercises);
        //3.设置ListView的Adapter

        lvExercise.setAdapter(adapter);
        // 4. ListView中的item的事件监听
        lvExercise.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Exercise exercise = (Exercise) parent.getItemAtPosition(position);
                Intent intent = new Intent(BlankFragment.this.getContext(), UserInfoActivity.class);
                intent.putExtra("id", exercise.getId());
                intent.putExtra("title", exercise.getTitle());
                BlankFragment.this.startActivity(intent);
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

    private void initData(){
        exercises=new ArrayList<>();

        try{
            //1.从assets目录中获取资源的输入流
            InputStream input=getResources().getAssets().open("exercise_title.json");
            //2.将Inputstream转为String
            String json= IOUtils.convert(input, StandardCharsets.UTF_8);
            //3.利用fastjson将字符串转为字符集
            exercises=IOUtils.convert(json,Exercise.class);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;

        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
