package com.android.gang.anabolix.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;

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
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_fragment_container);
        assert navHostFragment != null;
        NavigationUI.setupWithNavController(mBottomNavigationView, navHostFragment.getNavController());

        NavHostFragment
                .findNavController(navHostFragment)
                .addOnDestinationChangedListener((controller, destination, arguments) -> {
                    if (destination.getId() == R.id.runFragment ||
                            destination.getId() == R.id.settingsFragment ||
                            destination.getId() == R.id.statisticsFragment) {
                        mBottomNavigationView.setVisibility(View.VISIBLE);
                    } else
                        mBottomNavigationView.setVisibility(View.GONE);
                });
    }
}
