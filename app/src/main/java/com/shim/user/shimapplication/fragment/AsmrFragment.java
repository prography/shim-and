package com.shim.user.shimapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shim.user.shimapplication.R;
import com.shim.user.shimapplication.media.AudioApplication;
import com.shim.user.shimapplication.media.AudioServiceInterface;
import com.shim.user.shimapplication.room.Asmr;
import com.shim.user.shimapplication.room.AsmrDao;
import com.shim.user.shimapplication.room.Music;
import com.shim.user.shimapplication.room.ShimDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.shim.user.shimapplication.activity.MainActivity.musicPlayList;
import static com.shim.user.shimapplication.activity.MainActivity.showPlayer;
import static com.shim.user.shimapplication.fragment.HomeFragment.isOtherMusicPlayed;

public class AsmrFragment extends Fragment {
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_asmr, container, false);
        AsmrCardAdapter recyclerViewAdapter = new AsmrCardAdapter();
        RecyclerView recyclerView = view.findViewById(R.id.list_asmr);
        recyclerView.setAdapter(recyclerViewAdapter);
        AsmrDao dao = ShimDatabase.getInstance(getContext()).getAsmrDao();
        new Thread(() -> recyclerViewAdapter.setItem((ArrayList<Asmr>) dao.getAll())).start();
        return view;
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
            holder.action.setOnClickListener(view -> {
                Music asmrLike = new Music.Builder()
                        .setTitle(asmr.getTitle())
                        .setDuration(asmr.getDuration())
                        .setThumbnail(asmr.getThumbnail())
                        .setUrl(asmr.getUrl())
                        .build();
                musicPlayList.add(asmrLike);
                AudioServiceInterface audioServiceInterface = AudioApplication.getInstance().getServiceInterface();
                if (audioServiceInterface.getIsHomePlayed()) {
                    audioServiceInterface.stop();
                    audioServiceInterface.setIsHomePlayed(false);
                    isOtherMusicPlayed = true;
                    showPlayer();
                }
                audioServiceInterface.setPlayList(musicPlayList);
                audioServiceInterface.play(musicPlayList.size() - 1);
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
            ImageButton action;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                thumbnail = itemView.findViewById(R.id.image_asmr_thumbnail);
                title = itemView.findViewById(R.id.text_asmr_title);
                action = itemView.findViewById(R.id.button_play_asmr);
            }
        }
    }
}
