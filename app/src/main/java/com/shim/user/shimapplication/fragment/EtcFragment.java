package com.shim.user.shimapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.shim.user.shimapplication.R;
import com.shim.user.shimapplication.activity.AppDetailActivity;
import com.shim.user.shimapplication.activity.FeedbackActivity;


public class EtcFragment extends Fragment {
    public static EtcFragment newInstance(){
        return new EtcFragment();
    }
    private TextView feedbackIntentButton;
    private TextView detailIntentButton;
    //private TextView versionText;
    //private PackageInfo packageInfo;
    //private String version;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_etc, container, false);

        feedbackIntentButton = (TextView)view.findViewById(R.id.button_show_feedback_input);
        detailIntentButton = (TextView)view.findViewById(R.id.button_show_app_info);

        feedbackIntentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FeedbackActivity.class);
                startActivity(intent);
            }
        });
        detailIntentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AppDetailActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
