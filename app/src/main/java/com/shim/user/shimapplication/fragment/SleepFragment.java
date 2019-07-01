package com.shim.user.shimapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shim.user.shimapplication.R;
import com.shim.user.shimapplication.adapter.SleepAdapter;
import com.shim.user.shimapplication.data.Sleep;
import com.shim.user.shimapplication.data.handler.ShowSleepHandler;
import com.shim.user.shimapplication.data.repository.ShimRepo;

import java.util.ArrayList;
import java.util.List;

public class SleepFragment extends Fragment {
    private RecyclerView sleepContainerView;
    public static SleepAdapter sleepAdapter;
    public static List<Sleep> sleepExtendList = new ArrayList<>();

    ShimRepo shimRepo;

    public static SleepFragment newInstance(){
        return new SleepFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_sleep, container, false);


        sleepContainerView = (RecyclerView)view.findViewById(R.id.sleep_recycler_container);
        sleepAdapter = new SleepAdapter(getContext(), sleepExtendList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
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

        return view;
    }

}
