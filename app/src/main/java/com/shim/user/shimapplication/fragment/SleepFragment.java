package com.shim.user.shimapplication.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.shim.user.shimapplication.R;
import com.shim.user.shimapplication.activity.MainActivity;
import com.shim.user.shimapplication.adapter.SleepAdapter;
import com.shim.user.shimapplication.data.LogResponse;
import com.shim.user.shimapplication.data.LogSleep;
import com.shim.user.shimapplication.data.ShowSleepResponse;
import com.shim.user.shimapplication.data.Sleep;
import com.shim.user.shimapplication.data.SleepExtend;
import com.shim.user.shimapplication.data.handler.LogSleepHandler;
import com.shim.user.shimapplication.data.handler.ShowSleepHandler;
import com.shim.user.shimapplication.data.repository.LogRepo;
import com.shim.user.shimapplication.data.repository.ShimRepo;

import java.util.ArrayList;
import java.util.List;

public class SleepFragment extends Fragment {
    private RecyclerView sleepContainerView;
    public static SleepAdapter sleepAdapter;
    public static List<SleepExtend> sleepExtendList = new ArrayList<>();

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
                LinearLayoutManager.VERTICAL, false);
        sleepContainerView.setLayoutManager(manager);
        sleepContainerView.setAdapter(sleepAdapter);

        ShowSleepHandler showSleepHandler = new ShowSleepHandler() {
            @Override
            public void onSuccessShowSleep(List<Sleep> arr) {
                int remember = -1;
                boolean check=false;
                for(int i=0; i<sleepExtendList.size(); i++){
                    if(sleepExtendList.get(i).getButton_pushed()==1){
                        check=true;
                        remember = i;
                    }
                }
                sleepExtendList.clear();
                for(int i=0; i<arr.size(); i++){
                    SleepExtend sleepExtend = new SleepExtend();
                    sleepExtend.setSleep_id(arr.get(i).getSleep_id());
                    sleepExtend.setSleep_music(arr.get(i).getSleep_music());
                    sleepExtend.setSleep_name(arr.get(i).getSleep_name());
                    sleepExtend.setButton_pushed(0);
                    if(i==remember&&check==true){
                        sleepExtend.setButton_pushed(1);
                    }
                    sleepExtendList.add(sleepExtend);
                }
                sleepAdapter.setItem(sleepExtendList);
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
