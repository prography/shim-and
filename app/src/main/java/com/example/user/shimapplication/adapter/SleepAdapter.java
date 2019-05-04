package com.example.user.shimapplication.adapter;

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

import com.example.user.shimapplication.R;
import com.example.user.shimapplication.data.Sleep;

import java.io.IOException;
import java.util.List;

import static com.example.user.shimapplication.activity.MainActivity.mp;

public class SleepAdapter extends RecyclerView.Adapter<SleepAdapter.ViewHolder> {
    private List<Sleep> sleepList;
    private Context context;

    public SleepAdapter(Context context, List<Sleep> sleepList){
        this.context = context;
        this.sleepList = sleepList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(context).inflate(R.layout.sleep_player_layout, viewGroup,false);
        return new ViewHolder(view);
    }
    public void onBindViewHolder(ViewHolder viewHolder, int position){
        final Sleep sleep = sleepList.get(position);
        viewHolder.sleepName.setText(sleep.getSleep_name());
        viewHolder.sleepPlayBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
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
                mp.start();
                /*Intent intent = new Intent(context, MusicService.class);

                if(v.getId()==R.id.sleep_btn_play){
                    intent.putExtra(MusicService.MESSAGE_KEY, true);
                    intent.putExtra("URL", sleep.getSleep_url());
                }else{
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

    public void setItem(List<Sleep> List){
        sleepList = List;
        notifyDataSetChanged();
    }

}