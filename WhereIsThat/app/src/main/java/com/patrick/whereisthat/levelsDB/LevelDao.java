package com.patrick.whereisthat.levelsDB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Patrick on 2/13/2018.
 */

@Dao
public interface LevelDao {
    @Query("SELECT * FROM level ORDER BY RANDOM()")
    List<Level> getLevels();
    @Insert
    void insertAll(List<Level> levels );

    @Query("DELETE FROM level")
    void deleteAll();
    @Query("SELECT COUNT(*) FROM level")
    int lines();

}
