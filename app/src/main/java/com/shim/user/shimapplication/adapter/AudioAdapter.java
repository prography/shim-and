package com.shim.user.shimapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shim.user.shimapplication.R;
import com.shim.user.shimapplication.data.Music;

import java.util.List;

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.ViewHolder>{
    private List<Music> musicList;
    private Context context;

    public AudioAdapter(Context context, List<Music> musicList){
        this.context=context;
        this.musicList=musicList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.listitem_audio, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AudioAdapter.ViewHolder viewHolder, int position) {
        final Music music = musicList.get(position);
        Glide.with(viewHolder.itemView.getContext())
                .load("https://s3.ap-northeast-2.amazonaws.com/shim-music/"
                        +music.getMusic_picture())
                .into(viewHolder.musicItemImage);
        viewHolder.musicItemTitle.setText(music.getMusic_name());
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView musicItemImage;
        TextView musicItemTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            musicItemImage=(ImageView)itemView.findViewById(R.id.music_item_image);
            musicItemTitle=(TextView)itemView.findViewById(R.id.music_item_title);
        }
    }

    public void setItem(List<Music> list){
        musicList=list;
        notifyDataSetChanged();
    }
}
