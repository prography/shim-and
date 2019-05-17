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
import com.example.user.shimapplication.data.MusicExtend;
import com.example.user.shimapplication.data.handler.ShowMusicHandler;
import com.example.user.shimapplication.data.repository.ShimRepo;

import java.util.ArrayList;
import java.util.List;

public class MusicThirdFragment extends Fragment {
    private RecyclerView musicThirdContainerView;
    public static MusicAdapter musicThirdAdapter;
    public static List<MusicExtend> musicThirdList = new ArrayList<>();

    ShimRepo shimRepo;

    public static MusicThirdFragment newInstance() {
        Bundle args = new Bundle();
        MusicThirdFragment fragment = new MusicThirdFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music_third, container, false);

        musicThirdContainerView = (RecyclerView)view.findViewById(R.id.music_instrument_recycler_container);
        musicThirdAdapter = new MusicAdapter(getContext(), musicThirdList, 3);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 2);
        musicThirdContainerView.setLayoutManager(manager);
        musicThirdContainerView.setAdapter(musicThirdAdapter);

        ShowMusicHandler showMusicHandler = new ShowMusicHandler() {
            @Override
            public void onSuccessShowMusic(List<Music> arr) {
                int remember = -1;
                boolean check=false;
                for(int i=0; i<musicThirdList.size(); i++){
                    if(musicThirdList.get(i).getButton_pushed()==1){
                        check=true;
                        remember = i;
                    }
                }
                musicThirdList.clear();
                for(int i=0; i<arr.size(); i++){
                    MusicExtend musicExtend = new MusicExtend();
                    musicExtend.setMusic_id(arr.get(i).getMusic_id());
                    musicExtend.setMusic_music(arr.get(i).getMusic_music());
                    musicExtend.setMusic_name(arr.get(i).getMusic_name());
                    musicExtend.setMusic_picture(arr.get(i).getMusic_picture());
                    if(i==remember&&check==true){
                        musicExtend.setButton_pushed(1);
                    }
                    musicThirdList.add(musicExtend);
                }
                musicThirdAdapter.setItem(musicThirdList);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        };
        shimRepo = new ShimRepo(showMusicHandler);

        shimRepo.showMusic("instrument");

        return view;

    }

}
