package com.example.user.shimapplication.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicFirstFragment extends Fragment {
    private RecyclerView musicFirstContainerView;
    private MusicAdapter musicFirstAdapter;
    private List<Music> musicFirstList = new ArrayList<>();

    ShimRepo shimRepo;


    public static MusicFirstFragment newInstance() {
        Bundle args = new Bundle();
        MusicFirstFragment fragment = new MusicFirstFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music_first, container, false);

        musicFirstContainerView = (RecyclerView)view.findViewById(R.id.music_all_recycler_container);
        musicFirstAdapter = new MusicAdapter(getContext(), musicFirstList);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 2);
        musicFirstContainerView.setLayoutManager(manager);
        musicFirstContainerView.setAdapter(musicFirstAdapter);

        ShowMusicHandler showMusicHandler = new ShowMusicHandler() {
            @Override
            public void onSuccessShowMusic(List<Music> arr) {
                musicFirstAdapter.setItem(arr);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        };
        shimRepo = new ShimRepo(showMusicHandler);

        shimRepo.showMusic("all");

        return view;
    }

}
