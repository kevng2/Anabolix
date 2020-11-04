package com.android.gang.anabolix.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
interface RunDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRun(Run run);

    @Delete
    void deleteRun(Run run);

    @Delete
    void resetRun(List<Run> run);

    @Query("SELECT * FROM running_table ORDER BY mTimeStamp DESC")
    LiveData<List<Run>> getAllRunsSortedByDate();

    @Query("SELECT * FROM running_table ORDER BY mTimeInMillis DESC")
    LiveData<List<Run>> getAllRunsSortedByTimeInMillis();

    @Query("SELECT * FROM running_table ORDER BY mCaloriesBurned DESC")
    LiveData<List<Run>> getAllRunsSortedByCaloriesBurned();

    @Query("SELECT * FROM running_table ORDER BY mAvgSpeedInMPH DESC")
    LiveData<List<Run>> getAllRunsSortedByAvgSpeed();

    @Query("SELECT * FROM running_table ORDER BY mDistanceInFeet DESC")
    LiveData<List<Run>> getAllRunsSortedByDistanceInFeet();

    @Query("UPDATE running_table SET mTimeStamp = :timeStamp WHERE id = :sID")
    void update(int sID, Long timeStamp);

    @Query("SELECT SUM(mTimeInMillis) FROM running_table")
    LiveData<Long> getTotalTimeInMilis();

    @Query("SELECT SUM(mCaloriesBurned) FROM running_table")
    LiveData<Integer> getTotalCaloriesBurned();

    @Query("SELECT SUM(mDistanceInFeet) FROM running_table")
    LiveData<Integer> getTotalDistanceInFeet();

    @Query("SELECT SUM(mAvgSpeedInMPH) FROM running_table")
    LiveData<Float> getTotalAvgSpeedInMPH();
}
