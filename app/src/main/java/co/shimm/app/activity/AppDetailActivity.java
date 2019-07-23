package co.shimm.app.activity;

import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.shimm.app.BuildConfig;
import co.shimm.app.R;

import static co.shimm.app.activity.MainActivity.isCurrentEtc;

public class AppDetailActivity extends AppCompatActivity {
    TextView versionText;
    TextView privacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_detail);
        versionText = findViewById(R.id.text_app_version);
        privacy = findViewById(R.id.text_app_privacy);

        versionText.setText("App Version : " + BuildConfig.VERSION_NAME);

        Linkify.TransformFilter mTransform = new Linkify.TransformFilter() {
            @Override
            public String transformUrl(Matcher matcher, String s) {
                return "";
            }
        };

        Pattern pattern = Pattern.compile("개인정보처리방침");
        Linkify.addLinks(privacy, pattern, "http://prography.org/privacy-policy", null,mTransform);

        isCurrentEtc = false;
    }
}
