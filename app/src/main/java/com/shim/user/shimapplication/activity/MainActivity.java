package com.shim.user.shimapplication.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.shim.user.shimapplication.data.Main;
import com.shim.user.shimapplication.data.ShowMainResponse;
import com.shim.user.shimapplication.data.handler.ShowMainHandler;
import com.shim.user.shimapplication.data.repository.ShimRepo;
import com.shim.user.shimapplication.data.retrofit.RetrofitClient;
import com.shim.user.shimapplication.data.retrofit.ShimService;
import com.shim.user.shimapplication.fragment.EtcFragment;
import com.shim.user.shimapplication.fragment.HomeFragment;
import com.shim.user.shimapplication.fragment.MusicFragment;
import com.shim.user.shimapplication.fragment.SleepFragment;
import com.shim.user.shimapplication.fragment.VideoFragment;
import com.shim.user.shimapplication.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.shim.user.shimapplication.fragment.MusicFirstFragment.musicFirstAdapter;
import static com.shim.user.shimapplication.fragment.MusicFirstFragment.musicFirstList;
import static com.shim.user.shimapplication.fragment.MusicFourthFragment.musicFourthAdapter;
import static com.shim.user.shimapplication.fragment.MusicFourthFragment.musicFourthList;
import static com.shim.user.shimapplication.fragment.MusicSecondFragment.musicSecondAdapter;
import static com.shim.user.shimapplication.fragment.MusicSecondFragment.musicSecondList;
import static com.shim.user.shimapplication.fragment.MusicThirdFragment.musicThirdAdapter;
import static com.shim.user.shimapplication.fragment.MusicThirdFragment.musicThirdList;
import static com.shim.user.shimapplication.fragment.SleepFragment.sleepAdapter;
import static com.shim.user.shimapplication.fragment.SleepFragment.sleepExtendList;


public class MainActivity extends AppCompatActivity {
    public static MediaPlayer mp;
    int pos;
    public static boolean isPlaying = false;
    public static int playingPosition=-1;
    public static int playingIndex=-1;

    public static String userID;

    public static final List<Main> mainList = new ArrayList<>();
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

        //mp = MediaPlayer.create(getApplicationContext(), null);
        //mp.setLooping(false);
        // if true , 반복재생

        /*
        ShowMainHandler showMainHandler = new ShowMainHandler() {
            @Override
            public void onSuccessShowMain(List<Main> arr) {
                for(int i=0;i<arr.size(); i++){
                    mainList.add(new Main(arr.get(i)));
                }
            }

            @Override
            public void onFailure(Throwable t) {
            }
        };

        shimRepo = new ShimRepo(showMainHandler);
        shimRepo.showMain();
        */

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://52.78.106.14/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ShimService shimService = retrofit.create(ShimService.class);
        shimService.showMain().enqueue(new Callback<ShowMainResponse>() {
            @Override
            public void onResponse(Call<ShowMainResponse> call, Response<ShowMainResponse> response) {
                for(int i=0;i<response.body().getArr().size(); i++){
                    mainList.add(new Main(response.body().getArr().get(i)));
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

        mp = new MediaPlayer();
        mp.setLooping(true);

    }

    public static void pauseButton(){
        switch (playingPosition) {
            case 1:
                for (int i = 0; i < sleepExtendList.size(); i++) {
                    sleepExtendList.get(i).setButton_pushed(0);
                }
                sleepAdapter.setItem(sleepExtendList);
                break;
            case 2:
                for (int i = 0; i < musicFirstList.size(); i++) {
                    musicFirstList.get(i).setButton_pushed(0);
                }
                musicFirstAdapter.setItem(musicFirstList);
                break;
            case 3:
                for (int i=0;i<musicSecondList.size();i++){
                    musicSecondList.get(i).setButton_pushed(0);
                }
                musicSecondAdapter.setItem(musicSecondList);
                break;
            case 4:
                for (int i=0;i<musicThirdList.size();i++){
                    musicThirdList.get(i).setButton_pushed(0);
                }
                musicThirdAdapter.setItem(musicThirdList);
                break;
            case 5:
                for (int i=0;i<musicFourthList.size();i++){
                    musicFourthList.get(i).setButton_pushed(0);
                }
                musicFourthAdapter.setItem(musicFourthList);
                break;
            default:
                break;
        }
        playingPosition=-1;
    }

    public static void changeButton(int position, int index){
        if(playingPosition!=position) {
            switch (playingPosition) {
                case 1:
                    for (int i = 0; i < sleepExtendList.size(); i++) {
                        sleepExtendList.get(i).setButton_pushed(0);
                    }
                    sleepAdapter.setItem(sleepExtendList);
                    break;
                case 2:
                    for (int i = 0; i < musicFirstList.size(); i++) {
                        musicFirstList.get(i).setButton_pushed(0);
                    }
                    musicFirstAdapter.setItem(musicFirstList);
                    break;
                case 3:
                    for (int i=0;i<musicSecondList.size();i++){
                        musicSecondList.get(i).setButton_pushed(0);
                    }
                    musicSecondAdapter.setItem(musicSecondList);
                    break;
                case 4:
                    for (int i=0;i<musicThirdList.size();i++){
                        musicThirdList.get(i).setButton_pushed(0);
                    }
                    musicThirdAdapter.setItem(musicThirdList);
                    break;
                case 5:
                    for (int i=0;i<musicFourthList.size();i++){
                        musicFourthList.get(i).setButton_pushed(0);
                    }
                    musicFourthAdapter.setItem(musicFourthList);
                    break;
                    default:
                        break;
            }
        }

        switch(position) {
            case 1:
                for(int i=0; i<sleepExtendList.size(); i++){
                    if(i!=index){
                        sleepExtendList.get(i).setButton_pushed(0);
                    }else{
                        sleepExtendList.get(i).setButton_pushed(1);
                    }
                }
                sleepAdapter.setItem(sleepExtendList);
                break;
            case 2:
                for(int i=0; i<musicFirstList.size(); i++){
                    if(i!=index){
                        musicFirstList.get(i).setButton_pushed(0);
                    }else{
                        musicFirstList.get(i).setButton_pushed(1);
                    }
                }
                musicFirstAdapter.setItem(musicFirstList);
                break;
            case 3:
                for(int i=0; i<musicSecondList.size(); i++){
                    if(i!=index){
                        musicSecondList.get(i).setButton_pushed(0);
                    }else{
                        musicSecondList.get(i).setButton_pushed(1);
                    }
                }
                musicSecondAdapter.setItem(musicSecondList);
                break;
            case 4:
                for(int i=0; i<musicThirdList.size(); i++){
                    if(i!=index){
                        musicThirdList.get(i).setButton_pushed(0);
                    }else{
                        musicThirdList.get(i).setButton_pushed(1);
                    }
                }
                musicThirdAdapter.setItem(musicThirdList);
                break;
            case 5:
                for(int i=0; i<musicFourthList.size(); i++){
                    if(i!=index){
                        musicFourthList.get(i).setButton_pushed(0);
                    }else{
                        musicFourthList.get(i).setButton_pushed(1);
                    }
                }
                musicFourthAdapter.setItem(musicFourthList);
                break;
            default:
                break;
        }
    }
}
