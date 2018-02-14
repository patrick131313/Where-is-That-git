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
    @Query("SELECT * FROM level WHERE level='1'ORDER BY RANDOM()")
    List<Level> getLevel1();
    @Query("SELECT * FROM level WHERE level='2'ORDER BY RANDOM()")
    List<Level> getLevel2();
    @Query("SELECT * FROM level WHERE level='3'ORDER BY RANDOM()")
    List<Level> getLevel3();
    @Query("SELECT * FROM level WHERE level='4'ORDER BY RANDOM()")
    List<Level> getLevel4();
    @Query("SELECT * FROM level WHERE level='5'ORDER BY RANDOM()")
    List<Level> getLevel5();
    @Query("SELECT * FROM level WHERE level='6'ORDER BY RANDOM()")
    List<Level> getLevel6();
    @Query("SELECT * FROM level WHERE level='7'ORDER BY RANDOM()")
    List<Level> getLevel7();
    @Query("SELECT * FROM level WHERE level='8'ORDER BY RANDOM()")
    List<Level> getLevel8();
    @Query("SELECT * FROM level WHERE level='9'ORDER BY RANDOM()")
    List<Level> getLevel9();
    @Query("SELECT * FROM level WHERE level='10'ORDER BY RANDOM()")
    List<Level> getLevel10();
    @Query("SELECT * FROM level WHERE level='11'ORDER BY RANDOM()")
    List<Level> getLevel11();

    @Insert
    void insertAll(List<Level> levels );

    @Query("DELETE FROM level")
    void deleteAll();
    @Query("SELECT COUNT(*) FROM level")
    int lines();
}
