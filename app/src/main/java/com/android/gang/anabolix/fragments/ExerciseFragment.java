package com.android.gang.anabolix.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.gang.anabolix.ExerciseAdapter;
import com.android.gang.anabolix.R;
import com.android.gang.anabolix.ui.ExerciseActivity;
import com.android.gang.anabolix.ui.ExerciseActivity2;
import com.android.gang.anabolix.ui.ExerciseActivity3;
import com.android.gang.anabolix.ui.ExerciseActivity4;
import com.android.gang.anabolix.ui.ExerciseActivity5;
import com.android.gang.anabolix.ui.ExerciseActivity6;

public class ExerciseFragment extends Fragment implements ExerciseAdapter.OnRecyclerListener {
    private RecyclerView recyclerView;
    private String[] s1;
    private String[] s2;
    private int[] images = {R.drawable.strength, R.drawable.cardio, R.drawable.endurance, R.drawable.flexibility, R.drawable.cardio, R.drawable.balance};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.exercise_fragment, container, false);

        recyclerView = v.findViewById(R.id.recyclerView);

        s1 = getResources().getStringArray(R.array.exercise_list);
        s2 = getResources().getStringArray(R.array.exercise_descriptions);

        ExerciseAdapter ExerciseAdapter = new ExerciseAdapter(requireContext(), s1, s2, images, this);
        recyclerView.setAdapter(ExerciseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        return v;
    }

    @Override
    public void onRecyclerClick(int position) {
        if (position == 0) {
            Intent intent = new Intent(requireActivity(), ExerciseActivity.class);
            startActivity(intent);
        } else if (position == 1) {
            Intent intent = new Intent(requireActivity(), ExerciseActivity2.class);
            startActivity(intent);
        } else if (position == 2) {
            Intent intent = new Intent(requireActivity(), ExerciseActivity3.class);
            startActivity(intent);
        } else if (position == 3) {
            Intent intent = new Intent(requireActivity(), ExerciseActivity4.class);
            startActivity(intent);
        } else if (position == 4) {
            Intent intent = new Intent(requireActivity(), ExerciseActivity5.class);
            startActivity(intent);
        } else if (position == 5) {
            Intent intent = new Intent(requireActivity(), ExerciseActivity6.class);
            startActivity(intent);
        }

    }
}