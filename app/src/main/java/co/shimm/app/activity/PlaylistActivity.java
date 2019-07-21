package co.shimm.app.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import co.shimm.app.R;
import co.shimm.app.media.AudioApplication;
import co.shimm.app.media.BroadcastActions;
import co.shimm.app.room.Music;
import co.shimm.app.util.logging.Log;
import co.shimm.app.util.logging.LogEvent;

import static co.shimm.app.activity.MainActivity.musicPlayList;

public class PlaylistActivity extends AppCompatActivity {
    public ImageView playerImage;
    public TextView playerTitle;
    public TextView playerArtist;
    public ImageButton playerPlayBtn;
    public ImageButton playerRewindBtn;
    public ImageButton playerForwardBtn;


    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateUI();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        playerImage = findViewById(R.id.player_music_image);
        playerTitle = findViewById(R.id.player_music_title);
        playerArtist = findViewById(R.id.player_music_artist);
        playerPlayBtn = findViewById(R.id.player_btn_play_pause);
        playerForwardBtn = findViewById(R.id.player_btn_forward);
        playerRewindBtn = findViewById(R.id.player_btn_rewind);
        playerRewindBtn.setOnClickListener(v -> {
            AudioApplication.getInstance().getServiceInterface().setPlayList(musicPlayList);
            AudioApplication.getInstance().getServiceInterface().rewind();
        });
        playerPlayBtn.setOnClickListener(v -> {
            AudioApplication.getInstance().getServiceInterface().setPlayList(musicPlayList);
            AudioApplication.getInstance().getServiceInterface().togglePlay();
        });
        playerForwardBtn.setOnClickListener(v -> {
            AudioApplication.getInstance().getServiceInterface().setPlayList(musicPlayList);
            AudioApplication.getInstance().getServiceInterface().forward();
        });

        RecyclerView recyclerView = findViewById(R.id.playlist);
        PlaylistAdapter recyclerViewAdapter = new PlaylistAdapter();
        recyclerViewAdapter.setItem(musicPlayList);
        recyclerView.setAdapter(recyclerViewAdapter);

        registerBroadcast();
        updateUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterBroadcast();
    }

    public void registerBroadcast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadcastActions.PLAY_STATE_CHANGED);
        registerReceiver(mBroadcastReceiver, filter);
    }

    public void unregisterBroadcast() {
        unregisterReceiver(mBroadcastReceiver);
    }

    public void updateUI() {
        Music music = AudioApplication.getInstance().getServiceInterface().getMusic();
        if (music != null && !AudioApplication.getInstance().getServiceInterface().getIsHomePlayed()) {
            Glide.with(this)
                    .load(music.getThumbnail())
                    .into(playerImage);
            if(music.getTitle().contains("(HOME)")||music.getTitle().contains("(ASMR)")){
                playerTitle.setText(music.getTitle().substring(7));
                playerArtist.setVisibility(View.GONE);
            }else {
                playerTitle.setText(music.getTitle());
                playerArtist.setVisibility(View.VISIBLE);
            }
            playerArtist.setText(music.getArtist());
        } else {
            playerImage.setImageResource(R.drawable.img_music);
            playerTitle.setText("재생중인 음악이 없습니다");
            playerArtist.setVisibility(View.GONE);
        }

        if (AudioApplication.getInstance().getServiceInterface().isPlaying()
                && !AudioApplication.getInstance().getServiceInterface().getIsHomePlayed()) {
            playerPlayBtn.setImageResource(R.drawable.ic_pause);
        } else {
            playerPlayBtn.setImageResource(R.drawable.ic_play);
        }
    }

    private class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {
        private ArrayList<Music> musicList = new ArrayList<>();

        @NotNull
        @Override
        public PlaylistAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_music, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final Music music = musicList.get(position);
            Glide.with(holder.itemView.getContext())
                    .load(music.getThumbnail())
                    .into(holder.thumbnail);
            holder.artist.setText(music.getArtist());
            if(music.getTitle().contains("(HOME)")||music.getTitle().contains("(ASMR)")){
                holder.title.setText(music.getTitle().substring(7));
                holder.artist.setVisibility(View.GONE);
            }else {
                holder.title.setText(music.getTitle());
                holder.artist.setVisibility(View.VISIBLE);
            }
            holder.action.setOnClickListener(view -> {
                Log.i(music.getTitle().contains("(ASMR)") ? LogEvent.PLAYLIST_REMOVE_ASMR : LogEvent.PLAYLIST_REMOVE_MUSIC, String.valueOf(music.getId()));
                musicPlayList.remove(position);
                notifyDataSetChanged();
                AudioApplication.getInstance().getServiceInterface().delete(position, musicPlayList);
                updateUI();
            });
        }

        @Override
        public int getItemCount() {
            return musicList.size();
        }

        public void setItem(ArrayList<Music> musicList) {
            this.musicList = musicList;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView thumbnail;
            TextView title;
            TextView artist;
            ImageButton action;

            ViewHolder(View itemView) {
                super(itemView);
                thumbnail = itemView.findViewById(R.id.music_item_image);
                title = itemView.findViewById(R.id.music_item_title);
                artist = itemView.findViewById(R.id.music_item_artist);
                action = itemView.findViewById(R.id.music_item_delete);
            }
        }
    }
}
