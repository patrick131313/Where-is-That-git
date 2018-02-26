package com.patrick.whereisthat.sprintDB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.patrick.whereisthat.levelsDB.Level;

import java.util.List;

/**
 * Created by Patrick on 2/24/2018.
 */
@Dao
public interface SprintDao {

    @Query("SELECT * FROM sprint ORDER BY RANDOM() limit 20")
    List<Sprint> getSprint();

    @Insert
    void insertAll(List<Sprint> sprints );

    @Query("DELETE FROM sprint")
    void deleteAll();
    @Query("SELECT COUNT(*) FROM sprint")
    int lines();
}
