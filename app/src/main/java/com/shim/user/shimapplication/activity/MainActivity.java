package com.shim.user.shimapplication.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shim.user.shimapplication.R;
import com.shim.user.shimapplication.fragment.AsmrFragment;
import com.shim.user.shimapplication.fragment.BreathFragment;
import com.shim.user.shimapplication.fragment.EtcFragment;
import com.shim.user.shimapplication.fragment.HomeFragment;
import com.shim.user.shimapplication.fragment.MusicFragment;
import com.shim.user.shimapplication.media.AudioApplication;
import com.shim.user.shimapplication.media.BroadcastActions;
import com.shim.user.shimapplication.retrofit.ServiceGenerator;
import com.shim.user.shimapplication.retrofit.ShimService;
import com.shim.user.shimapplication.retrofit.response.AsmrListResponse;
import com.shim.user.shimapplication.retrofit.response.MusicListResponse;
import com.shim.user.shimapplication.room.Asmr;
import com.shim.user.shimapplication.room.Music;
import com.shim.user.shimapplication.room.ShimDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.VISIBLE;


public class MainActivity extends AppCompatActivity {
    public static final List<com.shim.user.shimapplication.room.Music> mainList = new ArrayList<>();
    // 재생목록 추가를 위한 Music Play List
    public static ArrayList<Music> musicPlayList = new ArrayList<>();

    public static String userID;
    static LinearLayout musicPlayerLayout;
    ImageView musicPlayerImage;
    TextView musicPlayerTitle;
    ImageButton musicPlayerPlayBtn;
    ImageButton musicPlayerRewindBtn;
    ImageButton musicPlayerForwardBtn;
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateMusicUI();
        }
    };

    private FragmentManager fragmentManager = getSupportFragmentManager();

    private HomeFragment homeFragment = new HomeFragment();
    private AsmrFragment asmrFragment = new AsmrFragment();
    private BreathFragment breathFragment = new BreathFragment();
    private MusicFragment musicFragment = new MusicFragment();
    private EtcFragment etcFragment = new EtcFragment();

    public static void showPlayer() {
        musicPlayerLayout.setVisibility(VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userID = Settings.Secure.getString(getApplicationContext()
                .getContentResolver(), Settings.Secure.ANDROID_ID);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);

        fetchMusicList();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        fragmentManager.beginTransaction()
                .replace(R.id.frame_layout, homeFragment)
                .commitAllowingStateLoss();

        navigation.setOnNavigationItemSelectedListener(item -> {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    transaction.replace(R.id.frame_layout, homeFragment).commitAllowingStateLoss();
                    musicPlayerLayout.setVisibility(View.INVISIBLE);
                    return true;
                case R.id.navigation_asmr:
                    transaction.replace(R.id.frame_layout, asmrFragment).commitAllowingStateLoss();
                    if (!AudioApplication.getInstance().getServiceInterface().getIsHomePlayed()) {
                        musicPlayerLayout.setVisibility(VISIBLE);
                    }
                    return true;
                case R.id.navigation_breath:
                    transaction.replace(R.id.frame_layout, breathFragment).commitAllowingStateLoss();
                    if (!AudioApplication.getInstance().getServiceInterface().getIsHomePlayed()) {
                        musicPlayerLayout.setVisibility(VISIBLE);
                    }
                    return true;
                case R.id.navigation_music:
                    transaction.replace(R.id.frame_layout, musicFragment).commitAllowingStateLoss();
                    musicPlayerLayout.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_etc:
                    transaction.replace(R.id.frame_layout, etcFragment).commitAllowingStateLoss();
                    musicPlayerLayout.setVisibility(View.INVISIBLE);
                    return true;
            }
            return false;
        });

        musicPlayerImage = findViewById(R.id.music_player_music_image);
        musicPlayerTitle = findViewById(R.id.music_player_music_title);
        musicPlayerPlayBtn = findViewById(R.id.music_player_btn_play_pause);
        musicPlayerForwardBtn = findViewById(R.id.music_player_btn_forward);
        musicPlayerRewindBtn = findViewById(R.id.music_player_btn_rewind);
        musicPlayerLayout = findViewById(R.id.music_player_layout);

        musicPlayerRewindBtn.setOnClickListener(v -> {
            AudioApplication.getInstance().getServiceInterface().setPlayList(musicPlayList);
            AudioApplication.getInstance().getServiceInterface().rewind();
        });

        musicPlayerPlayBtn.setOnClickListener(v -> {
            AudioApplication.getInstance().getServiceInterface().setPlayList(musicPlayList);
            AudioApplication.getInstance().getServiceInterface().togglePlay();
        });

        musicPlayerForwardBtn.setOnClickListener(v -> {
            AudioApplication.getInstance().getServiceInterface().setPlayList(musicPlayList);
            AudioApplication.getInstance().getServiceInterface().forward();
        });

        musicPlayerLayout.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), PlaylistActivity.class);
            startActivity(intent);
        });

        registerBroadcast();
        updateMusicUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterBroadcast();
    }

    public void registerBroadcast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadcastActions.PLAY_STATE_CHANGED);
        registerReceiver(mBroadcastReceiver, filter);
    }

    public void unregisterBroadcast() {
        unregisterReceiver(mBroadcastReceiver);
    }

    public void updateMusicUI() {
        Music music = AudioApplication.getInstance().getServiceInterface().getMusic();
        if (music != null && !AudioApplication.getInstance().getServiceInterface().getIsHomePlayed()) {
            Glide.with(this)
                    .load(music.getThumbnail())
                    .into(musicPlayerImage);
            musicPlayerTitle.setText(music.getTitle());
        } else {
            musicPlayerImage.setImageResource(R.drawable.empty_albumart);
            musicPlayerTitle.setText("재생중인 음악이 없습니다");
        }
        if (AudioApplication.getInstance().getServiceInterface().isPlaying()
                && !AudioApplication.getInstance().getServiceInterface().getIsHomePlayed()) {
            musicPlayerPlayBtn.setImageResource(R.drawable.ic_pause);
        } else {
            musicPlayerPlayBtn.setImageResource(R.drawable.ic_play);
        }
    }

    public void fetchMusicList() {
        ShimDatabase database = ShimDatabase.getInstance(getApplicationContext());
        ShimService service = ServiceGenerator.create();
        service.getAsmrList().enqueue(new Callback<AsmrListResponse>() {
            @Override
            public void onResponse(@NotNull Call<AsmrListResponse> call, @NotNull Response<AsmrListResponse> response) {
                List<Asmr> asmrList = Objects.requireNonNull(response.body()).getData();
                for (Asmr asmr : asmrList) {
                    asmr.setThumbnail("https://s3.ap-northeast-2.amazonaws.com/shim-main/" + asmr.getThumbnail());
                    asmr.setUrl("https://s3.ap-northeast-2.amazonaws.com/shim-main/" + asmr.getUrl());
                }
                new Thread(() -> database.getAsmrDao().insertAll(asmrList)).start();
            }

            @Override
            public void onFailure(@NotNull Call<AsmrListResponse> call, @NotNull Throwable t) {
            }
        });
        service.getHomeMusicList().enqueue(new Callback<MusicListResponse>() {
            @Override
            public void onResponse(@NotNull Call<MusicListResponse> call, @NotNull Response<MusicListResponse> response) {
                ArrayList<Music> musicList = (ArrayList<Music>) Objects.requireNonNull(response.body()).getData();
                for (Music music : musicList) {
                    music.setThumbnail("https://s3.ap-northeast-2.amazonaws.com/shim-main/" + music.getThumbnail());
                    music.setUrl("https://s3.ap-northeast-2.amazonaws.com/shim-main/" + music.getUrl());
                }
                mainList.addAll(musicList);
                homeFragment.notifyMusicListChanged();
            }

            @Override
            public void onFailure(@NotNull Call<MusicListResponse> call, @NotNull Throwable t) {
            }
        });
        service.getMusicList(userID).enqueue(new Callback<MusicListResponse>() {
            @Override
            public void onResponse(@NotNull Call<MusicListResponse> call, @NotNull Response<MusicListResponse> response) {
                List<Music> musicList = Objects.requireNonNull(response.body()).getData();
                for (Music music : musicList) {
                    music.setThumbnail("https://s3.ap-northeast-2.amazonaws.com/shim-main/" + music.getThumbnail());
                    music.setUrl("https://s3.ap-northeast-2.amazonaws.com/shim-main/" + music.getUrl());
                }
                new Thread(() -> database.getMusicDao().insertAll(musicList)).start();
            }

            @Override
            public void onFailure(@NotNull Call<MusicListResponse> call, @NotNull Throwable t) {
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        hideSystemUI();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void hideSystemUI() {
        // Enables sticky immersive mode.
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        // Set the content to appear under the system bars so that the content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags except for the ones that make the content appear under the system bars.
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}
