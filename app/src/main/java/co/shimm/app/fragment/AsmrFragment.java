package co.shimm.app.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
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
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;

import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import co.shimm.app.R;
import co.shimm.app.media.AudioApplication;
import co.shimm.app.room.Asmr;
import co.shimm.app.room.AsmrDao;
import co.shimm.app.room.Music;
import co.shimm.app.room.ShimDatabase;
import co.shimm.app.util.logging.Log;
import co.shimm.app.util.logging.LogEvent;

import static co.shimm.app.activity.MainActivity.musicPlayList;
import static co.shimm.app.activity.MainActivity.showPlayer;
import static co.shimm.app.fragment.HomeFragment.isOtherMusicPlayed;

public class AsmrFragment extends Fragment {
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_asmr, container, false);
        AsmrCardAdapter recyclerViewAdapter = new AsmrCardAdapter();
        RecyclerView recyclerView = view.findViewById(R.id.list_asmr);
        recyclerView.setAdapter(recyclerViewAdapter);
        new RecycleViewUpdater(recyclerViewAdapter).execute(getContext());
        return view;
    }

    private static class RecycleViewUpdater extends AsyncTask<Context, Void, Void> {
        private final AsmrCardAdapter adapter;

        RecycleViewUpdater(AsmrCardAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        protected Void doInBackground(Context... params) {
            AsmrDao dao = ShimDatabase.getInstance(params[0]).getAsmrDao();
            adapter.setItem((ArrayList<Asmr>) dao.getAll());
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            adapter.notifyDataSetChanged();
        }
    }

    private class AsmrCardAdapter extends RecyclerView.Adapter<AsmrCardAdapter.ViewHolder> {
        private ArrayList<Asmr> asmrList = new ArrayList<>();

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.card_asmr, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Asmr asmr = asmrList.get(position);
            Glide.with(holder.itemView.getContext())
                    .load(asmr.getThumbnail())
                    .into(holder.thumbnail);
            holder.title.setText(asmr.getTitle());
            int seconds = (asmr.getDuration()/1000)%60;
            long minutes = ((asmr.getDuration()-seconds)/1000)/60;
            String string = String.format("%d:%02d", minutes, seconds);
            holder.duration.setText(string);
            holder.action.setOnClickListener(view -> {
                PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.options_music_play, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(menuItem -> {
                    Music musicLike = new Music.Builder()
                            .setId(asmr.getId())
                            .setTitle("(ASMR) " + asmr.getTitle())
                            .setDuration(asmr.getDuration())
                            .setThumbnail(asmr.getThumbnail())
                            .setUrl(asmr.getUrl())
                            .build();
                    musicPlayList.add(musicLike);
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    JSONArray jsonArray = new JSONArray();
                    for (Music row: musicPlayList){
                        jsonArray.put(row.getTitle());
                    }
                    editor.putString("playlist", jsonArray.toString());
                    editor.apply();
                    Log.i(LogEvent.PLAYLIST_ADD_ASMR, String.valueOf(musicLike.getId()));
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
            return asmrList.size();
        }

        void setItem(ArrayList<Asmr> asmrList) {
            this.asmrList = asmrList;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView thumbnail;
            TextView title;
            TextView duration;
            ImageButton action;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                thumbnail = itemView.findViewById(R.id.image_asmr_thumbnail);
                title = itemView.findViewById(R.id.text_asmr_title);
                duration = itemView.findViewById(R.id.text_asmr_duration);
                action = itemView.findViewById(R.id.button_play_asmr);
            }
        }
    }
}
