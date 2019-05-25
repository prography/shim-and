package com.shim.user.shimapplication.fragment;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shim.user.shimapplication.R;


public class EtcFragment extends Fragment {
    public static EtcFragment newInstance(){
        return new EtcFragment();
    }
    private TextView versionText;
    private PackageInfo packageInfo;
    private String version;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_etc, container, false);
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

        return view;
    }
}
