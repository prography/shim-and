package com.example.user.shimapplication.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.shimapplication.R;
import com.example.user.shimapplication.adapter.MusicAdapter;
import com.example.user.shimapplication.data.Music;
import com.example.user.shimapplication.data.handler.ShowMusicHandler;
import com.example.user.shimapplication.data.repository.ShimRepo;

import java.util.ArrayList;
import java.util.List;


public class MusicSecondFragment extends Fragment {
    private RecyclerView musicSecondContainerView;
    private MusicAdapter musicSecondAdapter;
    private List<Music> musicSecondList = new ArrayList<>();
    ShimRepo shimRepo;

    public static MusicSecondFragment newInstance() {
        Bundle args = new Bundle();
        MusicSecondFragment fragment = new MusicSecondFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music_second, container, false);

        musicSecondContainerView = (RecyclerView)view.findViewById(R.id.music_sleep_recycler_container);
        musicSecondAdapter = new MusicAdapter(getContext(), musicSecondList);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 2);
        musicSecondContainerView.setLayoutManager(manager);
        musicSecondContainerView.setAdapter(musicSecondAdapter);

        ShowMusicHandler showMusicHandler = new ShowMusicHandler() {
            @Override
            public void onSuccessShowMusic(List<Music> arr) {
                musicSecondAdapter.setItem(arr);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        };
        shimRepo = new ShimRepo(showMusicHandler);

        shimRepo.showMusic("sleep");

        return view;
    }

}
