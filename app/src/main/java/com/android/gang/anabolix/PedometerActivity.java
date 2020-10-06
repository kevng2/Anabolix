package com.android.gang.anabolix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class PedometerActivity extends AppCompatActivity implements SensorEventListener {
    private static final String TAG = "PedometerActivity";
    private final String USER_PREFERENCES = "user_preferences";
    private final String PREVIOUS_TOTAL_STEPS = "previous_total_steps";
    private SensorManager mSensorManager;
    private CircularProgressBar mCircularProgressBar;
    private boolean mIsRunning;
    private float mTotalSteps = 0f;
    private float mPreviousTotalSteps = 0f;
    private TextView mStepCount;
    private TextView mStepCountProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedometer);
        mStepCount = findViewById(R.id.step_count_text);
        mStepCountProgress = findViewById(R.id.step_count_out_of);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mCircularProgressBar = findViewById(R.id.progress_circular);
        loadData();
        resetSteps();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIsRunning = true;
        Sensor stepSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if (stepSensor == null) {
            Toast.makeText(this, "No sensor detected on this device", Toast.LENGTH_SHORT).show();
        } else {
            mSensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (mIsRunning) {
            mTotalSteps = event.values[0];
            float currentSteps = (int) mTotalSteps - (int) mPreviousTotalSteps;
            mStepCount.setText(String.valueOf((int) currentSteps));
            mStepCountProgress.setText(getString(R.string.step_count_out_of_num, (int) currentSteps));
            mCircularProgressBar.setProgressWithAnimation(currentSteps);
        }
    }

    void resetSteps() {
        mStepCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PedometerActivity.this, "Long tap to reset steps", Toast.LENGTH_SHORT).show();
            }
        });

        mStepCount.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mPreviousTotalSteps = mTotalSteps;
                mStepCount.setText("0");
                mStepCountProgress.setText(getString(R.string.step_count_out_of_num, 0));
                saveData();
                return true;
            }
        });
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(PREVIOUS_TOTAL_STEPS, mPreviousTotalSteps);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        float savedNumber = sharedPreferences.getFloat(PREVIOUS_TOTAL_STEPS, 0f);
        Log.d(TAG, "loadData: " + savedNumber);
        mPreviousTotalSteps = savedNumber;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
