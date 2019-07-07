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
import com.shim.user.shimapplication.data.LogResponse;
import com.shim.user.shimapplication.data.LogSleep;
import com.shim.user.shimapplication.data.Media.AudioApplication;
import com.shim.user.shimapplication.data.Music;
import com.shim.user.shimapplication.data.Sleep;
import com.shim.user.shimapplication.data.handler.LogSleepHandler;
import com.shim.user.shimapplication.data.repository.LogRepo;

import java.util.ArrayList;
import java.util.List;

import static com.shim.user.shimapplication.activity.MainActivity.musicPlayList;
import static com.shim.user.shimapplication.activity.MainActivity.showPlayer;
import static com.shim.user.shimapplication.fragment.HomeFragment.isOtherMusicPlayed;

public class SleepAdapter extends RecyclerView.Adapter<SleepAdapter.ViewHolder> {
    private List<Sleep> sleepList;
    private Context context;
    private int category;
    private List<Music> forHomeCheckList = new ArrayList<>();

    private LogSleep logSleep;
    LogRepo logSleepRepo;

    public SleepAdapter(Context context, List<Sleep> sleepList) {
        this.context = context;
        this.sleepList = sleepList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(context).inflate(R.layout.sleep_player_layout, viewGroup,false);

        logSleep = new LogSleep();
        LogSleepHandler logSleepHandler = new LogSleepHandler() {
            @Override
            public void onSuccessLogSleep(LogResponse response) {

            }

            @Override
            public void onFailure(Throwable t) {

            }
        };
        logSleepRepo = new LogRepo(logSleepHandler);

        return new ViewHolder(view);
    }
    public void onBindViewHolder(final ViewHolder viewHolder, final int position){
        final Sleep sleep = sleepList.get(position);
        viewHolder.sleepName.setText(sleep.getSleep_name());
        Glide.with(viewHolder.itemView.getContext())
                .load("https://s3.ap-northeast-2.amazonaws.com/shim-sleep/"
                        + sleep.getSleep_picture())
                .into(viewHolder.sleepImage);
        viewHolder.sleepPlayBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Music addingMusic = new Music(sleep.getSleep_id(), sleep.getSleep_name(),
                        "https://s3.ap-northeast-2.amazonaws.com/shim-sleep/" + sleep.getSleep_music(),
                        null, "https://s3.ap-northeast-2.amazonaws.com/shim-sleep/"+sleep.getSleep_picture(), false);
                musicPlayList.add(addingMusic);
                if (AudioApplication.getInstance().getServiceInterface().getIsHomePlayed() == true) {
                    AudioApplication.getInstance().getServiceInterface().stop();
                    AudioApplication.getInstance().getServiceInterface().setPlayList((ArrayList<Music>) forHomeCheckList);
                    AudioApplication.getInstance().getServiceInterface().setIsHomePlayed(false);
                    isOtherMusicPlayed = true;
                    showPlayer();
                }
                AudioApplication.getInstance().getServiceInterface().setPlayList(musicPlayList);
                AudioApplication.getInstance().getServiceInterface().play(musicPlayList.size() - 1);
            }
        });

    }

    @Override
    public int getItemCount(){
        if(sleepList==null){
            return 0;
        }
        else{
            return sleepList.size();}}

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView sleepName;
        ImageView sleepImage;
        ImageButton sleepPlayBtn;

        ViewHolder(View itemView){
            super(itemView);
            sleepImage = (ImageView)itemView.findViewById(R.id.sleep_picture);
            sleepPlayBtn = (ImageButton)itemView.findViewById(R.id.sleep_btn_play);
            sleepName = (TextView)itemView.findViewById(R.id.sleep_name);
        }
    }

    public void setItem(List<Sleep> List) {
        sleepList = List;
        notifyDataSetChanged();
    }

}