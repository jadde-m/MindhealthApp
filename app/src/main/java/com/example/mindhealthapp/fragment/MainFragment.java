package com.example.mindhealthapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mindhealthapp.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import cn.bmob.v3.Bmob;

public class MainFragment extends Fragment {

    private RecyclerView recyclerView;
    private SmartRefreshLayout smartRefreshLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*逻辑处理*/
        initView();

        Bmob.initialize(getActivity(),"d678f27fde3ba99626b41d550316f7ba");
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                Refresh();
            }
        });


    }

    private void Refresh() {


    }

    private void initView() {
        recyclerView = getActivity().findViewById(R.id.mainrecycleview);
        smartRefreshLayout = getActivity().findViewById(R.id.refresh);

    }
}
