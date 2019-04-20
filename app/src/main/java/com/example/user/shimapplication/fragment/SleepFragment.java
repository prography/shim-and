package com.example.user.shimapplication.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.user.shimapplication.R;
import com.example.user.shimapplication.activity.MainActivity;
import com.example.user.shimapplication.adapter.SleepAdapter;
import com.example.user.shimapplication.data.ShowSleepResponse;
import com.example.user.shimapplication.data.Sleep;
import com.example.user.shimapplication.data.handler.ShowSleepHandler;
import com.example.user.shimapplication.data.repository.ShimRepo;

import java.util.ArrayList;
import java.util.List;

public class SleepFragment extends Fragment {
    private RecyclerView sleepContainerView;
    private SleepAdapter sleepAdapter;
    private List<Sleep> sleepList = new ArrayList<>();

    ShimRepo shimRepo;

    public static SleepFragment newInstance(){
        return new SleepFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_sleep, container, false);

        sleepContainerView = (RecyclerView)view.findViewById(R.id.sleep_recycler_container);
        sleepAdapter = new SleepAdapter(getContext(), sleepList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        sleepContainerView.setLayoutManager(manager);
        sleepContainerView.setAdapter(sleepAdapter);

        ShowSleepHandler showSleepHandler = new ShowSleepHandler() {
            @Override
            public void onSuccessShowSleep(List<Sleep> arr) {
                    sleepAdapter.setItem(arr);
            }

            @Override
            public void onFailure(Throwable t) {
                // 에러 확인 대책 추가 필요
            }
        };
        shimRepo = new ShimRepo(showSleepHandler);

        shimRepo.showSleep();
        // List Item 추가 및 Adapter Setting

        return view;
    }
}
