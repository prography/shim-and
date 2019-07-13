package co.shimm.app.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import co.shimm.app.BuildConfig;
import co.shimm.app.R;

import static co.shimm.app.activity.MainActivity.isCurrentEtc;

public class AppDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_detail);
        TextView versionText = findViewById(R.id.text_app_version);
        versionText.setText("App Version : " + BuildConfig.VERSION_NAME);

        isCurrentEtc = false;
    }
}
