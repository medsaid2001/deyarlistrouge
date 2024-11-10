package mr.gov.listerouge.fragments;

import static mr.gov.listerouge.Constant.APPNAME;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import mr.gov.listerouge.GPSTracker;
import mr.gov.listerouge.R;
import mr.gov.listerouge.api.ApiRedlistResponse;
import mr.gov.listerouge.api.RetrofitClientInstance;
import mr.gov.listerouge.database.RedListItemDao;
import mr.gov.listerouge.database.SyncTimeDao;
import mr.gov.listerouge.database.AppDatabase;
import mr.gov.listerouge.models.DeviceInfo;
import mr.gov.listerouge.models.RedListItem;
import mr.gov.listerouge.models.RedlistRequest;
import mr.gov.listerouge.models.SyncTime;
import mr.gov.listerouge.network.NetworkService;
import mr.gov.listerouge.tools.NetworkUtil;
import mr.gov.listerouge.workers.SyncWorker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends Fragment {

    private static final String TAG = "SettingsFragment";
    private RedListItemDao redListItemDao;
    private AppDatabase appDatabase;

    // Member variables
    private String nni;
    private String serial;
    private String appId;
    private String appName;
    private String appVersion;

    private DeviceInfo.Location locationData;
    private SharedPreferences sharedPreferences;
    private boolean isLoading = false;

    // UI components
    private GPSTracker gpsTracker;
    private ProgressBar progressBar;
    private AppCompatButton syncButton;

    private EditText syncIntervalInput;
    private AppCompatButton setSyncIntervalButton;
    private TextView lastSyncTimeLabel;

    private long syncIntervalMinutes = 1; // Default value

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Initialize UI components
        syncButton = view.findViewById(R.id.syncbutton);
        progressBar = view.findViewById(R.id.progressBar);

        syncIntervalInput = view.findViewById(R.id.sync_interval_input);
        setSyncIntervalButton = view.findViewById(R.id.set_sync_interval_button);
        lastSyncTimeLabel = view.findViewById(R.id.last_sync_time_label);

        // Load shared preferences
        sharedPreferences = requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        nni = sharedPreferences.getString("nni", null);
        appId = sharedPreferences.getString("appid", null);
        serial = sharedPreferences.getString("serial", null);
        appName = APPNAME;
        appVersion = String.valueOf(getCurrentVersionCode());

        // Load saved sync interval
        syncIntervalMinutes = sharedPreferences.getLong("sync_interval_minutes", 1);
        syncIntervalInput.setText(String.valueOf(syncIntervalMinutes));

        // Set up the set sync interval button
        setSyncIntervalButton.setOnClickListener(v -> {
            String input = syncIntervalInput.getText().toString();
            if (!input.isEmpty()) {
                try {
                    syncIntervalMinutes = Long.parseLong(input);
                    if (syncIntervalMinutes < 1) {
                        Toast.makeText(requireContext(), "Minimum sync interval is 15 minutes.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // Save to shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putLong("sync_interval_minutes", syncIntervalMinutes);
                    editor.apply();


                    Toast.makeText(requireContext(), "Sync interval set to " + syncIntervalMinutes + " minutes", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    Toast.makeText(requireContext(), "Please enter a valid number.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(requireContext(), "Please enter a valid interval.", Toast.LENGTH_SHORT).show();
            }
        });

        // Initialize GPS tracker
        gpsTracker = new GPSTracker(requireContext());
        locationData = new DeviceInfo.Location();

        // Initialize the database and DAO
        appDatabase = AppDatabase.getInstance(requireContext());
        redListItemDao = appDatabase.redListItemDao();

        // Schedule the worker on first launch or when fragment is created
      //  scheduleSyncWorker(syncIntervalMinutes);

        // Display last sync time
        displayLastSyncTime();

        // Set up sync button click listener
        syncButton.setOnClickListener(v -> {
            fetchData();
            progressBar.setVisibility(View.VISIBLE);
            syncButton.setEnabled(false);
        });

        // Handle GPS and proceed
        handleGPS();

        return view;
    }

    private void fetchData() {
        if (isLoading) return;
        isLoading = true;

        // Prepare the request
        RedlistRequest redlistRequest = new RedlistRequest(serial, locationData);

        NetworkService service = RetrofitClientInstance.getRetrofitInstance().create(NetworkService.class);
        Call<ApiRedlistResponse> call = service.getItems2(
                "a96e90c5-d561-4a1d-8307-a22b8999cc9f",
                nni,
                redlistRequest,
                "deyar_redlist",
                appVersion,
                appId,
                1,  // Page number
                10  // Page size
        );

        call.enqueue(new Callback<ApiRedlistResponse>() {
            @Override
            public void onResponse(Call<ApiRedlistResponse> call, Response<ApiRedlistResponse> response) {
                isLoading = false;
                progressBar.setVisibility(View.GONE);
                syncButton.setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {
                    List<RedListItem> items = response.body().getData();

                    // Insert data into the database in a background thread
                    Executors.newSingleThreadExecutor().execute(() -> {
                        redListItemDao.insertAll(items);
                        Log.d(TAG, "Inserted " + items.size() + " items into the database.");

                        // Update sync time
                        SyncTimeDao syncTimeDao = appDatabase.syncTimeDao();
                        SyncTime syncTime = new SyncTime();
                        syncTime.setId(1); // Single row
                        syncTime.setLastSyncTime(System.currentTimeMillis());
                        syncTimeDao.insertOrUpdate(syncTime);

                        // Update the UI
                        requireActivity().runOnUiThread(() -> {
                            displayLastSyncTime();
                            Toast.makeText(requireContext(), "Data synced successfully", Toast.LENGTH_SHORT).show();
                        });
                    });

                } else {
                    handleErrorResponse(response);
                }
            }

            @Override
            public void onFailure(Call<ApiRedlistResponse> call, Throwable t) {
                isLoading = false;
                progressBar.setVisibility(View.GONE);
                syncButton.setEnabled(true);
                Log.e(TAG, "API Error: " + t.getMessage());
                Toast.makeText(requireContext(), "Network error occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleErrorResponse(Response<ApiRedlistResponse> response) {
        Log.e(TAG, "API Error: Code " + response.code() + ", Message: " + response.message());
        try {
            if (response.errorBody() != null) {
                Log.e(TAG, "Error body: " + response.errorBody().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(requireContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
    }

    private void scheduleSyncWorker(long intervalMinutes) {
        // Ensure minimum interval of 15 minutes due to WorkManager constraints
        if (intervalMinutes < 2) {
            intervalMinutes = 1;
        }

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest syncWorkRequest =
                new PeriodicWorkRequest.Builder(SyncWorker.class, intervalMinutes, TimeUnit.MINUTES)
                        .setConstraints(constraints)
                        .build();

        WorkManager.getInstance(getContext()).enqueueUniquePeriodicWork(
                "SyncWorker2",
                ExistingPeriodicWorkPolicy.REPLACE,
                syncWorkRequest);
    }

    private void displayLastSyncTime() {
        Executors.newSingleThreadExecutor().execute(() -> {
            SyncTimeDao syncTimeDao = appDatabase.syncTimeDao();
            SyncTime syncTime = syncTimeDao.getSyncTime();

            requireActivity().runOnUiThread(() -> {
                if (syncTime != null) {
                    String lastSync = DateFormat.getDateTimeInstance().format(new Date(syncTime.getLastSyncTime()));
                    lastSyncTimeLabel.setText("Last Sync Time: " + lastSync);
                } else {
                    lastSyncTimeLabel.setText("Last Sync Time: Never");
                }
            });
        });
    }

    private int getCurrentVersionCode() {
        try {
            PackageInfo packageInfo = requireContext()
                    .getPackageManager()
                    .getPackageInfo(requireContext().getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private void handleGPS() {
        if (!gpsTracker.isGPSEnabled()) {
            showGPSAlertDialog();
        } else {
            Location location = gpsTracker.getLocation();
            if (location != null) {
                locationData.setType("Point");
                locationData.setCoordinates(new double[]{location.getLongitude(), location.getLatitude()});
                checkInternetAndProceed();
            } else {
                showGPSAlertDialog();
            }
        }
    }

    private void showGPSAlertDialog() {
        new AlertDialog.Builder(requireContext())
                .setMessage("GPS is disabled or cannot obtain location. Do you want to enable it?")
                .setPositiveButton("Yes", (dialog, id) ->
                        startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 200))
                .setNegativeButton("No", (dialog, id) -> dialog.cancel())
                .setCancelable(false)
                .show();
    }

    private void checkInternetAndProceed() {
        if (NetworkUtil.isInternetAvailable(requireContext())) {
            if (nni != null && !nni.isEmpty() && serial != null && !serial.isEmpty()) {
                fetchData();
            } else {
                Toast.makeText(requireContext(), "NNI or Serial Number is missing", Toast.LENGTH_SHORT).show();
            }
        } else {
            showNoInternetDialog();
        }
    }

    private void showNoInternetDialog() {
        new AlertDialog.Builder(requireContext())
                .setMessage("No internet connection. Please check your connection and try again.")
                .setPositiveButton("Retry", (dialog, which) -> checkInternetAndProceed())
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .setCancelable(false)
                .show();
    }

}
