package com.example.user.shimapplication.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.shimapplication.R;
import com.example.user.shimapplication.adapter.VideoAdapter;
import com.example.user.shimapplication.data.Video;
import com.example.user.shimapplication.data.handler.ShowVideoHandler;
import com.example.user.shimapplication.data.repository.ShimRepo;

import java.util.ArrayList;
import java.util.List;


public class VideoSecondFragment extends Fragment {
    private RecyclerView videoSecondContainerView;
    private VideoAdapter videoSecondAdapter;
    private List<Video> videoSecondList = new ArrayList<>();

    ShimRepo shimRepo;

    public static VideoSecondFragment newInstance() {
        Bundle args = new Bundle();
        VideoSecondFragment fragment = new VideoSecondFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_second,container,false);


        videoSecondContainerView = (RecyclerView)view.findViewById(R.id.video_baby_recycler_container);
        videoSecondContainerView.setHasFixedSize(true);
        videoSecondAdapter = new VideoAdapter(getContext(), videoSecondList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,false);
        videoSecondContainerView.setLayoutManager(manager);
        videoSecondContainerView.setAdapter(videoSecondAdapter);

        ShowVideoHandler showVideoHandler = new ShowVideoHandler() {
            @Override
            public void onSuccessShowVideo(List<Video> arr) {
                videoSecondAdapter.setItem(arr);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        };
        shimRepo = new ShimRepo(showVideoHandler);
        shimRepo.showVideo("baby");

        return view;
    }
}
