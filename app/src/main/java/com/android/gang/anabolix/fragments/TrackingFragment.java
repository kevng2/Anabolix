package com.android.gang.anabolix.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.android.gang.anabolix.R;
import com.android.gang.anabolix.other.Constants;
import com.android.gang.anabolix.services.TrackingService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class TrackingFragment extends Fragment {
    private Button mToggleButton;
    private Button mFinishRun;
    private GoogleMap mGoogleMap;
    private MapView mMapView;
    private boolean isTracking = false;
    private ArrayList<ArrayList<LatLng>> pathPoints = new ArrayList<>();
    private Chronometer mChronometer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tracking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(googleMap -> {
            mGoogleMap = googleMap;
            addAllPolyLines();
        });
        mFinishRun = view.findViewById(R.id.btnFinishRun);
        mFinishRun.setOnClickListener(v -> toggleRun());
        mToggleButton = view.findViewById(R.id.btnToggleRun);
        mChronometer = view.findViewById(R.id.chronometer);
        mToggleButton.setOnClickListener(v -> toggleRun());
        subscribeToObservers();
    }

    private void subscribeToObservers() {
        TrackingService.isTracking.observe(getViewLifecycleOwner(), this::updateTracking);

        TrackingService.pathPoints.observe(getViewLifecycleOwner(), arrayLists -> {
            pathPoints = arrayLists;
            addLatestPolyline();
            moveCameraToUser();
        });
    }

    private void toggleRun() {
        if (isTracking) {
            sendCommandToService(Constants.ACTION_PAUSE_SERVICE);
            mChronometer.stop();
        } else {
            sendCommandToService(Constants.ACTION_START_OR_RESUME_SERVICE);
            mChronometer.start();
        }
    }

    private void updateTracking(boolean isTracking) {
        this.isTracking = isTracking;
        if(!isTracking) {
            mToggleButton.setText("Start");
            mFinishRun.setVisibility(View.VISIBLE);
        } else {
            mToggleButton.setText("Stop");
            mFinishRun.setVisibility(View.GONE);
        }
    }

    private void moveCameraToUser() {
        int lastIdx = pathPoints.size() - 1;
        if (!pathPoints.isEmpty() && pathPoints.get(lastIdx).size() > 1)  {
            int lastLatLngIdx = pathPoints.get(lastIdx).size() - 1;
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    pathPoints.get(lastIdx).get(lastLatLngIdx),
                    Constants.MAP_ZOOM
            ));
        }
    }

    private void addAllPolyLines() {
        for (ArrayList<LatLng> x : pathPoints) {
            PolylineOptions polylineOptions = new PolylineOptions()
                    .color(Constants.POLYLINE_COLOR)
                    .width(Constants.POLYLINE_WIDTH)
                    .addAll(x);
            mGoogleMap.addPolyline(polylineOptions);
        }
    }

    private void addLatestPolyline() {
        int lastIdx = pathPoints.size() - 1;
        if (!pathPoints.isEmpty() && pathPoints.get(lastIdx).size() > 1) {
            int lastLatLngIdx = pathPoints.get(lastIdx).size() - 1;
            int secondLastLatLngIdx = pathPoints.get(lastIdx).size() - 2;
            LatLng secondLastLatLng = pathPoints.get(lastIdx).get(secondLastLatLngIdx);
            LatLng lastLatLng = pathPoints.get(lastIdx).get(lastLatLngIdx);
            PolylineOptions polylineOptions = new PolylineOptions()
                    .color(Constants.POLYLINE_COLOR)
                    .width(Constants.POLYLINE_WIDTH)
                    .add(secondLastLatLng)
                    .add(lastLatLng);

            mGoogleMap.addPolyline(polylineOptions);
        }
    }

    private void sendCommandToService(String action) {
        Intent intent = new Intent(requireContext(), TrackingService.class);
        intent.setAction(action);
        requireContext().startService(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mMapView != null)
            mMapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mMapView != null)
            mMapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMapView != null)
            mMapView.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mMapView != null)
            mMapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mMapView != null)
            mMapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMapView != null)
            mMapView.onSaveInstanceState(outState);
    }
}
