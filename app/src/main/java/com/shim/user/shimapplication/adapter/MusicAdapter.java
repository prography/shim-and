package com.shim.user.shimapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shim.user.shimapplication.R;
import com.shim.user.shimapplication.data.LogMusic;
import com.shim.user.shimapplication.data.LogResponse;
import com.shim.user.shimapplication.data.Media.AudioApplication;
import com.shim.user.shimapplication.data.Music;
import com.shim.user.shimapplication.data.handler.LogMusicHandler;
import com.shim.user.shimapplication.data.repository.LogRepo;

import java.util.ArrayList;
import java.util.List;

import static com.shim.user.shimapplication.activity.MainActivity.musicPlayList;
import static com.shim.user.shimapplication.fragment.HomeFragment.isOtherMusicPlayed;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {
    LogRepo logMusicRepo;
    private List<Music> musicList;
    private Context context;
    private int category;
    private List<Music> forHomeCheckList = new ArrayList<>();
    private LogMusic logMusic;


    public MusicAdapter(Context context, List<Music> musicList, int category) {
        this.context = context;
        this.musicList = musicList;
        this.category = category;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_music_card, viewGroup, false);

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
        final Music music = musicList.get(position);
        viewHolder.musicName.setText(music.getMusic_name());
        Glide.with(viewHolder.itemView.getContext())
                .load("https://s3.ap-northeast-2.amazonaws.com/shim-music/"
                        + music.getMusic_picture())
                .into(viewHolder.musicImage);

        viewHolder.musicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Music addingMusic = new Music(music.getMusic_id(), music.getMusic_name(),
                        "https://s3.ap-northeast-2.amazonaws.com/" +
                                "shim-music/" + music.getMusic_music(),
                        null, music.getMusic_picture(), music.isMusic_my());
                musicPlayList.add(addingMusic);
                if (AudioApplication.getInstance().getServiceInterface().getIsHomePlayed() == true) {
                    AudioApplication.getInstance().getServiceInterface().stop();
                    AudioApplication.getInstance().getServiceInterface().setPlayList((ArrayList<Music>) forHomeCheckList);
                    AudioApplication.getInstance().getServiceInterface().setIsHoemPlayed(false);
                    isOtherMusicPlayed = true;
                }
                AudioApplication.getInstance().getServiceInterface().setPlayList(musicPlayList);
                AudioApplication.getInstance().getServiceInterface().play(musicPlayList.size() - 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (musicList == null) {
            return 0;
        } else {
            return musicList.size();
        }
    }

    public void setItem(List<Music> List) {
        musicList = List;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView musicImage;
        TextView musicName;
        ImageButton musicBtn;

        ViewHolder(View itemView) {
            super(itemView);
            musicImage = itemView.findViewById(R.id.image_music_thumbnail);
            musicName = itemView.findViewById(R.id.text_music_title);
            musicName.setOnClickListener(view -> view.setSelected(!view.isSelected()));
            musicBtn = itemView.findViewById(R.id.button_add_playlist);
        }
    }

}
