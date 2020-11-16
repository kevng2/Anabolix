package com.android.gang.anabolix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

public class RecyclerActivity extends AppCompatActivity implements ExerciseAdapter.OnRecyclerListener {

    RecyclerView recyclerView;

    String[] s1;
    String[] s2;
    int[] images = {R.drawable.strength, R.drawable.cardio, R.drawable.endurance, R.drawable.flexibility, R.drawable.cardio, R.drawable.balance};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        recyclerView = findViewById(R.id.recyclerView);

        s1 = getResources().getStringArray(R.array.exercise_list);
        s2 = getResources().getStringArray(R.array.exercise_descriptions);

        ExerciseAdapter ExerciseAdapter = new ExerciseAdapter(getApplicationContext(), s1, s2, images, this);
        recyclerView.setAdapter(ExerciseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    @Override
    public void onRecyclerClick(int position) {
        Intent intent = new Intent(this, ExerciseActivity.class);
        startActivity(intent);
    }
}