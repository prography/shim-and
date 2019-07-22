package co.shimm.app.activity;

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
import android.widget.ProgressBar;
import android.widget.TextView;
import com.facebook.FacebookSdk;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import co.shimm.app.R;
import co.shimm.app.fragment.AsmrFragment;
import co.shimm.app.fragment.BreathFragment;
import co.shimm.app.fragment.EtcFragment;
import co.shimm.app.fragment.HomeFragment;
import co.shimm.app.fragment.MusicFragment;
import co.shimm.app.media.AudioApplication;
import co.shimm.app.media.BroadcastActions;
import co.shimm.app.retrofit.ServiceGenerator;
import co.shimm.app.retrofit.ShimService;
import co.shimm.app.retrofit.response.AsmrListResponse;
import co.shimm.app.retrofit.response.MusicListResponse;
import co.shimm.app.room.Asmr;
import co.shimm.app.room.Music;
import co.shimm.app.room.ShimDatabase;
import co.shimm.app.util.Theme;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import co.shimm.app.util.logging.Log;
import co.shimm.app.util.logging.LogEvent;
import co.shimm.app.util.logging.LogSender;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//import co.shimm.app.shimapplication.dev.MusicPlayerNotification;


public class MainActivity extends AppCompatActivity {
    public static final List<co.shimm.app.room.Music> mainList = new ArrayList<>();
    // 재생목록 추가를 위한 Music Play List
    public static ArrayList<Music> musicPlayList = new ArrayList<>();

    public static String userID;
    static CardView musicPlayerCard;
    ImageView musicPlayerImage;
    TextView musicPlayerTitle;
    TextView musicPlayerArtist;
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

    private boolean isPreviousBreath = false;
    private boolean isPlaying = false;
    public static boolean isCurrentEtc = false;
    public static boolean isChangedTheme = false;

    public static void showPlayer() {
        musicPlayerCard.setVisibility(View.VISIBLE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userID = Settings.Secure.getString(getApplicationContext()
                .getContentResolver(), Settings.Secure.ANDROID_ID);
        setContentView(R.layout.activity_main);

        LogSender.SSAID = userID;
        Theme.apply(PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .getString("theme", "night_owl"));
        fetchMusicList();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        fragmentManager.beginTransaction()
                .replace(R.id.frame_layout, homeFragment)
                .commitAllowingStateLoss();
        if(isChangedTheme){
            navigation.setSelectedItemId(R.id.navigation_etc);
            fragmentManager.beginTransaction().replace(R.id.frame_layout, etcFragment).commitAllowingStateLoss();
            isChangedTheme=false;
        }else {
            navigation.setSelectedItemId(R.id.navigation_home);
        }// Default Position Setting

        navigation.setOnNavigationItemSelectedListener(item -> {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_breath:
                    transaction.replace(R.id.frame_layout, breathFragment).commitAllowingStateLoss();
                    if (!AudioApplication.getInstance().getServiceInterface().getIsHomePlayed()
                            &&AudioApplication.getInstance().getServiceInterface().getPlayListSize()!=0) {
                        musicPlayerCard.setVisibility(View.VISIBLE);
                    }
                    if(AudioApplication.getInstance().getServiceInterface().isPlaying()){
                        AudioApplication.getInstance().getServiceInterface().pause();
                    }
                    isPreviousBreath=true;
                    Log.i(LogEvent.PAGE_CHANGE, "FRAGMENT_BREATHE");
                    return true;
                case R.id.navigation_asmr:
                    transaction.replace(R.id.frame_layout, asmrFragment).commitAllowingStateLoss();
                    if (!AudioApplication.getInstance().getServiceInterface().getIsHomePlayed()
                        &&AudioApplication.getInstance().getServiceInterface().getPlayListSize()!=0) {
                        musicPlayerCard.setVisibility(View.VISIBLE);
                    }
                    if(!AudioApplication.getInstance().getServiceInterface().isPlaying()&&isPreviousBreath==true){
                        AudioApplication.getInstance().getServiceInterface().play();
                        isPreviousBreath=false;
                    }
                    Log.i(LogEvent.PAGE_CHANGE, "FRAGMENT_ASMR");
                    return true;
                case R.id.navigation_home:
                    transaction.replace(R.id.frame_layout, homeFragment).commitAllowingStateLoss();
                    musicPlayerCard.setVisibility(View.INVISIBLE);
                    if(!AudioApplication.getInstance().getServiceInterface().isPlaying()&&isPreviousBreath==true){
                        AudioApplication.getInstance().getServiceInterface().play();
                        isPreviousBreath=false;
                    }
                    Log.i(LogEvent.PAGE_CHANGE, "FRAGMENT_HOME");
                    return true;
                case R.id.navigation_music:
                    transaction.replace(R.id.frame_layout, musicFragment).commitAllowingStateLoss();
                    if (!AudioApplication.getInstance().getServiceInterface().getIsHomePlayed()
                        &&AudioApplication.getInstance().getServiceInterface().getPlayListSize()!=0) {
                        musicPlayerCard.setVisibility(View.VISIBLE);
                    }
                    if(!AudioApplication.getInstance().getServiceInterface().isPlaying()&&isPreviousBreath==true){
                        AudioApplication.getInstance().getServiceInterface().play();
                        isPreviousBreath=false;
                    }
                    return true;
                case R.id.navigation_etc:
                    transaction.replace(R.id.frame_layout, etcFragment).commitAllowingStateLoss();
                    musicPlayerCard.setVisibility(View.INVISIBLE);
                    if(!AudioApplication.getInstance().getServiceInterface().isPlaying()&&isPreviousBreath==true){
                        AudioApplication.getInstance().getServiceInterface().play();
                        isPreviousBreath=false;
                    }
                    Log.i(LogEvent.PAGE_CHANGE, "FRAGMENT_ETC");
                    return true;
            }
            return false;
        });

        musicPlayerImage = findViewById(R.id.image_music_thumbnail);
        musicPlayerTitle = findViewById(R.id.text_music_title);
        musicPlayerArtist = findViewById(R.id.text_music_artist);
        musicPlayerPlayBtn = findViewById(R.id.button_play_pause);
        musicPlayerForwardBtn = findViewById(R.id.button_forward);
        musicPlayerRewindBtn = findViewById(R.id.button_rewind);
        musicPlayerCard = findViewById(R.id.card_music_player);

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

        musicPlayerCard.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), PlaylistActivity.class);
            startActivity(intent);
        });

        registerBroadcast();
        updateMusicUI();

        //MusicPlayerNotification.notify(getApplicationContext(), "asdfg", 1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AudioApplication.getInstance().getServiceInterface().getIsHomePlayed()) {
            AudioApplication.getInstance().getServiceInterface().play();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (AudioApplication.getInstance().getServiceInterface().getIsHomePlayed()&&!isCurrentEtc) {
            AudioApplication.getInstance().getServiceInterface().pause();
        }
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
            if(music.getTitle().contains("(HOME)")||music.getTitle().contains("(ASMR)")){
                musicPlayerTitle.setText(music.getTitle().substring(7));
                musicPlayerArtist.setVisibility(View.GONE);
            }else {
                musicPlayerTitle.setText(music.getTitle());
                musicPlayerArtist.setVisibility(View.VISIBLE);
            }
            musicPlayerArtist.setText(music.getArtist());
        } else {
            musicPlayerImage.setImageResource(R.drawable.img_music);
            musicPlayerTitle.setText("재생중인 음악이 없습니다");
            musicPlayerArtist.setVisibility(View.GONE);
        }
        if (AudioApplication.getInstance().getServiceInterface().isPlaying()
                && !AudioApplication.getInstance().getServiceInterface().getIsHomePlayed()) {
            musicPlayerPlayBtn.setImageResource(R.drawable.ic_pause);
        } else {
            musicPlayerPlayBtn.setImageResource(R.drawable.ic_play);
        }
        if(AudioApplication.getInstance().getServiceInterface().getPlayListSize()==0
        ||AudioApplication.getInstance().getServiceInterface().getIsHomePlayed()){
            musicPlayerCard.setVisibility(View.INVISIBLE);
        }else{
            musicPlayerCard.setVisibility(View.VISIBLE);
        }
    }

    public void fetchMusicList() {
        ShimDatabase database = ShimDatabase.getInstance(getApplicationContext());
        ShimService service = ServiceGenerator.create();
        service.getAsmrList().enqueue(new Callback<AsmrListResponse>() {
            @Override
            public void onResponse(@NotNull Call<AsmrListResponse> call, @NotNull Response<AsmrListResponse> response) {
                List<Asmr> asmrList = Objects.requireNonNull(response.body()).getData();
                int index = 0;
                for (Asmr asmr : asmrList) {
                    asmr.setOrder(index++);
                    asmr.setThumbnail("https://s3.ap-northeast-2.amazonaws.com/shim-sleep/" + asmr.getThumbnail());
                    asmr.setUrl("https://s3.ap-northeast-2.amazonaws.com/shim-sleep/" + asmr.getUrl());
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
                    music.setTitle("(HOME)"+music.getTitle());
                    music.setThumbnail("https://s3.ap-northeast-2.amazonaws.com/shim-main/" + music.getThumbnail());
                    music.setUrl("https://s3.ap-northeast-2.amazonaws.com/shim-main/" + music.getUrl());
                }
                mainList.clear();
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
                int index = 0;
                for (Music music : musicList) {
                    music.setOrder(index++);
                    music.setThumbnail("https://s3.ap-northeast-2.amazonaws.com/shim-music/" + music.getThumbnail());
                    music.setUrl("https://s3.ap-northeast-2.amazonaws.com/shim-music/" + music.getUrl());
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
        if (PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .getBoolean("fullscreen", false)) {
            hideSystemUI();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void hideSystemUI() {
        // Enables sticky immersive mode.
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
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
