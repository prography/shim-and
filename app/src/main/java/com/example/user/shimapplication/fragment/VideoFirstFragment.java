package com.example.user.shimapplication.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import java.util.ArrayList;
import java.util.List;

public class VideoFirstFragment extends YouTubePlayerSupportFragment {
    private RecyclerView videoFirstContainerView;
    private VideoAdapter videoFirstAdapter;
    private List<Video> videoFirstList = new ArrayList<>();

    ShimRepo shimRepo;


    public static VideoFirstFragment newInstance() {
        Bundle args = new Bundle();
        VideoFirstFragment fragment = new VideoFirstFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_first,container,false);

        YouTubePlayerSupportFragment youTubePlayerSupportFragment
                = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_view, youTubePlayerSupportFragment).commit();

                videoFirstContainerView = (RecyclerView)view.findViewById(R.id.video_all_recycler_container);
        videoFirstAdapter = new VideoAdapter(getContext(), videoFirstList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,false);
        videoFirstContainerView.setLayoutManager(manager);
        videoFirstContainerView.setAdapter(videoFirstAdapter);

        ShowVideoHandler showVideoHandler = new ShowVideoHandler() {
            @Override
            public void onSuccessShowVideo(List<Video> arr) {
                videoFirstAdapter.setItem(arr);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        };
        shimRepo = new ShimRepo(showVideoHandler);
        shimRepo.showVideo("all");

        return view;
    }

}
