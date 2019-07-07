package com.shim.user.shimapplication.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    public ImageView playerImage;
    public TextView playerTitle;
    public ImageButton playerPlayBtn;
    public ImageButton playerRewindBtn;
    public ImageButton playerForwardBtn;

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

        playerImage = (ImageView) findViewById(R.id.player_music_image);
        playerTitle = (TextView) findViewById(R.id.player_music_title);
        playerPlayBtn = (ImageButton) findViewById(R.id.player_btn_play_pause);
        playerForwardBtn = (ImageButton) findViewById(R.id.player_btn_forward);
        playerRewindBtn = (ImageButton) findViewById(R.id.player_btn_rewind);

        playerRewindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioApplication.getInstance().getServiceInterface().setPlayList(musicPlayList);
                AudioApplication.getInstance().getServiceInterface().rewind();
            }
        });

        playerPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioApplication.getInstance().getServiceInterface().setPlayList(musicPlayList);
                AudioApplication.getInstance().getServiceInterface().togglePlay();
            }
        });

        playerForwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioApplication.getInstance().getServiceInterface().setPlayList(musicPlayList);
                AudioApplication.getInstance().getServiceInterface().forward();
            }
        });

        musicListRecyclerview=(RecyclerView)findViewById(R.id.music_list_recycler_view);
        musicListAdapter=new AudioAdapter(this, musicPlayList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false);
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

    public void updateUI() {
        Music music = AudioApplication.getInstance().getServiceInterface().getMusic();
        if (music != null && !AudioApplication.getInstance().getServiceInterface().getIsHomePlayed()) {
            Glide.with(this).load(music.getMusic_picture()).into(playerImage);
            playerTitle.setText(music.getMusic_name());
        }else{
            playerImage.setImageResource(R.drawable.empty_albumart);
            playerTitle.setText("재생중인 음악이 없습니다");
        }

        if (AudioApplication.getInstance().getServiceInterface().isPlaying()
                && !AudioApplication.getInstance().getServiceInterface().getIsHomePlayed()) {
            playerPlayBtn.setImageResource(R.drawable.ic_pause);
        } else {
            playerPlayBtn.setImageResource(R.drawable.ic_play);
        }
    }
}
