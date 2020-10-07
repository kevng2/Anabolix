package com.android.gang.anabolix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button mKevinButton;
    private Button mSamButton;
    private Button mLewisButton;
    private Button mCarterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mKevinButton = findViewById(R.id.kevin_button);
        mKevinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PedometerActivity.class);
                startActivity(intent);
            }
        });


        mSamButton = findViewById(R.id.sam_button);
        mSamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Hi, I'm Sam!", Toast.LENGTH_SHORT).show();
            }
        });

        mLewisButton = findViewById(R.id.lewis_button);
        mLewisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Hey, this is probably Lewis!", Toast.LENGTH_SHORT).show();
            }
        });

        mCarterButton = findViewById(R.id.carter_button);
        mCarterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "toooooast", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
