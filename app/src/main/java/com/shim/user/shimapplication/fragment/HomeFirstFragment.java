package com.shim.user.shimapplication.fragment;

import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.shim.user.shimapplication.R;
import com.shim.user.shimapplication.data.Main;
import com.shim.user.shimapplication.data.handler.ShowMainHandler;
import com.shim.user.shimapplication.data.repository.ShimRepo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.shim.user.shimapplication.activity.MainActivity.mainList;
import static com.shim.user.shimapplication.activity.MainActivity.isPlaying;
import static com.shim.user.shimapplication.activity.MainActivity.mp;

public class HomeFirstFragment extends Fragment {
    private String title;
    private int page;
    private ImageView mainFirstImage;


    // newInstance constructor for creating fragment with arguments
    public static HomeFirstFragment newInstance() {
        HomeFirstFragment fragment = new HomeFirstFragment();
        return fragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_first, container, false);


        mainFirstImage = (ImageView)view.findViewById(R.id.main_first_image);
        if(mainList.size()!=0) {
            Glide.with(getContext())
                    .load("https://s3.ap-northeast-2.amazonaws.com/shim-main/"
                            + mainList.get(0).getMain_picture())
                    .into(mainFirstImage);
        }else{
            Glide.with(getContext())
                    .load("https://s3.ap-northeast-2.amazonaws.com/shim-main/1.jpg")
                    .into(mainFirstImage);
        }

        if(!isPlaying){
            mp.reset();
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            if(mainList.size()!=0) {
                try {
                    mp.setDataSource("https://s3.ap-northeast-2.amazonaws.com/shim-main/"
                            + mainList.get(0).getMain_music());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    mp.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mp.setLooping(true);
                mp.start();
            }
            isPlaying=true;
        }
        /*
        mp.reset();
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        if(mainList.size()!=0) {
            try {
                mp.setDataSource("https://s3.ap-northeast-2.amazonaws.com/shim-main/"
                        + mainList.get(1).getMain_music());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                mp.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mp.start();
        }
        */

        return view;
    }


}
