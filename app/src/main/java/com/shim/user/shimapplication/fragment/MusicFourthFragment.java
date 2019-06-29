package com.shim.user.shimapplication.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shim.user.shimapplication.R;
import com.shim.user.shimapplication.adapter.MusicAdapter;
import com.shim.user.shimapplication.data.Music;
import com.shim.user.shimapplication.data.MusicExtend;
import com.shim.user.shimapplication.data.handler.ShowMusicHandler;
import com.shim.user.shimapplication.data.repository.ShimRepo;

import java.util.ArrayList;
import java.util.List;

public class MusicFourthFragment extends Fragment {
    private RecyclerView musicFourthContainerView;
    public static MusicAdapter musicFourthAdapter;
    public static List<MusicExtend> musicFourthList = new ArrayList<>();

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
        musicFourthAdapter = new MusicAdapter(getContext(), musicFourthList, 4);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 2);
        musicFourthContainerView.setLayoutManager(manager);
        musicFourthContainerView.setAdapter(musicFourthAdapter);

        ShowMusicHandler showMusicHandler = new ShowMusicHandler() {
            @Override
            public void onSuccessShowMusic(List<Music> arr) {
                int remember = -1;
                boolean check=false;
                for(int i=0; i<musicFourthList.size(); i++){
                    if(musicFourthList.get(i).getButton_pushed()==1){
                        check=true;
                        remember = i;
                    }
                }
                musicFourthList.clear();
                for(int i=0; i<arr.size(); i++){
                    MusicExtend musicExtend = new MusicExtend();
                    musicExtend.setMusic_id(arr.get(i).getMusic_id());
                    musicExtend.setMusic_music(arr.get(i).getMusic_music());
                    musicExtend.setMusic_name(arr.get(i).getMusic_name());
                    musicExtend.setMusic_picture(arr.get(i).getMusic_picture());
                    if(i==remember&&check==true){
                        musicExtend.setButton_pushed(1);
                    }
                    musicFourthList.add(musicExtend);
                }
                musicFourthAdapter.setItem(musicFourthList);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        };
        shimRepo = new ShimRepo(showMusicHandler);

        shimRepo.showMusic("focus");

        return view;
    }

}
