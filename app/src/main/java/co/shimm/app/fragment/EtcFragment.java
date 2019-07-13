package co.shimm.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import co.shimm.app.R;
import co.shimm.app.activity.AppDetailActivity;
import co.shimm.app.activity.FeedbackActivity;
import co.shimm.app.activity.SettingsActivity;

import org.jetbrains.annotations.NotNull;

import static co.shimm.app.activity.MainActivity.isCurrentEtc;


public class EtcFragment extends Fragment {
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_etc, container, false);
        Button feedbackButton = (Button)view.findViewById(R.id.button_show_feedback_input);
        Button appInfoButton = (Button)view.findViewById(R.id.button_show_app_info);
        Button settingButton = (Button)view.findViewById(R.id.button_show_settings);

        feedbackButton.setOnClickListener(view1 -> {
            isCurrentEtc = true;
            Intent intent = new Intent(getActivity(),FeedbackActivity.class);
            startActivity(intent);
        });
        appInfoButton.setOnClickListener(view2 -> {
            isCurrentEtc = true;
            Intent intent = new Intent(getActivity(),AppDetailActivity.class);
            startActivity(intent);
        });
        settingButton.setOnClickListener(view3 -> {
            isCurrentEtc = true;
            Intent intent = new Intent(getActivity(),SettingsActivity.class);
            startActivity(intent);
        });
        return view;
    }
}
