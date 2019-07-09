package com.shim.user.shimapplication.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.shim.user.shimapplication.R;
import com.shim.user.shimapplication.media.AudioApplication;
import com.shim.user.shimapplication.retrofit.ServiceGenerator;
import com.shim.user.shimapplication.retrofit.ShimService;
import com.shim.user.shimapplication.retrofit.request.MusicFavoriteRequest;
import com.shim.user.shimapplication.retrofit.response.BaseResponse;
import com.shim.user.shimapplication.room.Music;
import com.shim.user.shimapplication.room.MusicDao;
import com.shim.user.shimapplication.room.ShimDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            if (music.isFavorite()) {
                ImageViewCompat.setImageTintList(holder.actionToggle, ColorStateList.valueOf(Color.parseColor("#FF7B7B")));
                holder.actionToggle.setImageResource(R.drawable.ic_favorite);
            } else {
                ImageViewCompat.setImageTintList(holder.actionToggle, ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                holder.actionToggle.setImageResource(R.drawable.ic_favorite_border);
            }
            holder.actionToggle.setOnClickListener(view -> {
                boolean favorite = music.isFavorite();
                service.setMusicFavorite(new MusicFavoriteRequest(userId, music.getId(), favorite)).enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<BaseResponse> call, @NotNull Response<BaseResponse> response) {
                    }

                    @Override
                    public void onFailure(@NotNull Call<BaseResponse> call, @NotNull Throwable t) {
                    }
                });
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
                popupMenu.setOnMenuItemClickListener(menuItem -> {
                    switch (menuItem.getItemId()) {
                        case R.id.option_play_now:
                            musicPlayList.add(music);
                            if (AudioApplication.getInstance().getServiceInterface().getIsHomePlayed()) {
                                AudioApplication.getInstance().getServiceInterface().stop();
                                AudioApplication.getInstance().getServiceInterface().setIsHomePlayed(false);
                                isOtherMusicPlayed = true;
                                showPlayer();
                            }
                            AudioApplication.getInstance().getServiceInterface().setPlayList(musicPlayList);
                            AudioApplication.getInstance().getServiceInterface().play(musicPlayList.size() - 1);
                            return true;
                        case R.id.option_add_playlist:
                            musicPlayList.add(music);
                            if (AudioApplication.getInstance().getServiceInterface().getIsHomePlayed()) {
                                Toast toast = Toast.makeText(getContext(), "재생목록에 추가되었습니다.", Toast.LENGTH_SHORT);
                                toast.show();
                            } else {
                                AudioApplication.getInstance().getServiceInterface().setIsHomePlayed(false);
                                isOtherMusicPlayed = true;
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

        void setTabPosition(int tabPosition) {
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
