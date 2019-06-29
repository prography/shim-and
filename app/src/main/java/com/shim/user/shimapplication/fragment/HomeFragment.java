package com.shim.user.shimapplication.fragment;

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
import com.shim.user.shimapplication.data.Media.AudioApplication;
import com.shim.user.shimapplication.data.Music;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

import static com.shim.user.shimapplication.activity.MainActivity.mainList;

public class HomeFragment extends Fragment {
    private static List<Music> homeMusicList = new ArrayList<>();
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
                if (mainList.size() != 0) {
                    homeMusicList.clear();
                    homeMusicList.add(mainList.get(position));
                    AudioApplication.getInstance().getServiceInterface().setPlayList((ArrayList<Music>) homeMusicList);
                    AudioApplication.getInstance().getServiceInterface().playOneMusic();
                }
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
            Bundle args = new Bundle();
            args.putInt("position", position);
            Page page = new Page();
            page.setArguments(args);
            return page;
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
            int position = getArguments() != null ? getArguments().getInt("position") : 0;
            View view = inflater.inflate(R.layout.fragment_home_page, container, false);
            ImageView imageView = view.findViewById(R.id.main_first_image);
            if (mainList.size() != 0) {
                Glide.with(getContext())
                        .load("https://s3.ap-northeast-2.amazonaws.com/shim-main/" + mainList.get(position).getMusic_picture())
                        .into(imageView);
            }
            if (AudioApplication.getInstance().getServiceInterface().isPlaying()) {
                if (mainList.size() != 0) {
                    homeMusicList.clear();
                    homeMusicList.add(mainList.get(position));
                    AudioApplication.getInstance().getServiceInterface().setPlayList((ArrayList<Music>) homeMusicList);
                    AudioApplication.getInstance().getServiceInterface().playOneMusic();
                }
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
        public int getCount() {
            return ITEM_COUNT;
        }

        @Override
        public Fragment getItem(int position) {
            if (position >= 0 && position < ITEM_COUNT) {
                return Page.newInstance(position);
            } else {
                return null;
            }
        }
    }

}