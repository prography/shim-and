package co.shimm.app.activity;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import co.shimm.app.R;
import co.shimm.app.util.Theme;

import java.util.Objects;

import static co.shimm.app.activity.MainActivity.isChangedTheme;
import static co.shimm.app.activity.MainActivity.isCurrentEtc;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        isCurrentEtc = false;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.preferences, rootKey);
            Preference themePreference = findPreference("theme");
            Objects.requireNonNull(themePreference)
                    .setOnPreferenceChangeListener((preference, newValue) -> {
                        Theme.apply(newValue.toString());
                        isChangedTheme=true;
                        return true;
                    });
        }
    }
}