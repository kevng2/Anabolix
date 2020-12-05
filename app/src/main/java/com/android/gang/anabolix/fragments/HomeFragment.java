package com.android.gang.anabolix.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.gang.anabolix.AlarmActivity;
import com.android.gang.anabolix.ui.CalorieCounterActivity;
import com.android.gang.anabolix.ui.LoginActivity;
import com.android.gang.anabolix.R;
import com.android.gang.anabolix.ui.RunActivity;
import com.firebase.ui.auth.AuthUI;

public class HomeFragment extends Fragment {
    private CardView mSamButton;
    private Button mLewisButton;
    private CardView mCarterButton;
    private CardView mRunButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        mSamButton = v.findViewById(R.id.sam_button);
        mSamButton.setOnClickListener(v12 -> {
            Intent intent = new Intent(requireActivity(), CalorieCounterActivity.class);
            startActivity(intent);
        });

        mLewisButton = v.findViewById(R.id.lewis_button);
        mLewisButton.setOnClickListener(v13 -> {
            Toast.makeText(requireActivity(), "Logged Out", Toast.LENGTH_SHORT).show();
            signOut();
        });

        mCarterButton = v.findViewById(R.id.carter_button);
        mCarterButton.setOnClickListener(v14 -> {
            Intent intent = new Intent(requireActivity(), AlarmActivity.class);
            startActivity(intent);
        });

        mRunButton = v.findViewById(R.id.running_button);
        mRunButton.setOnClickListener(v1 -> {
            Intent intent = new Intent(requireActivity(), RunActivity.class);
            startActivity(intent);
        });
        return v;
    }

    public void signOut() {
        // [START auth_fui_signout]
        AuthUI.getInstance()
                .signOut(requireActivity())
                .addOnCompleteListener(task -> {
                    requireActivity().finish();
                    startActivity(new Intent(requireActivity(), LoginActivity.class));
                });
    }
}
