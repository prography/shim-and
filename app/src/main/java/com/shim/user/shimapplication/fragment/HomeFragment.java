package com.shim.user.shimapplication.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

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
    public static boolean isFirstRunned = true;
    public static boolean isOtherMusicPlayed = false;
    private Music homeMusic;

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
                if (mainList.size() != 0 && isOtherMusicPlayed == false) {
                    homeMusicList.clear();
                    homeMusic = new Music(mainList.get(position).getMain_id(),
                            mainList.get(position).getMain_name(),
                            mainList.get(position).getMain_music(),
                            mainList.get(position).getMain_author(),
                            mainList.get(position).getMain_picture(),
                            false);
                    homeMusicList.add(homeMusic);
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
                        .load("https://s3.ap-northeast-2.amazonaws.com/shim-main/" + mainList.get(position).getMain_picture())
                        .into(imageView);
                Log.d("tag :", mainList.get(position).getMain_picture());
            }

            if (position == 0 && isOtherMusicPlayed == false) {
                homeMusicList.clear();
                Music firstMusic = new Music(mainList.get(0).getMain_id(),
                        mainList.get(0).getMain_name(),
                        mainList.get(0).getMain_music(),
                        mainList.get(0).getMain_author(),
                        mainList.get(0).getMain_picture(),
                        false);
                homeMusicList.add(firstMusic);
                AudioApplication.getInstance().getServiceInterface().setPlayList((ArrayList<Music>) homeMusicList);
                AudioApplication.getInstance().getServiceInterface().playOneMusic();
                isFirstRunned = false;
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