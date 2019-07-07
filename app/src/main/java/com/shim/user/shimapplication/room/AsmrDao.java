package com.shim.user.shimapplication.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AsmrDao {
    @Query("SELECT * FROM asmr WHERE id = (:id)")
    List<Asmr> findById(int id);

    @Query("SELECT * FROM asmr")
    List<Asmr> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Asmr asmr);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Asmr> asmrList);

    @Update
    void update(Asmr asmr);

    @Update
    void updateAll(List<Asmr> asmrList);
}
