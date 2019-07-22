package co.shimm.app.media;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import co.shimm.app.R;
import co.shimm.app.room.Music;
import co.shimm.app.util.logging.Log;
import co.shimm.app.util.logging.LogEvent;

public class AudioService extends Service {
    private final IBinder mBinder = new AudioServiceBinder();
    private MediaPlayer mMediaPlayer;
    private boolean isPrepared;
    private int mCurrentPosition = 0;
    private List<Music> musicList = new ArrayList<>();
    private Music music;
    private boolean isHomePlayed = true; // Home 음악이 재생 중인지 여부
    private NotificationPlayer mNotificationPlayer;


    public void onCreate() {
        super.onCreate();
        isPrepared = true;
        mMediaPlayer = new MediaPlayer();

        mMediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mMediaPlayer.setOnPreparedListener(mp -> {
            isPrepared = true;
            mp.start();
            sendBroadcast(new Intent(BroadcastActions.PREPARED));
            updateNotificationPlayer();
        });
        mMediaPlayer.setOnCompletionListener(mp -> {
            if (isPrepared) {
                forward();
                isPrepared = false;
            } else {
                isPrepared = false;
                sendBroadcast(new Intent(BroadcastActions.PLAY_STATE_CHANGED));
            }
            updateNotificationPlayer();
        });
        mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                isPrepared = false;
                sendBroadcast(new Intent(BroadcastActions.PLAY_STATE_CHANGED));
                updateNotificationPlayer();
                return false;
            }
        });
        mMediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mp) {

            }
        });
        mNotificationPlayer = new NotificationPlayer(this);
    }

    private void updateNotificationPlayer() {
        if (mNotificationPlayer != null && isHomePlayed == false) {
            mNotificationPlayer.updateNotificationPlayer();
        }
    }

    private void removeNotificationPlayer() {
        if (mNotificationPlayer != null) {
            mNotificationPlayer.removeNotificationPlayer();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    private void queryAudioItem(int position) {
        mCurrentPosition = position;
    }

    public void prepare() {
        try {
            mMediaPlayer.setDataSource(musicList.get(mCurrentPosition).getUrl());
            // 초기화 필요
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopWithNoLog(){
        mMediaPlayer.stop();
        mMediaPlayer.reset();
    }

    public void stop() {
        if(musicList.size()!=0&&mCurrentPosition<musicList.size()) {
            if (musicList.get(mCurrentPosition).getTitle().contains("(HOME)")) {
                Log.i(LogEvent.HOME_MUSIC_STOP, String.valueOf(musicList.get(mCurrentPosition).getId()));
            } else if (musicList.get(mCurrentPosition).getTitle().contains("(ASMR)")) {
                Log.i(LogEvent.ASMR_STOP, String.valueOf(musicList.get(mCurrentPosition).getId()));
            } else {
                Log.i(LogEvent.MUSIC_STOP, String.valueOf(musicList.get(mCurrentPosition).getId()));
            }
        }
        mMediaPlayer.stop();
        mMediaPlayer.reset();
        sendBroadcast(new Intent(BroadcastActions.PLAY_STATE_CHANGED));
        updateNotificationPlayer();
    }

    public void setPlayList(List<Music> list) {
        if (!musicList.equals(list)) {
            musicList.clear();
            musicList.addAll(list);
        }
    }

    public void play(int position) {
        queryAudioItem(position);
        stopWithNoLog();
        try {
            mMediaPlayer.setDataSource(musicList.get(mCurrentPosition).getUrl());
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setOnPreparedListener(mediaPlayer -> {
                mMediaPlayer.start();
                sendBroadcast(new Intent(BroadcastActions.PLAY_STATE_CHANGED)); // MusicPlayer
                updateNotificationPlayer(); // NoticationPlayer
            });
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (musicList.size() != 0) {
            if (musicList.get(mCurrentPosition).getTitle().contains("(HOME)")) {
                Log.i(LogEvent.HOME_MUSIC_PLAY, String.valueOf(musicList.get(mCurrentPosition).getId()));
            } else if (musicList.get(mCurrentPosition).getTitle().contains("(ASMR)")) {
                Log.i(LogEvent.ASMR_PLAY, String.valueOf(musicList.get(mCurrentPosition).getId()));
            } else {
                Log.i(LogEvent.MUSIC_PLAY, String.valueOf(musicList.get(mCurrentPosition).getId()));
            }
        }

    }

    public void play() {
        if (isPrepared) {
            if(musicList.size()!=0) {
                if (musicList.get(mCurrentPosition).getTitle().contains("(HOME)")) {
                    Log.i(LogEvent.HOME_MUSIC_PLAY, String.valueOf(musicList.get(mCurrentPosition).getId()));
                } else if (musicList.get(mCurrentPosition).getTitle().contains("(ASMR)")) {
                    Log.i(LogEvent.ASMR_PLAY, String.valueOf(musicList.get(mCurrentPosition).getId()));
                } else {
                    Log.i(LogEvent.MUSIC_PLAY, String.valueOf(musicList.get(mCurrentPosition).getId()));
                }
            }
            mMediaPlayer.start();
            sendBroadcast(new Intent(BroadcastActions.PLAY_STATE_CHANGED));
                        updateNotificationPlayer();
        }
    }

    public void pause() {
        if (isPrepared) {
            if (musicList.size() != 0) {
                if (musicList.get(mCurrentPosition).getTitle().contains("(HOME)")) {
                    // There's no event when home music is paused
                } else if (musicList.get(mCurrentPosition).getTitle().contains("(ASMR)")) {
                    Log.i(LogEvent.ASMR_PAUSE, String.valueOf(musicList.get(mCurrentPosition).getId()));
                } else {
                    Log.i(LogEvent.MUSIC_PAUSE, String.valueOf(musicList.get(mCurrentPosition).getId()));
                }
            }
            mMediaPlayer.pause();
            sendBroadcast(new Intent(BroadcastActions.PLAY_STATE_CHANGED));
            updateNotificationPlayer();
        }
    }

    public void forward() {
        if (musicList.size() - 1 > mCurrentPosition) {
            mCurrentPosition++;
        } else {
            mCurrentPosition = 0;
        }
        if (musicList.size() != 0) {
            play(mCurrentPosition);
        }
    }

    public void rewind() {
        if (mCurrentPosition > 0) {
            mCurrentPosition--;
        } else {
            mCurrentPosition = musicList.size() - 1;
        }
        if (musicList.size() != 0) {
            play(mCurrentPosition);
        }
    }

    public void delete(int position, List<Music> list) {
        musicList.clear();
        musicList.addAll(list);
        if (mCurrentPosition > position) {
            mCurrentPosition = mCurrentPosition - 1;
        } else if (mCurrentPosition == position) {
            stop();
            mCurrentPosition = mCurrentPosition - 1;
            forward();
        }
        if(musicList.size()==0){
            sendBroadcast(new Intent(BroadcastActions.PLAY_STATE_CHANGED));
            removeNotificationPlayer();
        }
        else {
            sendBroadcast(new Intent(BroadcastActions.PLAY_STATE_CHANGED));
            updateNotificationPlayer();
        }
    }

    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    public Music getMusic() {
        if (musicList.size() == 0) {
            return null;
        }
        music = musicList.get(mCurrentPosition);
        return music;
    }

    public int getPlayListSize(){
        if(musicList!=null) {
            return musicList.size();
        }
        else{
            return 0;
        }
    }

    public void playOneMusic() {
        try {
            if (isPlaying()) {
                mMediaPlayer.stop();
                mMediaPlayer.reset();
            }
            if(musicList.size()!=0) {
                if (musicList.get(mCurrentPosition).getTitle().contains("(HOME)")) {
                    Log.i(LogEvent.HOME_MUSIC_PLAY, String.valueOf(musicList.get(mCurrentPosition).getId()));
                } else if (musicList.get(mCurrentPosition).getTitle().contains("(ASMR)")) {
                    Log.i(LogEvent.ASMR_PLAY, String.valueOf(musicList.get(mCurrentPosition).getId()));
                } else {
                    Log.i(LogEvent.MUSIC_PLAY, String.valueOf(musicList.get(mCurrentPosition).getId()));
                }
            }
            mMediaPlayer.setDataSource(musicList.get(0).getUrl());
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.prepareAsync();
            mMediaPlayer.start();
            sendBroadcast(new Intent(BroadcastActions.PLAY_STATE_CHANGED));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getmCurrentPosition() {
        return mCurrentPosition;
    }

    public void setmCurrentPosition(int position) {
        mCurrentPosition = position;
    }

    public boolean getIsHomePlayed() {
        return isHomePlayed;
    }

    public void setIsHomePlayed(boolean check) {
        isHomePlayed = check;
    }

    public int getProgressPosition(){
        return mMediaPlayer.getCurrentPosition();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            if (CommandActions.TOGGLE_PLAY.equals(action)) {
                if (isPlaying()) {
                    pause();
                } else {
                    play();
                }
            } else if (CommandActions.REWIND.equals(action)) {
                rewind();
            } else if (CommandActions.FORWARD.equals(action)) {
                forward();
            } else if (CommandActions.CLOSE.equals(action)) {
                pause();
                removeNotificationPlayer();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @NonNull
    @TargetApi(26)
    public synchronized String createChannel() {
        NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        String name = "background service";
        int importance = NotificationManager.IMPORTANCE_LOW;

        @SuppressLint("WrongConstant") NotificationChannel mChannel = new NotificationChannel("service channel", name, importance);

        mChannel.enableLights(true);
        mChannel.setLightColor(Color.BLUE);
        mChannel.setVibrationPattern(new long[]{0});
        mChannel.enableVibration(true);
        if (mNotificationManager != null && isHomePlayed == false) {
            mNotificationManager.createNotificationChannel(mChannel);
        } else {
            stopSelf();
        }
        return "service channel";
    }

    public class AudioServiceBinder extends Binder {
        AudioService getService() {
            return AudioService.this;
        }
    }
}