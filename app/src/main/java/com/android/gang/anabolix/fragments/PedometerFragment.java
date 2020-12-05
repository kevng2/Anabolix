package com.android.gang.anabolix.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.gang.anabolix.R;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class PedometerFragment extends Fragment implements SensorEventListener {
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pedometer_fragment, container, false);
        mStepCount = v.findViewById(R.id.step_count_text);
        mStepCountProgress = v.findViewById(R.id.step_count_out_of);
        mSensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        mCircularProgressBar = v.findViewById(R.id.progress_circular);
        loadData();
        resetSteps();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mIsRunning = true;
        Sensor stepSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if (stepSensor == null) {
            Toast.makeText(getActivity(), "No sensor detected on this device", Toast.LENGTH_SHORT).show();
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

    private void resetSteps() {
        mStepCount.setOnClickListener(v -> Toast.makeText(getActivity(), "Long tap to reset steps", Toast.LENGTH_SHORT).show());

        mStepCount.setOnLongClickListener(v -> {
            mPreviousTotalSteps = mTotalSteps;
            mStepCount.setText("0");
            mStepCountProgress.setText(getString(R.string.step_count_out_of_num, 0));
            saveData();
            return true;
        });
    }

    private void saveData() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(PREVIOUS_TOTAL_STEPS, mPreviousTotalSteps);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        float savedNumber = sharedPreferences.getFloat(PREVIOUS_TOTAL_STEPS, 0f);
        Log.d(TAG, "loadData: " + savedNumber);
        mPreviousTotalSteps = savedNumber;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
