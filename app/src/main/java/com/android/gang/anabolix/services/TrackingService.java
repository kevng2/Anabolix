package com.android.gang.anabolix.services;


import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.MutableLiveData;

import com.android.gang.anabolix.R;
import com.android.gang.anabolix.other.Constants;
import com.android.gang.anabolix.other.TrackingUtility;
import com.android.gang.anabolix.ui.MainActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Objects;

import timber.log.Timber;

public class TrackingService extends LifecycleService {
    private boolean isFirstRun = true;
    public static MutableLiveData<Boolean> isTracking = new MutableLiveData<>();
    public static MutableLiveData<ArrayList<ArrayList<LatLng>>> pathPoints = new MutableLiveData<>();
    private FusedLocationProviderClient mFusedLocationProviderClient;

    private void postInitialValues() {
        isTracking.postValue(false);
        pathPoints.postValue(getEmptyPolylines());
    }

    private ArrayList<ArrayList<LatLng>> getEmptyPolylines() {
        ArrayList<ArrayList<LatLng>> polylines = new ArrayList<>();
        ArrayList<LatLng> latLngs = new ArrayList<>();
        polylines.add(latLngs);
        return polylines;
    }

    @SuppressLint("VisibleForTests")
    @Override
    public void onCreate() {
        super.onCreate();
        postInitialValues();
        mFusedLocationProviderClient = new FusedLocationProviderClient(this);

        isTracking.observe(this, this::updateLocationTracking);
    }

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
                    startForegroundService();
                }
                break;
            case Constants.ACTION_PAUSE_SERVICE:
                Timber.d("onStartCommand: Paused service");
                pauseService();
                break;
            case Constants.ACTION_STOP_SERVICE:
                Timber.d("onStartCommand: Stopped service");
                break;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void pauseService() {
        isTracking.postValue(false);
    }

    private void updateLocationTracking(Boolean isTracking) {
        if (isTracking) {
            if (TrackingUtility.hashLocationPermission(this)) {
                LocationRequest request = new LocationRequest()
                        .setInterval(Constants.LOCATION_UPDATE_INTERVAL)
                        .setFastestInterval(Constants.FASTEST_LOCATION_INTERVAL)
                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                mFusedLocationProviderClient.requestLocationUpdates(
                        request,
                        mLocationCallback,
                        Looper.getMainLooper());
            } else {
                mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
            }
        }
    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if (isTracking.getValue() != null && isTracking.getValue()) {
                for (Location location : locationResult.getLocations()) {
                    addPathPoint(location);
                    Timber.d("NEW LOCATION " + location.getLatitude() + " " + location.getLongitude());
                }
            }
        }
    };

    private void addPathPoint(Location location) {
        if (location != null) {
            LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());

            if (pathPoints.getValue() != null) {
                int lastIdx;
                if (pathPoints.getValue().size() - 1 > 0)
                    lastIdx = pathPoints.getValue().size() - 1;
                else lastIdx = 0;
                pathPoints.getValue().get(lastIdx).add(pos);
                pathPoints.postValue(pathPoints.getValue());
            }
        }
    }

    private void addEmptyPolyline() {
        if (pathPoints.getValue() != null) {
            pathPoints.getValue().add(new ArrayList<>());
            pathPoints.postValue(pathPoints.getValue());
        } else {
            pathPoints.postValue(getEmptyPolylines());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startForegroundService() {
        addEmptyPolyline();
        isTracking.postValue(true);

        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            assert notificationManager != null;
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
