package com.shim.user.shimapplication.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Asmr.class, Music.class}, version = 4)
public abstract class ShimDatabase extends RoomDatabase {
    private static ShimDatabase instance;

    public static synchronized ShimDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), ShimDatabase.class, "shim.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract AsmrDao getAsmrDao();

    public abstract MusicDao getMusicDao();
}
