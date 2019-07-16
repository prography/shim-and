package co.shimm.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import co.shimm.app.R;
import co.shimm.app.activity.AppDetailActivity;
import co.shimm.app.activity.FeedbackActivity;
import co.shimm.app.activity.SettingsActivity;
import co.shimm.app.util.logging.Log;
import co.shimm.app.util.logging.LogEvent;

import static co.shimm.app.activity.MainActivity.isCurrentEtc;


public class EtcFragment extends Fragment {
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_etc, container, false);
        Button feedbackButton = view.findViewById(R.id.button_show_feedback_input);
        Button appInfoButton = view.findViewById(R.id.button_show_app_info);
        Button settingButton = view.findViewById(R.id.button_show_settings);

        feedbackButton.setOnClickListener(view1 -> {
            isCurrentEtc = true;
            Log.i(LogEvent.PAGE_CHANGE, "ACTIVITY_FEEDBACK");
            Intent intent = new Intent(getActivity(), FeedbackActivity.class);
            startActivity(intent);
        });
        appInfoButton.setOnClickListener(view2 -> {
            isCurrentEtc = true;
            Log.i(LogEvent.PAGE_CHANGE, "ACTIVITY_APP_INFO");
            Intent intent = new Intent(getActivity(), AppDetailActivity.class);
            startActivity(intent);
        });
        settingButton.setOnClickListener(view3 -> {
            isCurrentEtc = true;
            Log.i(LogEvent.PAGE_CHANGE, "ACTIVITY_SETTINGS");
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent);
        });
        return view;
    }
}
