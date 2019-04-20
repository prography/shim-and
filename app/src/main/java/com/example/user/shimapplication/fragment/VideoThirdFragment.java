package com.example.user.shimapplication.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.shimapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoThirdFragment extends Fragment {


    public static VideoThirdFragment newInstance() {
        Bundle args = new Bundle();
        VideoThirdFragment fragment = new VideoThirdFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video_third, container, false);
    }

}
