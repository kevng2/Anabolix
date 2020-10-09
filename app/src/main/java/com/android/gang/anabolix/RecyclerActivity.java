package com.android.gang.anabolix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

public class RecyclerActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    String s1[], s2[];
    int images[] = {R.drawable.strength,R.drawable.cardio,R.drawable.endurance,R.drawable.flexibility, R.drawable.cardio,R.drawable.balance};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        recyclerView = findViewById(R.id.recyclerView);

        s1 = getResources().getStringArray(R.array.exercise_list);
        s2 = getResources().getStringArray(R.array.exercise_descriptions);

        adapter adapter = new adapter(getApplicationContext(), s1, s2, images);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
}