package com.shim.user.shimapplication.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.shim.user.shimapplication.R;
import com.shim.user.shimapplication.data.FavoriteRequest;
import com.shim.user.shimapplication.data.FavoriteResponse;
import com.shim.user.shimapplication.data.Media.AudioApplication;
import com.shim.user.shimapplication.data.handler.FavoriteHandler;
import com.shim.user.shimapplication.data.repository.ShimRepo;
import com.shim.user.shimapplication.retrofit.ServiceGenerator;
import com.shim.user.shimapplication.retrofit.ShimService;
import com.shim.user.shimapplication.room.Music;
import com.shim.user.shimapplication.room.MusicDao;
import com.shim.user.shimapplication.room.ShimDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.shim.user.shimapplication.activity.MainActivity.musicPlayList;
import static com.shim.user.shimapplication.activity.MainActivity.showPlayer;
import static com.shim.user.shimapplication.fragment.HomeFragment.isOtherMusicPlayed;

public class MusicFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        MusicCardAdapter recyclerViewAdapter = new MusicCardAdapter();
        RecyclerView recyclerView = view.findViewById(R.id.list_music);
        recyclerView.setAdapter(recyclerViewAdapter);
        // noinspection unchecked
        new RecycleViewUpdater(recyclerViewAdapter).execute(getContext(), 0);
        TabLayout tabLayout = view.findViewById(R.id.tabs_music);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // noinspection unchecked
                new RecycleViewUpdater(recyclerViewAdapter).execute(getContext(), tab.getPosition());
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

    private static class RecycleViewUpdater extends AsyncTask {
        private final MusicCardAdapter adapter;

        RecycleViewUpdater(MusicCardAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        protected Void doInBackground(Object... params) {
            MusicDao dao = ShimDatabase.getInstance((Context) params[0]).getMusicDao();
            int position = (int) params[1];
            switch (position) {
                case 0:
                    adapter.setItem((ArrayList<Music>) dao.getAll());
                    break;
                case 1:
                    adapter.setItem((ArrayList<Music>) dao.getFavorites());
                    break;
                case 2:
                    adapter.setItem((ArrayList<Music>) dao.findByCategory("relax"));
                    break;
                case 3:
                    adapter.setItem((ArrayList<Music>) dao.findByCategory("focus"));
                    break;
                case 4:
                    adapter.setItem((ArrayList<Music>) dao.findByCategory("classic"));
            }
            adapter.setTabPosition(position);
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            adapter.notifyDataSetChanged();
        }
    }

    private class MusicCardAdapter extends RecyclerView.Adapter<MusicCardAdapter.ViewHolder> {
        private ArrayList<Music> musicList = new ArrayList<>();
        private int tabPosition;
        ShimRepo shimRepo;
        private List<com.shim.user.shimapplication.data.Music> forHomeCheckList = new ArrayList<>();

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.card_music, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            @SuppressLint("HardwareIds") String userId = Settings.Secure.getString(Objects.requireNonNull(getContext()).getContentResolver(), Settings.Secure.ANDROID_ID);
            ShimService service = ServiceGenerator.create();
            Music music = musicList.get(position);
            Glide.with(holder.itemView.getContext())
                    .load(music.getThumbnail())
                    .into(holder.thumbnail);
            holder.title.setText(music.getTitle());
            holder.actionToggle.setBackgroundResource(music.isFavorite() ? R.drawable.ic_favorite : R.drawable.ic_favorite_border);

            FavoriteHandler favoriteHandler = new FavoriteHandler() {
                @Override
                public void onSuccessSendFavorite(FavoriteResponse response) {

                }

                @Override
                public void onFailure(Throwable t) {

                }
            };
            shimRepo = new ShimRepo(favoriteHandler);

            holder.actionToggle.setOnClickListener(view -> {
                boolean favorite = music.isFavorite();
                holder.actionToggle.setBackgroundResource(favorite ? R.drawable.ic_favorite_border : R.drawable.ic_favorite);
                FavoriteRequest request = new FavoriteRequest(userId, music.getId(), favorite);

                shimRepo.requestFavorite(request);

                music.setFavorite(!favorite);
                new Thread(() -> {
                    ShimDatabase.getInstance(getContext()).getMusicDao().update(music);
                    // noinspection unchecked
                    new RecycleViewUpdater(this).execute(getContext(), tabPosition);
                }).start();
            });
            holder.actionClick.setOnClickListener(view -> {
                PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.options_music_play, popupMenu.getMenu());
                com.shim.user.shimapplication.data.Music addingMusic
                        = new com.shim.user.shimapplication.data.Music();
                popupMenu.setOnMenuItemClickListener(menuItem -> {
                    switch (menuItem.getItemId()) {

                        case R.id.option_play_now:
                            addingMusic.setMusic_id(music.getId());
                            addingMusic.setMusic_music("https://s3.ap-northeast-2.amazonaws.com/shim-music/"+music.getUrl());
                            addingMusic.setMusic_my(music.isFavorite());
                            addingMusic.setMusic_name(music.getTitle());
                            addingMusic.setMusic_picture(music.getThumbnail());
                            musicPlayList.add(addingMusic);
                            if(AudioApplication.getInstance().getServiceInterface().getIsHomePlayed()==true){
                                AudioApplication.getInstance().getServiceInterface().stop();
                                AudioApplication.getInstance().getServiceInterface().setPlayList((ArrayList<com.shim.user.shimapplication.data.Music>) forHomeCheckList);
                                AudioApplication.getInstance().getServiceInterface().setIsHomePlayed(false);
                                isOtherMusicPlayed = false;
                                showPlayer();
                            }
                            AudioApplication.getInstance().getServiceInterface().setPlayList(musicPlayList);
                            AudioApplication.getInstance().getServiceInterface().play(musicPlayList.size()-1);
                            return true;
                        case R.id.option_add_playlist:
                            addingMusic.setMusic_id(music.getId());
                            addingMusic.setMusic_music("https://s3.ap-northeast-2.amazonaws.com/shim-music/"+music.getUrl());
                            addingMusic.setMusic_my(music.isFavorite());
                            addingMusic.setMusic_name(music.getTitle());
                            addingMusic.setMusic_picture(music.getThumbnail());
                            musicPlayList.add(addingMusic);
                            if(AudioApplication.getInstance().getServiceInterface().getIsHomePlayed()==true){

                            }else {
                                AudioApplication.getInstance().getServiceInterface().setIsHomePlayed(false);
                                isOtherMusicPlayed = false;
                                showPlayer();
                                AudioApplication.getInstance().getServiceInterface().setPlayList(musicPlayList);
                            }
                            return true;
                    }
                    return false;
                });
                popupMenu.show();
            });
        }

        @Override
        public int getItemCount() {
            return musicList.size();
        }

        void setItem(ArrayList<Music> musicList) {
            this.musicList = musicList;
        }

        public void setTabPosition(int tabPosition) {
            this.tabPosition = tabPosition;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView thumbnail;
            TextView title;
            ImageButton actionToggle;
            ImageButton actionClick;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                thumbnail = itemView.findViewById(R.id.image_music_thumbnail);
                title = itemView.findViewById(R.id.text_music_title);
                actionToggle = itemView.findViewById(R.id.button_toggle_favorite);
                actionClick = itemView.findViewById(R.id.button_show_play_options);
            }
        }
    }
}
