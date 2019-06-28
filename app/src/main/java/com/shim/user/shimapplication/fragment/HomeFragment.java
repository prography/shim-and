package com.shim.user.shimapplication.fragment;

import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shim.user.shimapplication.R;

import java.io.IOException;

import me.relex.circleindicator.CircleIndicator;

import static com.shim.user.shimapplication.activity.MainActivity.isPlaying;
import static com.shim.user.shimapplication.activity.MainActivity.mainList;
import static com.shim.user.shimapplication.activity.MainActivity.mp;
import static com.shim.user.shimapplication.activity.MainActivity.playingPosition;

public class HomeFragment extends Fragment {

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        ViewPager viewPager = view.findViewById(R.id.home_view_pager);
        viewPager.setAdapter(new PagerAdapter(getChildFragmentManager()));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(final int position) {
                MusicResetTask resetTask = new MusicResetTask();
                resetTask.execute(position);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        CircleIndicator indicator = view.findViewById(R.id.page_indicator);
        indicator.setViewPager(viewPager);
        return view;
    }

    public static class Page extends Fragment {

        public static Page newInstance(int position) {
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            Page page = new Page();
            page.setArguments(bundle);
            return page;
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
            int position = getArguments() != null ? getArguments().getInt("position") : 0;
            View view = inflater.inflate(R.layout.fragment_home_page, container, false);
            ImageView mainFirstImage = view.findViewById(R.id.main_first_image);
            if (mainList.size() != 0) {
                Glide.with(getContext())
                        .load("https://s3.ap-northeast-2.amazonaws.com/shim-main/" + mainList.get(position).getMain_picture())
                        .into(mainFirstImage);
            }
            if (!isPlaying) {
                mp.reset();
                mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                if (mainList.size() != 0) {
                    try {
                        mp.setDataSource("https://s3.ap-northeast-2.amazonaws.com/shim-main/" + mainList.get(position).getMain_music());
                        mp.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mp.setLooping(true);
                    mp.start();
                }
                isPlaying = true;
            }
            return view;
        }
    }

    private static class PagerAdapter extends FragmentPagerAdapter {
        private static int ITEM_COUNT = 3;

        PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position >= 0 && position < ITEM_COUNT) {
                return Page.newInstance(position);
            } else {
                return null;
            }
        }

        @Override
        public int getCount() {
            return ITEM_COUNT;
        }
    }

    private class MusicResetTask extends AsyncTask<Integer, Void, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... positions) {
            mp.reset();
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            if (mainList.size() != 0) {
                try {
                    mp.setDataSource("https://s3.ap-northeast-2.amazonaws.com/shim-main/" + mainList.get(positions[0]).getMain_music());
                    mp.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mp.setLooping(true);
                mp.start();
                isPlaying = true;
                playingPosition = 0;
            }
            return 0;
        }
    }
}
