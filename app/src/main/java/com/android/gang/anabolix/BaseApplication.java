package com.android.gang.anabolix;

import android.app.Application;
import android.content.Context;

import androidx.room.Dao;
import androidx.room.Room;

import com.android.gang.anabolix.db.RunningDatabase;
import com.android.gang.anabolix.other.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.android.HiltAndroidApp;
import dagger.hilt.android.qualifiers.ApplicationContext;
import timber.log.Timber;

@HiltAndroidApp
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
