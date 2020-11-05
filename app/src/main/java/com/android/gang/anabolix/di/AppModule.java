package com.android.gang.anabolix.di;

import android.content.Context;

import androidx.room.Dao;
import androidx.room.Room;

import com.android.gang.anabolix.db.RunDAO;
import com.android.gang.anabolix.db.RunningDatabase;
import com.android.gang.anabolix.other.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;

@Module
@InstallIn(ApplicationComponent.class)
public class AppModule {
    @Singleton
    @Provides
    RunningDatabase provideRunningDatabase(@ApplicationContext Context app) {
        return Room.databaseBuilder(app, RunningDatabase.class, Constants.RUNNING_DATABASE_NAME).build();
    }

    @Singleton
    @Provides
    RunDAO provideRunDao(RunningDatabase db) {
        return db.getRunDao();
    }
}
