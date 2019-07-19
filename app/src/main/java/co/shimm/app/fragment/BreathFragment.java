package co.shimm.app.fragment;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import co.shimm.app.R;

import org.jetbrains.annotations.NotNull;

public class BreathFragment extends Fragment {
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_breath, container, false);
        ProgressBar progressBar = view.findViewById(R.id.progress_breath);
        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 0, 1000);
        animation.setDuration(8000);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(ValueAnimator.INFINITE);
        animation.start();
        TextView manual = view.findViewById(R.id.text_breathe_manual);
        Handler textHandler = new Handler(message -> {
            manual.setText(message.obj.toString());
            return true;
        });
        new Thread(() -> {
            try {
                while (true) {
                    textHandler.sendMessage(textHandler.obtainMessage(1, "Breathe In"));
                    Thread.sleep(3333);
                    textHandler.sendMessage(textHandler.obtainMessage(1, "Hold"));
                    Thread.sleep(1334);
                    textHandler.sendMessage(textHandler.obtainMessage(1, "Breathe Out"));
                    Thread.sleep(3333);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        return view;
    }
}
