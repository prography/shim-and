package com.example.user.shimapplication.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.example.user.shimapplication.data.Main;
import com.example.user.shimapplication.data.handler.ShowMainHandler;
import com.example.user.shimapplication.data.repository.ShimRepo;
import com.example.user.shimapplication.fragment.EtcFragment;
import com.example.user.shimapplication.fragment.HomeFragment;
import com.example.user.shimapplication.fragment.MusicFragment;
import com.example.user.shimapplication.fragment.SleepFragment;
import com.example.user.shimapplication.fragment.VideoFragment;
import com.example.user.shimapplication.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static MediaPlayer mp;
    int pos;
    boolean isPlaaing = false;

    private FragmentManager fragmentManager = getSupportFragmentManager();

    private HomeFragment homeFragment = new HomeFragment();
    private SleepFragment sleepFragment = new SleepFragment();
    private VideoFragment videoFragment = new VideoFragment();
    private MusicFragment musicFragment = new MusicFragment();
    private EtcFragment etcFragment = new EtcFragment();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    transaction.replace(R.id.frame_layout, homeFragment).commitAllowingStateLoss();
                    return true;
                case R.id.navigation_sleep:
                    transaction.replace(R.id.frame_layout, sleepFragment).commitAllowingStateLoss();
                    return true;
                case R.id.navigation_video:
                    transaction.replace(R.id.frame_layout, videoFragment).commitAllowingStateLoss();
                    return true;
                case R.id.navigation_music:
                    transaction.replace(R.id.frame_layout, musicFragment).commitAllowingStateLoss();
                    return true;
                case R.id.navigation_etc:
                    transaction.replace(R.id.frame_layout, etcFragment).commitAllowingStateLoss();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, homeFragment).commitAllowingStateLoss();

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mp = new MediaPlayer();

        //mp = MediaPlayer.create(getApplicationContext(), null);
        //mp.setLooping(false);
        // if true , 반복재생

    }

}
