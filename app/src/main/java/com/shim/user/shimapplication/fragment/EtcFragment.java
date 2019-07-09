package com.shim.user.shimapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.shim.user.shimapplication.R;
import com.shim.user.shimapplication.activity.AppDetailActivity;
import com.shim.user.shimapplication.activity.FeedbackActivity;
import com.shim.user.shimapplication.activity.SettingsActivity;

import org.jetbrains.annotations.NotNull;


public class EtcFragment extends Fragment {
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_etc, container, false);
        view.findViewById(R.id.button_show_feedback_input)
                .setOnClickListener(v -> startActivity(new Intent(getActivity(), FeedbackActivity.class)));
        view.findViewById(R.id.button_show_app_info)
                .setOnClickListener(v -> startActivity(new Intent(getActivity(), AppDetailActivity.class)));
        view.findViewById(R.id.button_show_settings)
                .setOnClickListener(v -> startActivity(new Intent(getActivity(), SettingsActivity.class)));
        return view;
    }
}
