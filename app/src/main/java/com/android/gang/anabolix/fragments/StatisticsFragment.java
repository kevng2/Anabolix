package com.android.gang.anabolix.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.gang.anabolix.R;

public class StatisticsFragment extends Fragment {
    private static final String TAG = "StatisticsFragment";
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: inside statistics fragment");
        return inflater.inflate(R.layout.fragment_statistics, container, false);
    }
}
