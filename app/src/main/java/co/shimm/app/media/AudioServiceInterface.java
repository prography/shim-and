package co.shimm.app.media;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import co.shimm.app.room.Music;

import java.util.ArrayList;
import java.util.List;

public class AudioServiceInterface {
    private ServiceConnection mServiceConnection;
    private AudioService mService;

    public AudioServiceInterface(Context context) {
        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = ((AudioService.AudioServiceBinder) service).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mServiceConnection = null;
                mService = null;
            }
        };
        context.bindService(new Intent(context, AudioService.class)
                .setPackage(context.getPackageName()), mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    public void setPlayList(ArrayList<Music> musicList) {
        if (mService != null) {
            mService.setPlayList(musicList);
        }
    }

    public void play(int position) {
        if (mService != null) {
            mService.play(position);
        }
    }

    public void play() {
        if (mService != null) {
            mService.play();
        }
    }

    public void pause() {
        if (mService != null) {
            mService.pause();
        }
    }

    public void forward() {
        if (mService != null) {
            mService.forward();
        }
    }

    public void rewind() {
        if (mService != null) {
            mService.rewind();
        }
    }

    public void delete(int position, List<Music> list) {
        if (mService != null) {
            mService.delete(position, list);
        }
    }

    public boolean isPlaying() {
        if (mService != null) {
            return mService.isPlaying();
        }
        return false;
    }

    public void togglePlay() {
        if (isPlaying()) {
            mService.pause();
        } else {
            mService.prepare();
            mService.play();
        }
    }

    public Music getMusic() {
        if (mService != null) {
            return mService.getMusic();
        }
        return null;
    }

    public void playOneMusic() {
        if (mService != null) {
            mService.playOneMusic();
        }
    }

    public void stop() {
        if (mService != null) {
            mService.stop();
        }
    }

    public boolean getIsHomePlayed() {
        if (mService != null) {
            return mService.getIsHomePlayed();
        }
        return false;
    }

    public void setIsHomePlayed(boolean check) {
        if (mService != null) {
            mService.setIsHomePlayed(check);
        }
    }

    public int getPlayListSize(){
        if(mService!=null){
            return mService.getPlayListSize();
        }
        else{
            return 0;
        }
    }

    public void setmCurrentPosition(int position) {
        mService.setmCurrentPosition(position);
    }

    public int getProgressPosition(){
        if(mService!=null){
            return mService.getProgressPosition();
        }else{
            return 0;
        }
    }
}
