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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.user.shimapplication.R;
import com.example.user.shimapplication.data.Music;

import java.io.IOException;
import java.util.List;

import static com.example.user.shimapplication.activity.MainActivity.mp;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {
    private List<Music> musicList;
    private Context context;

    public MusicAdapter(Context context, List<Music> musicList){
        this.context = context;
        this.musicList = musicList;
    }


    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.music_player_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final Music music = musicList.get(position);
        viewHolder.musicName.setText(music.getMusic_name());
        Glide.with(viewHolder.itemView.getContext())
                .load("https://s3.ap-northeast-2.amazonaws.com/shim-music/"
                +music.getMusic_picture())
                .into(viewHolder.musicImage);
        viewHolder.musicPlayBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mp.reset();
                mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mp.setDataSource("https://s3.ap-northeast-2.amazonaws.com/" +
                            "shim-music/"+music.getMusic_music());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    mp.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mp.start();
            }
        });
        viewHolder.musicStopBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mp.stop();
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
        Button musicPlayBtn;
        Button musicStopBtn;

        ViewHolder(View itemView){
            super(itemView);
            musicImage = (ImageView)itemView.findViewById(R.id.music_picture);
            musicName = (TextView)itemView.findViewById(R.id.music_name);
            musicPlayBtn = (Button)itemView.findViewById(R.id.music_btn_play);
            musicStopBtn = (Button)itemView.findViewById(R.id.music_btn_stop);
        }
    }

    public void setItem(List<Music> List){
        musicList = List;
        notifyDataSetChanged();
    }
}
