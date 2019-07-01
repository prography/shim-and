package com.shim.user.shimapplication.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shim.user.shimapplication.R;
import com.shim.user.shimapplication.adapter.VideoAdapter;
import com.shim.user.shimapplication.data.Video;
import com.shim.user.shimapplication.data.handler.ShowVideoHandler;
import com.shim.user.shimapplication.data.repository.ShimRepo;

import java.util.ArrayList;
import java.util.List;

public class VideoFirstFragment extends Fragment {
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


        videoFirstContainerView = (RecyclerView)view.findViewById(R.id.video_all_recycler_container);
        videoFirstContainerView.setHasFixedSize(true);
        videoFirstAdapter = new VideoAdapter(getContext(), videoFirstList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
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
