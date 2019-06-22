package com.shim.user.shimapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.shim.user.shimapplication.R;
import com.shim.user.shimapplication.data.LogResponse;
import com.shim.user.shimapplication.data.LogSleep;
import com.shim.user.shimapplication.data.Sleep;
import com.shim.user.shimapplication.data.SleepExtend;
import com.shim.user.shimapplication.data.handler.LogSleepHandler;
import com.shim.user.shimapplication.data.repository.LogRepo;

import java.io.IOException;
import java.util.List;

import static com.shim.user.shimapplication.activity.MainActivity.changeButton;
import static com.shim.user.shimapplication.activity.MainActivity.isPlaying;
import static com.shim.user.shimapplication.activity.MainActivity.mp;
import static com.shim.user.shimapplication.activity.MainActivity.playingIndex;
import static com.shim.user.shimapplication.activity.MainActivity.playingPosition;
import static com.shim.user.shimapplication.activity.MainActivity.userID;

public class SleepAdapter extends RecyclerView.Adapter<SleepAdapter.ViewHolder> {
    private List<SleepExtend> sleepList;
    private Context context;
    private int category;

    private LogSleep logSleep;
    LogRepo logSleepRepo;

    public SleepAdapter(Context context, List<SleepExtend> sleepList){
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
        final SleepExtend sleep = sleepList.get(position);
        viewHolder.sleepName.setText(sleep.getSleep_name());
        if(sleep.getButton_pushed()==1){
            viewHolder.sleepPlayBtn.setImageResource(R.drawable.ic_pause_circle_outline);
        }else{
            viewHolder.sleepPlayBtn.setImageResource(R.drawable.ic_play_circle_outline);
        }
        viewHolder.sleepPlayBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(sleep.getButton_pushed()==0){
                    mp.reset();
                    mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    try {
                        mp.setDataSource("https://s3.ap-northeast-2.amazonaws.com/" +
                                "shim-sleep/"+sleep.getSleep_music());
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
                    isPlaying = true;
                    viewHolder.sleepPlayBtn.setImageResource(R.drawable.ic_pause_circle_outline);
                    changeButton(1, position);
                    playingPosition = 1;
                    playingIndex = position;

                    logSleep.setSleep_log_action(1);
                    logSleep.setSleep_log_user_id(userID);
                    logSleep.setSleep_log_sleep_id(sleep.getSleep_id());
                    logSleepRepo.logSleep(logSleep);
                }
                else{
                    mp.stop();
                    isPlaying = false;
                    playingPosition = -1;
                    playingIndex = -1;
                    sleepList.get(position).setButton_pushed(0);
                    viewHolder.sleepPlayBtn.setImageResource(R.drawable.ic_play_circle_outline);

                    logSleep.setSleep_log_sleep_id(sleep.getSleep_id());
                    logSleep.setSleep_log_user_id(userID);
                    logSleep.setSleep_log_action(0);
                    logSleepRepo.logSleep(logSleep);
                }
                /*Intent intent = new Intent(context, MusicService.class);

                if(v.getId()==R.id.sleep_btn_play){
                    intent.putExtra(MusicService.MESSAGE_KEY, true);
                    intent.putExtra("URL", sleep.getSleep_url());
                }else{log
                    intent.putExtra(MusicService.MESSAGE_KEY, false);
                }

                context.startService(intent);
                */
            }
        });
        /*viewHolder.sleepStopBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // 정지 기능 추가
                mp.stop();
            }
        });
        */
        /*
        viewHolder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("sleep_id", video.getSleep_id());
                context.startActivity(intent);
            }
        });
        */
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
        ImageButton sleepPlayBtn;

        ViewHolder(View itemView){
            super(itemView);
            sleepPlayBtn = (ImageButton)itemView.findViewById(R.id.sleep_btn_play);
            sleepName = (TextView)itemView.findViewById(R.id.sleep_name);
        }
    }

    public void setItem(List<SleepExtend> List){
        sleepList = List;
        notifyDataSetChanged();
    }

}