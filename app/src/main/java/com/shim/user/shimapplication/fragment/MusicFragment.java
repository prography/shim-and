package com.shim.user.shimapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.shim.user.shimapplication.R;
import com.shim.user.shimapplication.adapter.MusicAdapter;
import com.shim.user.shimapplication.data.Music;
import com.shim.user.shimapplication.data.handler.ShowMusicHandler;
import com.shim.user.shimapplication.data.repository.ShimRepo;

import java.util.ArrayList;
import java.util.List;

public class MusicFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        ArrayList<Music> musicList = new ArrayList<>();
        MusicAdapter musicAdapter = new MusicAdapter(getContext(), musicList, 1);
        ShimRepo shimRepo = new ShimRepo(new ShowMusicHandler() {
            @Override
            public void onSuccessShowMusic(List<Music> arr) {
                musicAdapter.setItem(arr);
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
        shimRepo.showMusic("all");
        RecyclerView recyclerView = view.findViewById(R.id.list_music);
        recyclerView.setAdapter(musicAdapter);
        TabLayout tabLayout = view.findViewById(R.id.tabs_music);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        shimRepo.showMusic("all");
                        break;
                    case 1:
                        shimRepo.showMusic("my");
                        break;
                    case 2:
                        shimRepo.showMusic("relax");
                        break;
                    case 3:
                        shimRepo.showMusic("focus");
                        break;
                    case 4:
                        shimRepo.showMusic("classic");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        return view;
    }
}
