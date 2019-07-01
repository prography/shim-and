package com.shim.user.shimapplication.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shim.user.shimapplication.R;
import com.shim.user.shimapplication.adapter.AudioAdapter;
import com.shim.user.shimapplication.data.Media.AudioApplication;
import com.shim.user.shimapplication.data.Media.BroadcastActions;
import com.shim.user.shimapplication.data.Music;

import static com.shim.user.shimapplication.activity.MainActivity.musicPlayList;

public class MusicListActivity extends AppCompatActivity {
    private RecyclerView musicListRecyclerview;
    private AudioAdapter musicListAdapter;

    ImageView musicPlayerImage;
    TextView musicPlayerTitle;
    ImageButton musicPlayerPlayBtn;
    ImageButton musicPlayerRewindBtn;
    ImageButton musicPlayerForwardBtn;

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateUI();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);


        musicPlayerImage = (ImageView)findViewById(R.id.player_music_image);
        musicPlayerTitle = (TextView)findViewById(R.id.player_music_title);
        musicPlayerPlayBtn = (ImageButton)findViewById(R.id.player_btn_play_pause);
        musicPlayerForwardBtn = (ImageButton)findViewById(R.id.player_btn_forward);
        musicPlayerRewindBtn = (ImageButton)findViewById(R.id.player_btn_rewind);

        musicPlayerRewindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioApplication.getInstance().getServiceInterface().setPlayList(musicPlayList);
                AudioApplication.getInstance().getServiceInterface().rewind();
            }
        });

        musicPlayerPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioApplication.getInstance().getServiceInterface().setPlayList(musicPlayList);
                AudioApplication.getInstance().getServiceInterface().togglePlay();
            }
        });

        musicPlayerForwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioApplication.getInstance().getServiceInterface().setPlayList(musicPlayList);
                AudioApplication.getInstance().getServiceInterface().forward();
            }
        });

        musicListRecyclerview=(RecyclerView)findViewById(R.id.music_list_recycler_view);
        musicListAdapter=new AudioAdapter(this, musicPlayList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        musicListRecyclerview.setLayoutManager(manager);
        musicListRecyclerview.setAdapter(musicListAdapter);

        registerBroadcast();
        updateUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterBroadcast();
    }

    public void registerBroadcast(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadcastActions.PLAY_STATE_CHANGED);
        registerReceiver(mBroadcastReceiver, filter);
    }

    public void unregisterBroadcast(){
        unregisterReceiver(mBroadcastReceiver);
    }

    private void updateUI(){
        if (AudioApplication.getInstance().getServiceInterface().isPlaying()
                && !AudioApplication.getInstance().getServiceInterface().getIsHomePlayed()) {
            musicPlayerPlayBtn.setImageResource(R.drawable.ic_pause);
        }else{
            musicPlayerPlayBtn.setImageResource(R.drawable.ic_play);
        }
        Music music = AudioApplication.getInstance().getServiceInterface().getMusic();
        if (music != null && !AudioApplication.getInstance().getServiceInterface().getIsHomePlayed()) {
            Glide.with(this).load("https://s3.ap-northeast-2.amazonaws.com/shim-music/"
                    +music.getMusic_picture()).into(musicPlayerImage);
            musicPlayerTitle.setText(music.getMusic_name());
        }else{
            musicPlayerImage.setImageResource(R.drawable.empty_albumart);
            musicPlayerTitle.setText("재생중인 음악이 없습니다");
        }
    }

}
