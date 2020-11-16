package com.android.gang.anabolix.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import android.os.Bundle;
import com.android.gang.anabolix.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RunActivity extends AppCompatActivity {
    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);
        setupNavigation();
    }

    private void setupNavigation() {
        mBottomNavigationView = findViewById(R.id.bottom_run_navigation_view);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_container);
        NavigationUI.setupWithNavController(mBottomNavigationView, navHostFragment.getNavController());
    }
}
