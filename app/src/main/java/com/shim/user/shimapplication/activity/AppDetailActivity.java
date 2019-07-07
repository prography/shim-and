package com.shim.user.shimapplication.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.shim.user.shimapplication.R;

public class AppDetailActivity extends AppCompatActivity {
    private TextView versionText;
    private PackageInfo packageInfo;
    private String version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_detail);

        versionText = (TextView)findViewById(R.id.text_app_version);

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
