package com.android.gang.anabolix.ui;

import android.os.Bundle;

import com.android.gang.anabolix.R;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

public class CalorieCounterActivity extends AppCompatActivity {
    private EditText editCalorie;
    private Button AddCalorieButton;
    private Button SubtractCalorieButton;
    private TextView CalorieDisplay;
    private ImageView mCake;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private int userCal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_counter);
        setUserCal(getInitialCal());


        editCalorie = findViewById(R.id.editCalorie);
        AddCalorieButton = findViewById(R.id.addCalories);
        SubtractCalorieButton = findViewById(R.id.subtractCalories);
        CalorieDisplay = findViewById(R.id.CalorieDisplay);
        mCake = findViewById(R.id.imageView);

        Glide
                .with(this)
                .load(R.drawable.cake)
                .into(mCake);


        CalorieDisplay.setText(Integer.toString(getUserCal()));
        AddCalorieButton.setOnClickListener(view -> {
            int Add = Integer.parseInt(editCalorie.getText().toString());
            int result = Add + Integer.parseInt(CalorieDisplay.getText().toString());
            UpdateUserCalorie(result);
            CalorieDisplay.setText(Integer.toString(result));
        });

        SubtractCalorieButton.setOnClickListener(view -> {
            int Sub = Integer.parseInt(editCalorie.getText().toString());
            int result = Integer.parseInt(CalorieDisplay.getText().toString()) - Sub;
            UpdateUserCalorie(result);
            CalorieDisplay.setText(Integer.toString(result));
        });
    }

    private void UpdateUserCalorie(int result) {
        mDatabase.child("users").child(userId).child("calories").setValue(result);
        if (result >= 2000) {
            Toast.makeText(getApplicationContext(), "Daily Calorie Goal Reached!", Toast.LENGTH_SHORT).show();
        }
    }

    protected void setUserCal(int value) {
        userCal = value;
    }

    protected int getUserCal() {
        return userCal;
    }

    private int getInitialCal() {
        mDatabase.child("users").child(userId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NotNull DataSnapshot snapshot, String prevChildKey) {
                if (snapshot.getValue() == null)
                    setUserCal(0);
                else {
                    setUserCal(Integer.parseInt(snapshot.getValue().toString()));
                    if (Integer.parseInt(snapshot.getValue().toString()) >= 2000) {
                        Toast.makeText(getApplicationContext(), "Daily Calorie Goal Already Reached!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                setUserCal(0);
            }
        });
        return getUserCal();
    }
}