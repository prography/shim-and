package com.shim.user.shimapplication.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MusicDao {
    @Query("SELECT * FROM music WHERE category = (:category)")
    List<Music> findByCategory(String category);

    @Query("SELECT * FROM music WHERE id = (:id)")
    List<Music> findById(int id);

    @Query("SELECT * FROM music")
    List<Music> getAll();

    @Query("SELECT * FROM music WHERE favorite = 1")
    List<Music> getFavorites();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Music music);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Music> musicList);

    @Update
    void update(Music music);

    @Update
    void updateAll(List<Music> musicList);
}
