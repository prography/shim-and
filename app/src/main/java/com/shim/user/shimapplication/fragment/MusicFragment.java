package com.shim.user.shimapplication.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shim.user.shimapplication.R;
import com.shim.user.shimapplication.adapter.MusicAdapter;
import com.shim.user.shimapplication.data.Music;
import com.shim.user.shimapplication.data.MusicExtend;
import com.shim.user.shimapplication.data.handler.ShowMusicHandler;
import com.shim.user.shimapplication.data.repository.ShimRepo;

import java.util.ArrayList;
import java.util.List;

public class MusicFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);

        ViewPager viewPager = view.findViewById(R.id.music_view_pager);
        viewPager.setAdapter(new PagerAdapter(getChildFragmentManager()));

        TabLayout musicTab = view.findViewById(R.id.music_tabs);
        musicTab.setupWithViewPager(viewPager);
        musicTab.setSelectedTabIndicatorColor(getResources().getColor(R.color.white));

        LinearLayout tabOne = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        TextView tabOneText = tabOne.findViewById(R.id.text_view);
        tabOneText.setText("전체");
        musicTab.getTabAt(0).setCustomView(tabOne);

        LinearLayout tabTwo = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        TextView tabTwoText = tabTwo.findViewById(R.id.text_view);
        tabTwoText.setText("수면");
        musicTab.getTabAt(1).setCustomView(tabTwo);

        LinearLayout tabThree = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        TextView tabThreeText = tabThree.findViewById(R.id.text_view);
        tabThreeText.setText("악기");
        musicTab.getTabAt(2).setCustomView(tabThree);

        LinearLayout tabFour = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        TextView tabFourText = tabFour.findViewById(R.id.text_view);
        tabFourText.setText("자연");
        musicTab.getTabAt(3).setCustomView(tabFour);

        return view;
    }

    public static class Page extends Fragment {
        public static List<MusicExtend> musicList = new ArrayList<>();

        public static Page newInstance(int position) {
            Bundle args = new Bundle();
            args.putInt("position", position);
            Page page = new Page();
            page.setArguments(args);
            return page;
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            int position = getArguments() != null ? getArguments().getInt("position") : 0;
            View view = inflater.inflate(R.layout.fragment_music_page, container, false);
            final MusicAdapter musicAdapter = new MusicAdapter(getContext(), musicList, position);
            RecyclerView recyclerView = view.findViewById(R.id.music_all_recycler_container);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
            recyclerView.setAdapter(musicAdapter);

            ShowMusicHandler showMusicHandler = new ShowMusicHandler() {
                @Override
                public void onSuccessShowMusic(List<Music> arr) {
                    int remember = -1;
                    boolean check = false;
                    for (int i = 0; i < musicList.size(); i++) {
                        if (musicList.get(i).getButton_pushed() == 1) {
                            check = true;
                            remember = i;
                        }
                    }
                    musicList.clear();
                    for (int i = 0; i < arr.size(); i++) {
                        MusicExtend musicExtend = new MusicExtend();
                        musicExtend.setMusic_id(arr.get(i).getMusic_id());
                        musicExtend.setMusic_music(arr.get(i).getMusic_music());
                        musicExtend.setMusic_name(arr.get(i).getMusic_name());
                        musicExtend.setMusic_picture(arr.get(i).getMusic_picture());
                        if (i == remember && check) {
                            musicExtend.setButton_pushed(1);
                        }
                        musicList.add(musicExtend);
                    }
                    musicAdapter.setItem(musicList);
                }

                @Override
                public void onFailure(Throwable t) {

                }
            };
            ShimRepo shimRepo = new ShimRepo(showMusicHandler);
            shimRepo.showMusic("all");
            return view;
        }
    }

    private static class PagerAdapter extends FragmentPagerAdapter {
        private static int ITEM_COUNT = 4;

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

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "전체";
                case 1:
                    return "수면";
                case 2:
                    return "악기";
                case 3:
                    return "자연";
                default:
                    return null;
            }
        }
    }
}
