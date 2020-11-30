package com.android.gang.anabolix.ui;

import android.os.Bundle;

import com.android.gang.anabolix.R;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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


        editCalorie = (EditText) findViewById(R.id.editCalorie);
        AddCalorieButton = (Button) findViewById(R.id.addCalories);
        SubtractCalorieButton = (Button) findViewById(R.id.subtractCalories);
        CalorieDisplay = (TextView) findViewById(R.id.CalorieDisplay);
        mCake = findViewById(R.id.imageView2);

        Glide
                .with(this)
                .load(R.drawable.cake)
                .into(mCake);


        CalorieDisplay.setText(Integer.toString(getUserCal()));
        AddCalorieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int Add = Integer.parseInt(editCalorie.getText().toString());
                int result = Add + Integer.parseInt(CalorieDisplay.getText().toString());
                UpdateUserCalorie(result);
                CalorieDisplay.setText(Integer.toString(result));
            }
        });

        SubtractCalorieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int Sub = Integer.parseInt(editCalorie.getText().toString());
                int result = Integer.parseInt(CalorieDisplay.getText().toString()) - Sub;
                UpdateUserCalorie(result);
                CalorieDisplay.setText(Integer.toString(result));
            }
        });


    }

    private void UpdateUserCalorie(int result){
        mDatabase.child("users").child(userId).child("calories").setValue(result);
    }

    private void setUserCal(int value){
        userCal = value;
    }

    private int getUserCal(){
        return userCal;
    }

    private int getInitialCal(){
        mDatabase.child("users").child(userId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String prevChildKey) {
                if (snapshot.getValue() == null)
                    setUserCal(0);
                else {
                    setUserCal(Integer.parseInt(snapshot.getValue().toString()));
                    //Loads correct info here
                    Log.d("tag", Integer.toString(getUserCal()));

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