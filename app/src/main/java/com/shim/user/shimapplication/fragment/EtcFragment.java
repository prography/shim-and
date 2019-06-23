package com.shim.user.shimapplication.fragment;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

        feedbackIntentButton = (TextView)view.findViewById(R.id.feedback_intent_button);
        detailIntentButton = (TextView)view.findViewById(R.id.detail_intent_button);

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

        /*
        versionText = (TextView)view.findViewById(R.id.etc_version);



        if(getContext()!=null) {
            try {
                packageInfo = getContext().getApplicationContext().getPackageManager()
                        .getPackageInfo(getContext().getApplicationContext().getPackageName(), 0);
                version = packageInfo.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        versionText.setText("App Version : "+version);

        */

        return view;
    }
}
