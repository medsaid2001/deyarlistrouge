package mr.gov.listerouge.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.morpho.android.usb.USBManager;

import mr.gov.listerouge.R;
import mr.gov.listerouge.fragments.DashboardFragment;
import mr.gov.listerouge.fragments.InfoFragment;
import mr.gov.listerouge.fragments.ProfileFragment;
import mr.gov.listerouge.fragments.SettingsFragment;
import mr.gov.listerouge.interfaces.FinishCallback;
import mr.gov.listerouge.workers.SyncService;

public class HomeActivity extends AppCompatActivity implements FinishCallback {

    private BottomNavigationView bottomNavigationView;
    private FinishCallback callback;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        if (savedInstanceState == null) {
            bottomNavigationView = findViewById(R.id.bottomNavigationView);
            setupBottomNavigation();
            bottomNavigationView.setSelectedItemId(R.id.home);
            try {
                USBManager.getInstance().initialize(this, "mr.gov.listerouge.USB_ACTION", true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            // startSyncService();
            // Avoid re-replacing the fragment if it's already added
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentById(R.id.frame_layout);
            if (fragment == null) {
                replaceFragment(new DashboardFragment());
            }
        }

    }

    private void setupBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();
            if (R.id.profile == id) {
                selectedFragment = new ProfileFragment();
            } else if (R.id.settings == id) {
                selectedFragment = new SettingsFragment();
            } else if (R.id.home == id) {
                selectedFragment = new DashboardFragment();
            } else if (R.id.info == id) {
                selectedFragment = new InfoFragment();
            }
            if (selectedFragment != null) {
                replaceFragment(selectedFragment);
            }
            return true;
        });


    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.frame_layout);

        if (currentFragment == null || !currentFragment.getClass().equals(fragment.getClass())) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment);
            fragmentTransaction.commit();
        }
    }

    private void startSyncService() {
        Intent intent = new Intent(this, SyncService.class);

        if (!isServiceRunning(SyncService.class)) {
            ContextCompat.startForegroundService(this, intent);  // If using ForegroundService
        } else {
            Log.d("ServiceCheck", "SyncService is already running");
        }
    }

    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (manager != null) {
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if (serviceClass.getName().equals(service.service.getClassName())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onSuccess() {
        // Handle success callback
    }

    @Override
    public void onError(String message) {
        // Handle error callback
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Stop the SyncService if the activity is destroyed and the service is not needed anymore
        stopSyncService();
    }

    private void stopSyncService() {
        if (isServiceRunning(SyncService.class)) {
            Intent intent = new Intent(this, SyncService.class);
            stopService(intent);  // Stop the SyncService if it is running
            Log.d("ServiceCheck", "SyncService stopped");
        }
    }
}
