package co.shimm.app.media;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.AppWidgetTarget;
import co.shimm.app.R;
import co.shimm.app.activity.PlaylistActivity;
import co.shimm.app.room.Music;

public class NotificationPlayer {
    private final static int NOTIFICATION_PLAYER_ID = 0x342;
    private AudioService mService;
    private NotificationManager mNotificationManager;
    private NotificationManagerBuilder mNotificationManagerBuilder;
    private boolean isForeground;

    public NotificationPlayer(AudioService service) {
        mService = service;
        mNotificationManager = (NotificationManager) service.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void updateNotificationPlayer() {
        cancel();
        mNotificationManagerBuilder = new NotificationManagerBuilder();
        mNotificationManagerBuilder.execute();
    }

    public void removeNotificationPlayer() {
        cancel();
        mService.stopForeground(true);
        isForeground = false;
    }

    private void cancel() {
        if (mNotificationManagerBuilder != null) {
            mNotificationManagerBuilder.cancel(true);
            mNotificationManagerBuilder = null;
        }
    }

    private class NotificationManagerBuilder extends AsyncTask<Void, Void, Notification> {
        private RemoteViews mRemoteViews;
        private NotificationCompat.Builder mNotificationBuilder;
        private PendingIntent mMainPendingIntent;

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Intent musicListActivity = new Intent(mService, PlaylistActivity.class);
            mMainPendingIntent = PendingIntent.getActivity(mService, 0, musicListActivity, 0);
            mRemoteViews = createRemoteView(R.layout.notification_player);
            String channel;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                channel = mService.createChannel();
            } else {
                channel = "";
            }

            mNotificationBuilder = new NotificationCompat.Builder(mService, channel);

            mNotificationBuilder.setSmallIcon(R.drawable.ic_logo_notification).setOngoing(true)
                    .setContentIntent(mMainPendingIntent).setContent(mRemoteViews);

            mNotificationBuilder.setVibrate(new long[]{-1});

            Notification notification = mNotificationBuilder.build();
            notification.priority = Notification.PRIORITY_MAX;
            notification.contentIntent = mMainPendingIntent;
            if (!isForeground) {
                isForeground = true;
                mService.startForeground(NOTIFICATION_PLAYER_ID, notification);
            }
        }

        @Override
        protected Notification doInBackground(Void... params) {
            mNotificationBuilder.setContent(mRemoteViews);
            mNotificationBuilder.setContentIntent(mMainPendingIntent);
            mNotificationBuilder.setPriority(Notification.PRIORITY_MAX);
            mNotificationBuilder.setVibrate(new long[]{-1});
            Notification notification = mNotificationBuilder.build();
            updateRemoteView(mRemoteViews, notification);
            return notification;
        }

        @Override
        protected void onPostExecute(Notification notification) {
            super.onPostExecute(notification);
            try {
                mNotificationManager.notify(NOTIFICATION_PLAYER_ID, notification);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private RemoteViews createRemoteView(int layoutId) {
            RemoteViews remoteView = new RemoteViews(mService.getPackageName(), layoutId);
            Intent actionTogglePlay = new Intent(CommandActions.TOGGLE_PLAY);
            Intent actionForward = new Intent(CommandActions.FORWARD);
            Intent actionRewind = new Intent(CommandActions.REWIND);
            Intent actionClose = new Intent(CommandActions.CLOSE);
            PendingIntent togglePlay = PendingIntent.getService(mService, 0, actionTogglePlay, 0);
            PendingIntent forward = PendingIntent.getService(mService, 0, actionForward, 0);
            PendingIntent rewind = PendingIntent.getService(mService, 0, actionRewind, 0);
            PendingIntent close = PendingIntent.getService(mService, 0, actionClose, 0);

            remoteView.setOnClickPendingIntent(R.id.noti_play_pause, togglePlay);
            remoteView.setOnClickPendingIntent(R.id.noti_forward, forward);
            remoteView.setOnClickPendingIntent(R.id.noti_rewind, rewind);
            remoteView.setOnClickPendingIntent(R.id.noti_close, close);
            return remoteView;
        }

        private void updateRemoteView(RemoteViews remoteViews, Notification notification) {
            if (mService.isPlaying()) {
                remoteViews.setImageViewResource(R.id.noti_play_pause, R.drawable.ic_pause);
            } else {
                remoteViews.setImageViewResource(R.id.noti_play_pause, R.drawable.ic_play);
            }
            Music music = AudioApplication.getInstance().getServiceInterface().getMusic();
            AppWidgetTarget target = new AppWidgetTarget(mService.getApplicationContext(), R.id.noti_img_albumart, remoteViews, R.layout.notification_player);
            if (music != null && !AudioApplication.getInstance().getServiceInterface().getIsHomePlayed()) {
                Glide.with(mService.getApplicationContext()).asBitmap().centerCrop().load(music.getThumbnail()).override(480, 342).into(target);
                if(music.getTitle().contains("(ASMR)")||music.getTitle().contains("(HOME)")){
                    remoteViews.setTextViewText(R.id.noti_title, music.getTitle().substring(7));
                    remoteViews.setTextViewText(R.id.noti_artist, "");
                }else {
                    remoteViews.setTextViewText(R.id.noti_title, music.getTitle());
                    remoteViews.setTextViewText(R.id.noti_artist, music.getArtist());
                }
            } else {
                remoteViews.setImageViewResource(R.id.noti_img_albumart, R.drawable.img_music);
                remoteViews.setTextViewText(R.id.noti_title, "재생중인 음악이 없습니다");
                remoteViews.setTextViewText(R.id.noti_artist, "");
            }
        }
    }
}
