package co.shimm.app.fragment;

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
import androidx.annotation.Nullable;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import co.shimm.app.R;
import co.shimm.app.media.AudioApplication;
import co.shimm.app.retrofit.ServiceGenerator;
import co.shimm.app.retrofit.ShimService;
import co.shimm.app.retrofit.request.MusicFavoriteRequest;
import co.shimm.app.retrofit.response.BaseResponse;
import co.shimm.app.room.Music;
import co.shimm.app.room.MusicDao;
import co.shimm.app.room.ShimDatabase;
import co.shimm.app.util.logging.Log;
import co.shimm.app.util.logging.LogEvent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static co.shimm.app.activity.MainActivity.musicPlayList;
import static co.shimm.app.activity.MainActivity.showPlayer;
import static co.shimm.app.fragment.HomeFragment.isOtherMusicPlayed;

public class MusicFragment extends Fragment {
    private static RecyclerView[] recyclerViews = new RecyclerView[]{null, null, null, null, null};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_music, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager());
        ViewPager pager = view.findViewById(R.id.pager_music);
        pager.setAdapter(pagerAdapter);
        TabLayout tabLayout = view.findViewById(R.id.tabs_music);
        tabLayout.setupWithViewPager(pager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                RecyclerView recyclerView = recyclerViews[position];
                if (recyclerView != null) {
                    // noinspection unchecked
                    new Page.RecycleViewUpdater((Page.MusicCardAdapter) recyclerView.getAdapter()).execute(getContext(), position);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    public static class Page extends Fragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_music_page, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            Bundle args = getArguments();
            int position = Objects.requireNonNull(args).getInt("position");
            MusicCardAdapter recyclerViewAdapter = new MusicCardAdapter();
            SwipeRefreshLayout refreshLayout = view.findViewById(R.id.refresher_music);
            refreshLayout.setOnRefreshListener(() -> {
                // noinspection unchecked
                new RecycleViewUpdater(recyclerViewAdapter).execute(getContext(), position);
                refreshLayout.setRefreshing(false);
            });
            RecyclerView recyclerView = view.findViewById(R.id.list_music);
            recyclerView.setAdapter(recyclerViewAdapter);
            recyclerViews[position] = recyclerView;
            // noinspection unchecked
            new RecycleViewUpdater(recyclerViewAdapter).execute(getContext(), position);
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
                        Log.i(LogEvent.PAGE_CHANGE, "FRAGMENT_MUSIC_ALL");
                        break;
                    case 1:
                        adapter.setItem((ArrayList<Music>) dao.getFavorites());
                        Log.i(LogEvent.PAGE_CHANGE, "FRAGMENT_MUSIC_FAVORITE");
                        break;
                    case 2:
                        adapter.setItem((ArrayList<Music>) dao.findByCategory("relax"));
                        Log.i(LogEvent.PAGE_CHANGE, "FRAGMENT_MUSIC_RELAX");
                        break;
                    case 3:
                        adapter.setItem((ArrayList<Music>) dao.findByCategory("focus"));
                        Log.i(LogEvent.PAGE_CHANGE, "FRAGMENT_MUSIC_FOCUS");
                        break;
                    case 4:
                        adapter.setItem((ArrayList<Music>) dao.findByCategory("classic"));
                        Log.i(LogEvent.PAGE_CHANGE, "FRAGMENT_MUSIC_CLASSIC");
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
                int seconds = (music.getDuration() / 1000) % 60;
                long minutes = ((music.getDuration() - seconds) / 1000) / 60;
                String string = String.format("%d:%02d", minutes, seconds);
                holder.duration.setText(string);
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
                        musicPlayList.add(music);
                        Log.i(LogEvent.PLAYLIST_ADD_MUSIC, String.valueOf(music.getId()));
                        switch (menuItem.getItemId()) {
                            case R.id.option_play_now:
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
                TextView duration;
                ImageButton actionToggle;
                ImageButton actionClick;

                ViewHolder(@NonNull View itemView) {
                    super(itemView);
                    thumbnail = itemView.findViewById(R.id.image_music_thumbnail);
                    title = itemView.findViewById(R.id.text_music_title);
                    duration = itemView.findViewById(R.id.text_music_duration);
                    actionToggle = itemView.findViewById(R.id.button_toggle_favorite);
                    actionClick = itemView.findViewById(R.id.button_show_play_options);
                }
            }
        }
    }

    private class PagerAdapter extends FragmentPagerAdapter {
        PagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            Fragment page = new Page();
            Bundle args = new Bundle();
            args.putInt("position", position);
            page.setArguments(args);
            return page;
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.tab_music_all);
                case 1:
                    return getString(R.string.tab_music_favorite);
                case 2:
                    return getString(R.string.tab_music_relax);
                case 3:
                    return getString(R.string.tab_music_focus);
                case 4:
                    return getString(R.string.tab_music_classic);
                default:
                    return "Unknown";
            }
        }
    }
}
