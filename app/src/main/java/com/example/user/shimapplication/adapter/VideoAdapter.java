package com.example.user.shimapplication.adapter;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.shimapplication.R;
import com.example.user.shimapplication.data.Video;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private List<Video> videoList;
    private Context context;

    public VideoAdapter(Context context, List<Video> videoList){
        this.context = context;
        this.videoList = videoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(context).inflate(R.layout.video_player_layout, viewGroup,false);
        return new ViewHolder(view);
    }
    public void onBindViewHolder(ViewHolder viewHolder, int position){
        final Video video = videoList.get(position);
        viewHolder.videoTitle.setText(video.getVideo_title());


        viewHolder.youtubeView.initialize(String.valueOf(R.string.youtube_key), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.cueVideo(video.getVideo_url());
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });

    }
    @Override
    public int getItemCount(){
        if(videoList==null){
            return 0;
        }else{
        return videoList.size();}}

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView videoTitle;
        YouTubePlayerView youtubeView;

        ViewHolder(View itemView){
            super(itemView);
            videoTitle = (TextView)itemView.findViewById(R.id.video_title);
            youtubeView = (YouTubePlayerView)itemView.findViewById(R.id.youtube_view);
        }
    }

    public void setItem(List<Video> List){
        videoList = List;
        notifyDataSetChanged();
    }
}