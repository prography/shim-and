package com.shim.user.shimapplication.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.shim.user.shimapplication.R;

import static java.security.AccessController.getContext;

public class AppDetailActivity extends AppCompatActivity {
    private TextView versionText;
    private PackageInfo packageInfo;
    private String version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_detail);

        versionText = (TextView)findViewById(R.id.detail_version);

        if(getApplicationContext()!=null){
            try{
                packageInfo= getPackageManager()
                        .getPackageInfo(getPackageName(), 0);
                version = packageInfo.versionName;
            } catch (PackageManager.NameNotFoundException e){
                e.printStackTrace();
            }
        }
        versionText.setText("App Version : " + version);
    }
}
