package com.shim.user.shimapplication.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.shim.user.shimapplication.R;
import com.shim.user.shimapplication.activity.MusicListActivity;
import com.shim.user.shimapplication.adapter.MusicAdapter;
import com.shim.user.shimapplication.data.Media.AudioApplication;
import com.shim.user.shimapplication.data.Media.BroadcastActions;
import com.shim.user.shimapplication.data.Music;
import com.shim.user.shimapplication.data.MusicExtend;
import com.shim.user.shimapplication.data.handler.ShowMusicHandler;
import com.shim.user.shimapplication.data.repository.ShimRepo;

import java.util.ArrayList;
import java.util.List;


public class MusicFirstFragment extends Fragment{
    private RecyclerView musicFirstContainerView;
    public static MusicAdapter musicFirstAdapter;
    public static List<MusicExtend> musicFirstList = new ArrayList<>();

    ShimRepo shimRepo;

    ImageButton playerCallButton;

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
        musicFirstAdapter = new MusicAdapter(getContext(), musicFirstList, 1);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 2);
        musicFirstContainerView.setLayoutManager(manager);
        musicFirstContainerView.setAdapter(musicFirstAdapter);

        playerCallButton = (ImageButton)view.findViewById(R.id.player_call_button);
        playerCallButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), MusicListActivity.class);
                startActivity(intent);
            }
        });

        ShowMusicHandler showMusicHandler = new ShowMusicHandler() {
            @Override
            public void onSuccessShowMusic(List<Music> arr) {
                int remember = -1;
                boolean check=false;
                for(int i=0; i<musicFirstList.size(); i++){
                    if(musicFirstList.get(i).getButton_pushed()==1){
                        check=true;
                        remember = i;
                    }
                }
                musicFirstList.clear();
                for(int i=0; i<arr.size(); i++){
                    MusicExtend musicExtend = new MusicExtend();
                    musicExtend.setMusic_id(arr.get(i).getMusic_id());
                    musicExtend.setMusic_music(arr.get(i).getMusic_music());
                    musicExtend.setMusic_name(arr.get(i).getMusic_name());
                    musicExtend.setMusic_picture(arr.get(i).getMusic_picture());
                    if(i==remember&&check==true){
                        musicExtend.setButton_pushed(1);
                    }
                    musicFirstList.add(musicExtend);
                }
                musicFirstAdapter.setItem(musicFirstList);
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
