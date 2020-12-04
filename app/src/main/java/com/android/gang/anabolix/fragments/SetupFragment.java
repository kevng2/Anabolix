package com.android.gang.anabolix.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.android.gang.anabolix.R;

import java.util.Objects;

public class SetupFragment extends Fragment {
    private static final String TAG = "SetupFragment";
    private TextView mContinue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_setup, container, false);
        mContinue = v.findViewById(R.id.button_continue);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavHostFragment navHostFragment =
                (NavHostFragment) requireActivity().getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_fragment_container);

        mContinue.setOnClickListener(v -> {
            assert navHostFragment != null;
            NavHostFragment.findNavController(navHostFragment)
                    .navigate(R.id.trackingFragment);
        });
    }
}

