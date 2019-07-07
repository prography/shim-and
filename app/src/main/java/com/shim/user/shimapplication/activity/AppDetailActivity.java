package com.shim.user.shimapplication.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.shim.user.shimapplication.BuildConfig;
import com.shim.user.shimapplication.R;

public class AppDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_detail);
        TextView versionText = findViewById(R.id.text_app_version);
        versionText.setText("App Version : " + BuildConfig.VERSION_NAME);
    }
}
