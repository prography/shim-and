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
import com.shim.user.shimapplication.data.Media.AudioApplication;
import com.shim.user.shimapplication.data.Music;

import java.util.ArrayList;
import java.util.List;

import static com.shim.user.shimapplication.activity.MainActivity.musicPlayList;

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.ViewHolder>{
    private ArrayList<Music> musicList;
    private Context context;

    public AudioAdapter(Context context, ArrayList<Music> musicList){
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
                .load(music.getMusic_picture())
                .into(viewHolder.musicItemImage);
        viewHolder.musicItemTitle.setText(music.getMusic_name());
        viewHolder.musicItemDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int deletePosition = position;
                musicPlayList.remove(position);
                notifyDataSetChanged();
                AudioApplication.getInstance().getServiceInterface().delete(deletePosition,musicPlayList);
                //musicList.clear();
                //musicList.addAll(musicPlayList);
                //notifyItemRangeRemoved(0,currentSize);
                //notifyItemRangeInserted(0,musicPlayList.size());
                //AudioApplication.getInstance().getServiceInterface().delete(deletePosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView musicItemImage;
        TextView musicItemTitle;
        ImageButton musicItemDeleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            musicItemImage=(ImageView)itemView.findViewById(R.id.music_item_image);
            musicItemTitle=(TextView)itemView.findViewById(R.id.music_item_title);
            musicItemDeleteButton=(ImageButton)itemView.findViewById(R.id.music_item_delete);
        }
    }

    public void setItem(ArrayList<Music> list){
        musicList=list;
        notifyDataSetChanged();
    }
}
