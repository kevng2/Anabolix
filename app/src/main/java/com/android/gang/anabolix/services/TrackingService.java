package com.android.gang.anabolix.services;


import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleService;

import com.android.gang.anabolix.other.Constants;

import java.util.Objects;

import timber.log.Timber;

public class TrackingService extends LifecycleService {
    private static final String TAG = "TrackingService";

    @Override
    public int onStartCommand(@NonNull Intent intent, int flags, int startId) {
        switch (Objects.requireNonNull(intent.getAction())) {
            case Constants.ACTION_START_OR_RESUME_SERVICE:
                Timber.d("onStartCommand: Started or resumed service");
                break;
            case Constants.ACTION_PAUSE_SERVICE:
                Timber.d("onStartCommand: Paused service");
                break;
            case Constants.ACTION_STOP_SERVICE:
                Timber.d("onStartCommand: Stopped service");
                break;
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
