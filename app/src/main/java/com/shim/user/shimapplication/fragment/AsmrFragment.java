package com.shim.user.shimapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shim.user.shimapplication.R;
import com.shim.user.shimapplication.adapter.AsmrAdapter;
import com.shim.user.shimapplication.data.Sleep;
import com.shim.user.shimapplication.data.handler.ShowSleepHandler;
import com.shim.user.shimapplication.data.repository.ShimRepo;

import java.util.ArrayList;
import java.util.List;

public class AsmrFragment extends Fragment {
    private RecyclerView sleepContainerView;
    public static AsmrAdapter asmrAdapter;
    public static List<Sleep> sleepExtendList = new ArrayList<>();

    ShimRepo shimRepo;

    public static AsmrFragment newInstance(){
        return new AsmrFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_asmr, container, false);


        sleepContainerView = (RecyclerView)view.findViewById(R.id.sleep_recycler_container);
        asmrAdapter = new AsmrAdapter(getContext(), sleepExtendList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        sleepContainerView.setLayoutManager(manager);
        sleepContainerView.setAdapter(asmrAdapter);

        ShowSleepHandler showSleepHandler = new ShowSleepHandler() {
            @Override
            public void onSuccessShowSleep(List<Sleep> arr) {
                asmrAdapter.setItem(arr);
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
