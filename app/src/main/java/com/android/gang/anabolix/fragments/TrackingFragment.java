package com.android.gang.anabolix.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.gang.anabolix.R;
import com.android.gang.anabolix.other.Constants;
import com.android.gang.anabolix.services.TrackingService;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

public class TrackingFragment extends Fragment {
    private Button mToggleButton;
    private GoogleMap mGoogleMap;
    private MapView mMapView;

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
        mMapView.getMapAsync(googleMap -> mGoogleMap = googleMap);
        mToggleButton = view.findViewById(R.id.btnToggleRun);
        mToggleButton.setOnClickListener(v -> sendCommandToService(Constants.ACTION_START_OR_RESUME_SERVICE));
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
