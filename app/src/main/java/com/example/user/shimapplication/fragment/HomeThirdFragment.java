package com.example.user.shimapplication.fragment;

import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.user.shimapplication.R;
import com.example.user.shimapplication.data.Main;
import com.example.user.shimapplication.data.handler.ShowMainHandler;
import com.example.user.shimapplication.data.repository.ShimRepo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.user.shimapplication.activity.MainActivity.mp;

public class HomeThirdFragment extends Fragment {
    private String title;
    private int page;
    private ImageView mainThirdImage;
    public static List<Main> mainList = new ArrayList<>();
    ShimRepo shimRepo;

    // newInstance constructor for creating fragment with arguments
    public static HomeThirdFragment newInstance() {
        HomeThirdFragment fragment = new HomeThirdFragment();
        return fragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_third, container, false);

        ShowMainHandler showMainHandler = new ShowMainHandler() {
            @Override
            public void onSuccessShowMain(List<Main> arr) {
                //mainList = new ArrayList<>(arr);
                mainList.addAll(arr);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        };
        shimRepo = new ShimRepo(showMainHandler);
        shimRepo.showMain();

        mainThirdImage = (ImageView)view.findViewById(R.id.main_third_image);
        if(mainList.size()!=0) {
            Glide.with(getContext())
                    .load("https://s3.ap-northeast-2.amazonaws.com/shim-main/"
                            + mainList.get(2).getMain_picture())
                    .into(mainThirdImage);
        }else{
            Glide.with(getContext())
                    .load("https://s3.ap-northeast-2.amazonaws.com/shim-main/3.jpg")
                    .into(mainThirdImage);
        }
        mp.reset();
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mp.setDataSource("https://s3.ap-northeast-2.amazonaws.com/shim-main/WalloonLilli.mp3 ");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.start();

        return view;
    }
}
