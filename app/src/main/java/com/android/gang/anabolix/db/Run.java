package com.android.gang.anabolix.db;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "running_table")
public class Run implements Serializable {
    @ColumnInfo
    private Bitmap mImg;
    private Long mTimeStamp = 0L;
    private Float mAvgSpeedInMPH = 0f;
    private int mDistanceInFeet = 0;
    private Long mTimeInMillis = 0L;
    private int mCaloriesBurned = 0;

    @PrimaryKey(autoGenerate = true)
    private int id;

    public Bitmap getImg() {
        return mImg;
    }

    public void setImg(Bitmap img) {
        mImg = img;
    }

    public Long getTimeStamp() {
        return mTimeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        mTimeStamp = timeStamp;
    }

    public Float getAvgSpeedInMPH() {
        return mAvgSpeedInMPH;
    }

    public void setAvgSpeedInMPH(Float avgSpeedInMPH) {
        mAvgSpeedInMPH = avgSpeedInMPH;
    }

    public int getDistanceInFeet() {
        return mDistanceInFeet;
    }

    public void setDistanceInFeet(int distanceInFeet) {
        mDistanceInFeet = distanceInFeet;
    }

    public Long getTimeInMmillis() {
        return mTimeInMillis;
    }

    public void setTimeInMmillis(Long timeInMmillis) {
        mTimeInMillis = timeInMmillis;
    }

    public int getCaloriesBurned() {
        return mCaloriesBurned;
    }

    public void setCaloriesBurned(int caloriesBurned) {
        mCaloriesBurned = caloriesBurned;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
