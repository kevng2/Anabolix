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

@HiltAndroidApp
public class BaseApplication extends Application {


}
