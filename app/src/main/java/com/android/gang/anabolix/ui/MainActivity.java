package com.android.gang.anabolix.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.android.gang.anabolix.R;
import com.android.gang.anabolix.db.RunDAO;
import com.android.gang.anabolix.other.Constants;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private BottomNavigationView bottomNavigationView;

    @Inject
    public RunDAO mRunDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Timber.plant(new Timber.DebugTree());
        bottomNavigationView = findViewById(R.id.bottomNav);
        navigateToTrackingFragmentIfNeeded(getIntent());
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_container_main);
        assert navHostFragment != null;
        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.getNavController());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        navigateToTrackingFragmentIfNeeded(getIntent());
    }

    private void navigateToTrackingFragmentIfNeeded(Intent intent) {
        if (Objects.equals(intent.getAction(), Constants.ACTION_SHOW_TRACKING_FRAGMENT)) {
            NavHostFragment navHostFragment =
                    (NavHostFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.nav_host_fragment_container_main);

            assert navHostFragment != null;
            NavHostFragment.findNavController(navHostFragment).navigate(R.id.action_global_nav_graph);
        }
    }
}
