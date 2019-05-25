package com.shim.user.shimapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shim.user.shimapplication.R;
import com.shim.user.shimapplication.data.LogResponse;
import com.shim.user.shimapplication.data.LogVideo;
import com.shim.user.shimapplication.data.Video;
import com.shim.user.shimapplication.data.handler.LogVideoHandler;
import com.shim.user.shimapplication.data.repository.LogRepo;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import org.w3c.dom.Text;

import java.util.List;

import static com.shim.user.shimapplication.activity.MainActivity.isPlaying;
import static com.shim.user.shimapplication.activity.MainActivity.mp;
import static com.shim.user.shimapplication.activity.MainActivity.playingIndex;
import static com.shim.user.shimapplication.activity.MainActivity.playingPosition;
import static com.shim.user.shimapplication.activity.MainActivity.userID;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoInfoHolder> {
    private List<Video> videoList;
    Context ctx;

    private LogVideo logVideo;
    LogRepo logVideoRepo;

    public VideoAdapter(Context context, List<Video> videoList) {
        this.ctx = context;
        this.videoList = videoList;
    }

    @Override
    public VideoInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_player_layout, parent, false);

        logVideo = new LogVideo();
        LogVideoHandler logVideoHandler = new LogVideoHandler() {
            @Override
            public void onSuccessLogVideo(LogResponse response) {

            }

            @Override
            public void onFailure(Throwable t) {

            }
        };
        logVideoRepo = new LogRepo(logVideoHandler);

        return new VideoInfoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final VideoInfoHolder holder, final int position) {
        final Video video = videoList.get(position);

        holder.videoTitle.setText(video.getVideo_title());

        final YouTubeThumbnailLoader.OnThumbnailLoadedListener  onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener(){
            @Override
            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

            }

            @Override
            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                youTubeThumbnailView.setVisibility(View.VISIBLE);
                holder.relativeLayoutOverYouTubeThumbnailView.setVisibility(View.VISIBLE);
            }
        };

        holder.youTubeThumbnailView.initialize("AIzaSyApKLg2ZLzIt18X1FlCYvXPoUYxgWOWfpM", new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {

                youTubeThumbnailLoader.setVideo(video.getVideo_url());

                youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                //write something for failure
            }
        });
    }

    @Override
    public int getItemCount() {
        if(videoList==null){
            return 0;
        }else {
            return videoList.size();
        }
    }

    public class VideoInfoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected RelativeLayout relativeLayoutOverYouTubeThumbnailView;
        YouTubeThumbnailView youTubeThumbnailView;
        protected ImageView playButton;
        TextView videoTitle;

        public VideoInfoHolder(View itemView) {
            super(itemView);
            videoTitle = (TextView)itemView.findViewById(R.id.video_title);
            playButton=(ImageView)itemView.findViewById(R.id.btnYoutube_player);
            playButton.setOnClickListener(this);
            relativeLayoutOverYouTubeThumbnailView = (RelativeLayout) itemView.findViewById(R.id.relativeLayout_over_youtube_thumbnail);
            youTubeThumbnailView = (YouTubeThumbnailView) itemView.findViewById(R.id.youtube_thumbnail);
        }

        @Override
        public void onClick(View v) {
            mp.stop();
            isPlaying=false;
            playingIndex=-1;
            playingPosition=-1;

            logVideo.setVideo_log_action(1);
            logVideo.setVideo_log_user_id(userID);
            logVideo.setVideo_log_video_id(videoList.get(getLayoutPosition()).getVideo_id());
            logVideoRepo.logVideo(logVideo);

            Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) ctx, "AIzaSyApKLg2ZLzIt18X1FlCYvXPoUYxgWOWfpM", videoList.get(getLayoutPosition()).getVideo_url());
            ctx.startActivity(intent);
        }
    }

    public void setItem(List<Video> List){
        videoList = List;
        notifyDataSetChanged();
    }
}