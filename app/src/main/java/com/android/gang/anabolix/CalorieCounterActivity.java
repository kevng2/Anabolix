package com.android.gang.anabolix;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_counter);


        editCalorie = (EditText) findViewById(R.id.editCalorie);
        AddCalorieButton = (Button) findViewById(R.id.addCalories);
        SubtractCalorieButton = (Button) findViewById(R.id.subtractCalories);
        CalorieDisplay = (TextView) findViewById(R.id.CalorieDisplay);
        mCake = findViewById(R.id.imageView2);

        Glide
                .with(this)
                .load(R.drawable.cake)
                .into(mCake);


        CalorieDisplay.setText("0");

        AddCalorieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int Add = Integer.parseInt(editCalorie.getText().toString());
                int result = Add + Integer.parseInt(CalorieDisplay.getText().toString());
                CalorieDisplay.setText(Integer.toString(result));
            }
        });

        SubtractCalorieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int Sub = Integer.parseInt(editCalorie.getText().toString());
                int result = Integer.parseInt(CalorieDisplay.getText().toString()) - Sub;
                CalorieDisplay.setText(Integer.toString(result));
            }
        });


    }

}