package com.patrick.whereisthat.levelsDB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by Patrick on 2/13/2018.
 */

@Database(entities ={Level.class},version = 1)
public abstract class LevelDatabase extends RoomDatabase {
    public abstract LevelDao levelDao();

}

