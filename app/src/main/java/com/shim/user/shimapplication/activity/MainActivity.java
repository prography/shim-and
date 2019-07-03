package com.shim.user.shimapplication.activity;

import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shim.user.shimapplication.R;
import com.shim.user.shimapplication.data.Main;
import com.shim.user.shimapplication.data.Music;
import com.shim.user.shimapplication.data.ShowMainResponse;
import com.shim.user.shimapplication.data.repository.ShimRepo;
import com.shim.user.shimapplication.fragment.EtcFragment;
import com.shim.user.shimapplication.fragment.HomeFragment;
import com.shim.user.shimapplication.fragment.MusicFragment;
import com.shim.user.shimapplication.fragment.SleepFragment;
import com.shim.user.shimapplication.fragment.VideoFragment;
import com.shim.user.shimapplication.retrofit.ServiceGenerator;
import com.shim.user.shimapplication.retrofit.ShimService;
import com.shim.user.shimapplication.room.MusicDao;
import com.shim.user.shimapplication.room.ShimDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    public static final List<Main> mainList = new ArrayList<>();
    // 재생목록 추가를 위한 Music Play List
    public static ArrayList<Music> musicPlayList = new ArrayList<>();
    public static int playingPosition = -1;
    public static int playingIndex = -1;

    public static String userID;
    int pos;
    ShimRepo shimRepo;


    private FragmentManager fragmentManager = getSupportFragmentManager();

    private HomeFragment homeFragment = new HomeFragment();
    private SleepFragment sleepFragment = new SleepFragment();
    private VideoFragment videoFragment = new VideoFragment();
    private MusicFragment musicFragment = new MusicFragment();
    private EtcFragment etcFragment = new EtcFragment();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = item -> {
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
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userID = Settings.Secure.getString(getApplicationContext()
                .getContentResolver(), Settings.Secure.ANDROID_ID);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);

        ShimService service = ServiceGenerator.create();
        service.getMusicList().enqueue(new Callback<Map>() {
            @Override
            public void onResponse(@NotNull Call<Map> call, @NotNull Response<Map> response) {
                new Thread(() -> {
                    MusicDao dao = ShimDatabase.getInstance(getApplicationContext()).getMusicDao();
                    // noinspection unchecked
                    List<Map> propsList = (List) Objects.requireNonNull(response.body()).get("arr");
                    for (Map props : propsList) {
                        int id = (int) ((double) props.get("music_id")); // DO NOT FIX THE TYPE CASTING
                        String title = (String) props.get("music_name");
                        String artist = (String) props.get("music_author");
                        String category = (String) props.get("music_category");
                        boolean favorite = (boolean) props.get("my");
                        String thumbnail = "https://s3.ap-northeast-2.amazonaws.com/shim-music/" + props.get("music_picture");
                        String url = (String) props.get("music_music");
                        dao.insert(new com.shim.user.shimapplication.room.Music(id, title, artist, category, favorite, thumbnail, url));
                    }
                }).start();
            }

            @Override
            public void onFailure(@NotNull Call<Map> call, @NotNull Throwable t) {
            }
        });
//        service.getHomeMusicList().enqueue(new Callback<Map>() {
//            @Override
//            public void onResponse(@NotNull Call<Map> call, @NotNull Response<Map> response) {
//                ArrayList<com.shim.user.shimapplication.room.Music> musicList = new ArrayList<>();
//                // noinspection unchecked
//                List<Map> propsList = (List) Objects.requireNonNull(response.body()).get("arr");
//                for (Map props : propsList) {
//                    int id = (int) ((double) props.get("main_id")); // DO NOT FIX THE TYPE CASTING
//                    String title = (String) props.get("main_name");
//                    String artist = (String) props.get("main_author");
//                    String category = (String) props.get("main_category");
//                    boolean favorite = false;
//                    String thumbnail = "https://s3.ap-northeast-2.amazonaws.com/shim-music/" + props.get("music_picture");
//                    String url = (String) props.get("main_music");
//                    musicList.add(new com.shim.user.shimapplication.room.Music(id, title, artist, category, favorite, thumbnail, url));
//                }
//            }
//
//            @Override
//            public void onFailure(@NotNull Call<Map> call, @NotNull Throwable t) {
//            }
//        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://52.78.106.14/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        com.shim.user.shimapplication.data.retrofit.ShimService shimService = retrofit.create(com.shim.user.shimapplication.data.retrofit.ShimService.class);
        shimService.showMain().enqueue(new Callback<ShowMainResponse>() {
            @Override
            public void onResponse(Call<ShowMainResponse> call, Response<ShowMainResponse> response) {
                for (int i = 0; i < response.body().getArr().size(); i++) {
                    mainList.add(response.body().getArr().get(i));
                }
            }

            @Override
            public void onFailure(Call<ShowMainResponse> call, Throwable t) {

            }
        });
        shimService.showMain();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        BottomNavigationView navigation = findViewById(R.id.navigation);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, homeFragment).commitAllowingStateLoss();

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
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
