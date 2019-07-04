package com.shim.user.shimapplication.data.Media;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;

import com.shim.user.shimapplication.data.Music;

import java.util.ArrayList;
import java.util.List;

public class AudioService extends Service {
    private final IBinder mBinder = new AudioServiceBinder();
    private MediaPlayer mMediaPlayer;
    private boolean isPrepared;
    private int mCurrentPosition=0;
    private List<Music> musicList = new ArrayList<>();
    private Music music;
    private boolean isHomePlayed = true; // Home 음악이 재생 중인지 여부

    public class AudioServiceBinder extends Binder {
        AudioService getService(){
            return AudioService.this;
        }
    }

    public void onCreate(){
        super.onCreate();
        isPrepared=true;
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                isPrepared = true;
                mp.start();
                sendBroadcast(new Intent(BroadcastActions.PREPARED));
            }
        });
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(isPrepared){
                    forward();
                    isPrepared=false;
                }else {
                    isPrepared = false;
                    sendBroadcast(new Intent(BroadcastActions.PLAY_STATE_CHANGED));
                }
            }
        });
        mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                isPrepared = false;
                sendBroadcast(new Intent(BroadcastActions.PLAY_STATE_CHANGED));
                return false;
            }
        });
        mMediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mp) {

            }
        });
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(mMediaPlayer!=null){
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer=null;
        }
    }

    private void queryAudioItem(int position){
        mCurrentPosition = position;
    }

    public void prepare(){
        try{
            mMediaPlayer.setDataSource(musicList.get(mCurrentPosition).getMusic_music());
            // 초기화 필요
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.prepareAsync();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void stop() {
        mMediaPlayer.stop();
        mMediaPlayer.reset();
    }

    public void setPlayList(List<Music> list){
        if(!musicList.equals(list)){
            musicList.clear();
            musicList.addAll(list);
        }
    }

    public void play(int position){
        queryAudioItem(position);
        stop();
        prepare();
        mMediaPlayer.start();
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                sendBroadcast(new Intent(BroadcastActions.PLAY_STATE_CHANGED));
            }
        }, 300);
    }

    public void play(){
        if(isPrepared){
            mMediaPlayer.start();
            sendBroadcast(new Intent(BroadcastActions.PLAY_STATE_CHANGED));
        }
    }

    public void pause(){
        if(isPrepared){
            mMediaPlayer.pause();
            sendBroadcast(new Intent(BroadcastActions.PLAY_STATE_CHANGED));
        }
    }

    public void forward(){
        if(musicList.size()-1>mCurrentPosition){
            mCurrentPosition++;
        }else{
            mCurrentPosition=0;
        }
        play(mCurrentPosition);
    }

    public void rewind(){
        if(mCurrentPosition>0){
            mCurrentPosition--;
        }else{
            mCurrentPosition=musicList.size()-1;
        }
        play(mCurrentPosition);
    }

    public void delete(int position, List<Music> list){
        musicList.clear();
        musicList.addAll(list);
        if(mCurrentPosition>position){
            mCurrentPosition=mCurrentPosition-1;
        }
        else if(mCurrentPosition==position){
            stop();
            mCurrentPosition=mCurrentPosition-1;
            forward();
        }
    }

    public boolean isPlaying(){
        return mMediaPlayer.isPlaying();
    }

    public Music getMusic(){
        if(musicList.size()==0){
            return null;
        }
        music = musicList.get(mCurrentPosition);
        return music;
    }

    public void playOneMusic() {
        try {
            if (isPlaying()) {
                mMediaPlayer.stop();
                mMediaPlayer.reset();
            }
            mMediaPlayer.setDataSource("https://s3.ap-northeast-2.amazonaws.com/shim-main/" + musicList.get(0).getMusic_music());
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.prepareAsync();
            mMediaPlayer.start();
            sendBroadcast(new Intent(BroadcastActions.PLAY_STATE_CHANGED));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getmCurrentPosition(){
        return mCurrentPosition;
    }

    public void setmCurrentPosition(int position){
        mCurrentPosition=position;
    }

    public boolean getIsHomePlayed() {
        return isHomePlayed;
    }

    public void setIsHomePlayed(boolean check) {
        isHomePlayed = check;
    }
}