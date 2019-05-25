package com.example.user.shimapplication.adapter;

import android.content.Context;
import android.media.AudioManager;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.user.shimapplication.R;
import com.example.user.shimapplication.data.LogMusic;
import com.example.user.shimapplication.data.LogResponse;
import com.example.user.shimapplication.data.Music;
import com.example.user.shimapplication.data.MusicExtend;
import com.example.user.shimapplication.data.handler.LogMusicHandler;
import com.example.user.shimapplication.data.repository.LogRepo;

import java.io.IOException;
import java.util.List;

import static com.example.user.shimapplication.activity.MainActivity.changeButton;
import static com.example.user.shimapplication.activity.MainActivity.isPlaying;
import static com.example.user.shimapplication.activity.MainActivity.mp;
import static com.example.user.shimapplication.activity.MainActivity.playingIndex;
import static com.example.user.shimapplication.activity.MainActivity.playingPosition;
import static com.example.user.shimapplication.activity.MainActivity.userID;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {
    private List<MusicExtend> musicList;
    private Context context;
    private int category;

    private LogMusic logMusic;
    LogRepo logMusicRepo;

    public MusicAdapter(Context context, List<MusicExtend> musicList, int category){
        this.context = context;
        this.musicList = musicList;
        this.category = category;
    }


    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.music_player_layout, viewGroup, false);

        logMusic = new LogMusic();
        LogMusicHandler logMusicHandler = new LogMusicHandler() {
            @Override
            public void onSuccessLogMusic(LogResponse response) {

            }

            @Override
            public void onFailure(Throwable t) {

            }
        };
        logMusicRepo = new LogRepo(logMusicHandler);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final MusicExtend music = musicList.get(position);
        viewHolder.musicName.setText(music.getMusic_name());
        Glide.with(viewHolder.itemView.getContext())
                .load("https://s3.ap-northeast-2.amazonaws.com/shim-music/"
                +music.getMusic_picture())
                .into(viewHolder.musicImage);
        if(music.getButton_pushed()==1){
            viewHolder.musicBtn.setImageResource(R.drawable.ic_stop_small);
        }else{
            viewHolder.musicBtn.setImageResource(R.drawable.ic_play_small);
        }
        viewHolder.musicBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (music.getButton_pushed() == 0) {
                    mp.reset();
                    mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    try {
                        mp.setDataSource("https://s3.ap-northeast-2.amazonaws.com/" +
                                "shim-music/" + music.getMusic_music());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        mp.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mp.setLooping(true);
                    mp.start();
                    isPlaying=true;
                    viewHolder.musicBtn.setImageResource(R.drawable.ic_stop_small);
                    changeButton(category+1, position);
                    playingPosition=category+1;
                    playingIndex=position;

                    logMusic.setMusic_log_action(1);
                    logMusic.setMusic_log_user_id(userID);
                    logMusic.setMusic_log_music_id(music.getMusic_id());
                    logMusicRepo.logMusic(logMusic);
                }
                else{
                    mp.stop();
                    isPlaying = false;
                    playingPosition = -1;
                    playingIndex = -1;
                    musicList.get(position).setButton_pushed(0);
                    viewHolder.musicBtn.setImageResource(R.drawable.ic_play_small);

                    logMusic.setMusic_log_music_id(music.getMusic_id());
                    logMusic.setMusic_log_user_id(userID);
                    logMusic.setMusic_log_action(0);
                    logMusicRepo.logMusic(logMusic);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(musicList==null){
            return 0;
        }
        else{
            return musicList.size();
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView musicImage;
        TextView musicName;
        ImageButton musicBtn;

        ViewHolder(View itemView){
            super(itemView);
            musicImage = (ImageView)itemView.findViewById(R.id.music_picture);
            musicName = (TextView)itemView.findViewById(R.id.music_name);
            musicBtn = (ImageButton)itemView.findViewById(R.id.music_btn_play);
        }
    }

    public void setItem(List<MusicExtend> List){
        musicList = List;
        notifyDataSetChanged();
    }
}
