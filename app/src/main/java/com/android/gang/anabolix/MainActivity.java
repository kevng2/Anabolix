package com.android.gang.anabolix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.gang.anabolix.db.RunDAO;
import com.android.gang.anabolix.db.RunDAO_Impl;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button mKevinButton;
    private Button mSamButton;
    private Button mLewisButton;
    private Button mCarterButton;
    private Button mTiffanyButton;
    private BottomNavigationView bottomNavigationView;

    @Inject
    public RunDAO mRunDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = getCurrentFocus();
        Log.d(TAG, "onCreate: " + mRunDAO.hashCode());

        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navMethod);
        bottomNavigationView.setItemTextColor(ColorStateList.valueOf(Color.RED));
        bottomNavigationView.setItemIconTintList(ColorStateList.valueOf(Color.RED));
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, new HomeFragment()).commit();

        if (LoginActivity.checkLoggedIn() == false)
            sendLogin(v);

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
                Intent intent = new Intent(MainActivity.this, CalorieCounterActivity.class);
                startActivity(intent);
            }
        });

        mLewisButton = findViewById(R.id.lewis_button);
        mLewisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                signOut();
            }
        });

        mCarterButton = findViewById(R.id.carter_button);
        mCarterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AlarmActivity.class);
                startActivity(intent);
            }
        });

        mTiffanyButton = findViewById(R.id.tiffany_button);
        mTiffanyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecyclerActivity.class);
                startActivity(intent);
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navMethod = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment fragment = null;

                    switch (item.getItemId()) {
                        case R.id.home:
                            fragment = new HomeFragment();
                            break;
                        case R.id.exercise:
                            fragment = new ExerciseFragment();
                            break;
                        case R.id.stats:
                            fragment = new StatsFragment();
                            break;
                        case R.id.timer:
                            fragment = new TimerFragment();
                            break;
                        case R.id.weather:
                            fragment = new WeatherFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment).commit();
                    return true;
                }
            };

    private void sendLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void signOut() {
        // [START auth_fui_signout]
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        finish();
                        startActivity(getIntent());
                    }
                });
    }
}
