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

public class MusicFourthFragment extends Fragment {
    private RecyclerView musicFourthContainerView;
    private MusicAdapter musicFourthAdapter;
    private List<Music> musicFourthList = new ArrayList<>();

    ShimRepo shimRepo;

    public static MusicFourthFragment newInstance() {
        Bundle args = new Bundle();
        MusicFourthFragment fragment = new MusicFourthFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music_fourth, container, false);

        musicFourthContainerView = (RecyclerView)view.findViewById(R.id.music_nature_recycler_container);
        musicFourthAdapter = new MusicAdapter(getContext(), musicFourthList);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 2);
        musicFourthContainerView.setLayoutManager(manager);
        musicFourthContainerView.setAdapter(musicFourthAdapter);

        ShowMusicHandler showMusicHandler = new ShowMusicHandler() {
            @Override
            public void onSuccessShowMusic(List<Music> arr) {
                musicFourthAdapter.setItem(arr);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        };
        shimRepo = new ShimRepo(showMusicHandler);

        shimRepo.showMusic("nature");

        return view;
    }

}
