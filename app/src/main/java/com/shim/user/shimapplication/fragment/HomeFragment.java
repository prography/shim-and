package com.shim.user.shimapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.shim.user.shimapplication.R;
import com.shim.user.shimapplication.media.AudioApplication;
import com.shim.user.shimapplication.room.Music;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import me.relex.circleindicator.CircleIndicator;

import static com.shim.user.shimapplication.activity.MainActivity.mainList;

public class HomeFragment extends Fragment {
    public static boolean isFirstRunned = true;
    public static boolean isOtherMusicPlayed = false;
    private static List<Music> homeMusicList = new ArrayList<>();

    private PagerAdapter viewPagerAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        viewPagerAdapter = new PagerAdapter(getChildFragmentManager());
        ViewPager viewPager = view.findViewById(R.id.home_view_pager);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(final int position) {
                if (mainList.size() != 0 && isOtherMusicPlayed == false && !isFirstRunned) {
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

    public void notifyMusicListChanged() {
        viewPagerAdapter.notifyDataSetChanged();
    }

    public static class Page extends Fragment {
        static Page newInstance(int position) {
            Bundle args = new Bundle();
            args.putInt("position", position);
            Page page = new Page();
            page.setArguments(args);
            return page;
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_home_page, container, false);
            if (mainList.size() == 3) {
                int position = getArguments() != null ? getArguments().getInt("position") : 0;
                ImageView thumbnail = view.findViewById(R.id.main_first_image);
                Glide.with(Objects.requireNonNull(getContext()))
                        .load(mainList.get(position).getThumbnail())
                        .into(thumbnail);
                if (position == 0 && !isOtherMusicPlayed) {
                    homeMusicList.clear();
                    homeMusicList.add(mainList.get(0));
                    AudioApplication.getInstance().getServiceInterface().setPlayList((ArrayList<Music>) homeMusicList);
                    AudioApplication.getInstance().getServiceInterface().playOneMusic();
                    isFirstRunned = false;
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

        @NotNull
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