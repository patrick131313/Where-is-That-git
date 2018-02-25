package com.patrick.whereisthat.sprintDB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by Patrick on 2/24/2018.
 */
@Database(entities ={Sprint.class},version = 1)
public abstract class SprintDatabase extends RoomDatabase {
    public abstract SprintDao sprintDao();
}
