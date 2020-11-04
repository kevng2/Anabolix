package com.android.gang.anabolix.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(
        entities = {Run.class},
        version = 1
)

@TypeConverters(Converters.class)
abstract public class RunningDatabase extends RoomDatabase {

    abstract RunDAO getRunDao();
}
