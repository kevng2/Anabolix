package com.android.gang.anabolix.services;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LifecycleService;

import com.android.gang.anabolix.R;
import com.android.gang.anabolix.other.Constants;
import com.android.gang.anabolix.ui.MainActivity;

import java.util.Objects;

import timber.log.Timber;

public class TrackingService extends LifecycleService {
    private boolean isFirstRun = true;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(@NonNull Intent intent, int flags, int startId) {
        switch (Objects.requireNonNull(intent.getAction())) {
            case Constants.ACTION_START_OR_RESUME_SERVICE:
                Timber.d("onStartCommand: Started or resumed service");
                if (isFirstRun) {
                    startForegroundService();
                    isFirstRun = false;
                } else {
                    Timber.d("Resuming service");
                }
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startForegroundService() {
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChanel(notificationManager);
        }

        Notification.Builder notification = new Notification.Builder(this, Constants.NOTIFICATION_CHANNEL_ID)
                .setAutoCancel(false)
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_run)
                .setContentTitle("Anabolix Run")
                .setContentText("00:00:00")
                .setContentIntent(getMainActivityPendingIntent());

        startForeground(Constants.NOTIFICATION_ID, notification.build());
    }

    private PendingIntent getMainActivityPendingIntent() {
        return PendingIntent.getActivity(this,
                0,
                new Intent(this, MainActivity.class)
                        .setAction(Constants.ACTION_SHOW_TRACKING_FRAGMENT),
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChanel(NotificationManager notificationManager) {
        NotificationChannel channel = new NotificationChannel(
                Constants.NOTIFICATION_CHANNEL_ID,
                Constants.NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW);
        notificationManager.createNotificationChannel(channel);
    }
}
