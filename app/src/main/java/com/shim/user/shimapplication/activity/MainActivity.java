package com.shim.user.shimapplication.activity;

import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;

import androidx.annotation.NonNull;
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
import com.shim.user.shimapplication.data.retrofit.ShimService;
import com.shim.user.shimapplication.fragment.EtcFragment;
import com.shim.user.shimapplication.fragment.HomeFragment;
import com.shim.user.shimapplication.fragment.MusicFragment;
import com.shim.user.shimapplication.fragment.SleepFragment;
import com.shim.user.shimapplication.fragment.VideoFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    public static ArrayList<Music> musicPlayList = new ArrayList<>();
    // 재생목록 추가를 위한 Music Play List

    public static final List<Main> mainList = new ArrayList<>();
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
        userID = Settings.Secure.getString(getApplicationContext()
                .getContentResolver(), Settings.Secure.ANDROID_ID);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://52.78.106.14/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ShimService shimService = retrofit.create(ShimService.class);
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

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, homeFragment).commitAllowingStateLoss();

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}
