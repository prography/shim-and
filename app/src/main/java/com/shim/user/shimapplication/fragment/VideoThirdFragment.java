package com.shim.user.shimapplication.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shim.user.shimapplication.R;
import com.shim.user.shimapplication.adapter.VideoAdapter;
import com.shim.user.shimapplication.data.Video;
import com.shim.user.shimapplication.data.handler.ShowVideoHandler;
import com.shim.user.shimapplication.data.repository.ShimRepo;

import java.util.ArrayList;
import java.util.List;

public class VideoThirdFragment extends Fragment {
    private RecyclerView videoThirdContainerView;
    private VideoAdapter videoThirdAdapter;
    private List<Video> videoThirdList = new ArrayList<>();

    ShimRepo shimRepo;

    public static VideoThirdFragment newInstance() {
        Bundle args = new Bundle();
        VideoThirdFragment fragment = new VideoThirdFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_third,container,false);


        videoThirdContainerView = (RecyclerView)view.findViewById(R.id.video_animal_recycler_container);
        videoThirdContainerView.setHasFixedSize(true);
        videoThirdAdapter = new VideoAdapter(getContext(), videoThirdList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,false);
        videoThirdContainerView.setLayoutManager(manager);
        videoThirdContainerView.setAdapter(videoThirdAdapter);

        ShowVideoHandler showVideoHandler = new ShowVideoHandler() {
            @Override
            public void onSuccessShowVideo(List<Video> arr) {
                videoThirdAdapter.setItem(arr);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        };
        shimRepo = new ShimRepo(showVideoHandler);
        shimRepo.showVideo("animal");

        return view;
    }

}
