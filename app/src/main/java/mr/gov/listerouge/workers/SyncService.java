package mr.gov.listerouge.workers;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import mr.gov.listerouge.GPSTracker;
import mr.gov.listerouge.api.ApiRedlistResponse;
import mr.gov.listerouge.api.RetrofitClientInstance;
import mr.gov.listerouge.database.AppDatabase;
import mr.gov.listerouge.database.RedListItemDao;
import mr.gov.listerouge.database.SyncTimeDao;
import mr.gov.listerouge.models.DeviceInfo;
import mr.gov.listerouge.models.RedListItem;
import mr.gov.listerouge.models.RedlistRequest;
import mr.gov.listerouge.models.SyncTime;
import mr.gov.listerouge.network.NetworkService;
import retrofit2.Call;
import retrofit2.Response;

public class SyncService extends Service {

    private static final String TAG = "SyncService";
    private static final long DEFAULT_INTERVAL = 2 * 60 * 1000; // Default 2 minutes in milliseconds
    private Handler handler;
    private Runnable syncRunnable;
    private long syncIntervalMillis;
    private SharedPreferences sharedPreferences;
    private ExecutorService executorService;

    @Override
    public void onCreate() {
        super.onCreate();

        // Load shared preferences to get the sync interval (2 minutes default if null)
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        syncIntervalMillis = sharedPreferences.getLong("sync_interval", DEFAULT_INTERVAL);

        handler = new Handler(Looper.getMainLooper());
        executorService = Executors.newSingleThreadExecutor(); // Use a single-thread executor for background operations

        // Define the periodic sync task
        syncRunnable = new Runnable() {
            @Override
            public void run() {
                // Perform the sync task on a background thread
                executorService.execute(() -> fetchLocationAndSync());

                // Schedule the next sync based on the interval
                handler.postDelayed(this, DEFAULT_INTERVAL);
            }
        };

        // Start the initial sync
        handler.post(syncRunnable);

        Log.d(TAG, "Service created, syncing every " + syncIntervalMillis / 1000 + " seconds.");
        showToast("Sync Service Started");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Handle intent and start the service
        return START_STICKY; // Keep the service running even after the app is closed
    }



    @Override
    public IBinder onBind(Intent intent) {
        return null; // We are not binding this service
    }

    // Using FusedLocationProviderClient to fetch location asynchronously
    private void fetchLocationAndSync() {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                DeviceInfo.Location locationData = new DeviceInfo.Location();
                locationData.setType("Point");
                locationData.setCoordinates(new double[]{location.getLongitude(), location.getLatitude()});
                executorService.execute(() -> syncDatabase(locationData));  // Run syncDatabase on the background thread
            } else {
                Log.e(TAG, "Unable to obtain location");
            }
        }).addOnFailureListener(e -> Log.e(TAG, "Error fetching location", e));

    }

    private void syncDatabase(DeviceInfo.Location locationData) {
        try {
            String nni = sharedPreferences.getString("nni", null);
            String appId = sharedPreferences.getString("appid", null);
            String serial = sharedPreferences.getString("serial", null);
            String appVersion = String.valueOf(getCurrentVersionCode());

            if (nni == null || nni.isEmpty() || serial == null || serial.isEmpty()) {
                Log.e(TAG, "NNI or Serial Number is missing");
                return;
            }

            RedlistRequest redlistRequest = new RedlistRequest(serial, locationData);
            NetworkService service = RetrofitClientInstance.getRetrofitInstance().create(NetworkService.class);
            Call<ApiRedlistResponse> call = service.getItems2(
                    "a96e90c5-d561-4a1d-8307-a22b8999cc9f", nni, redlistRequest, "deyar_redlist", appVersion, appId, 1, 10);

            // Execute the network call asynchronously
            call.enqueue(new retrofit2.Callback<ApiRedlistResponse>() {
                @Override
                public void onResponse(Call<ApiRedlistResponse> call, retrofit2.Response<ApiRedlistResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<RedListItem> items = response.body().getData();

                        // Insert data into the database
                        executorService.execute(() -> {
                            AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());
                            RedListItemDao redListItemDao = appDatabase.redListItemDao();
                            redListItemDao.insertAll(items);

                            // Update sync time
                            SyncTimeDao syncTimeDao = appDatabase.syncTimeDao();
                            SyncTime syncTime = new SyncTime();
                            syncTime.setId(1); // Single row
                            syncTime.setLastSyncTime(System.currentTimeMillis());
                            syncTimeDao.insertOrUpdate(syncTime);

                            Log.d(TAG, "Sync completed successfully.");
                        });
                    } else {
                        Log.e(TAG, "API Error: Code " + response.code() + ", Message: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<ApiRedlistResponse> call, Throwable t) {
                    Log.e(TAG, "Network request failed", t);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Error syncing database", e);
        }
    }

    private void showToast(String message) {
        // Use the main thread to show the toast
        new Handler(Looper.getMainLooper()).post(() -> {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        });
    }

    private int getCurrentVersionCode() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(syncRunnable);

        // Shut down executorService properly
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(1, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
                if (!executorService.awaitTermination(1, TimeUnit.SECONDS)) {
                    Log.e(TAG, "ExecutorService did not terminate");
                }
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }

        Log.d(TAG, "Service destroyed.");
        showToast("Sync Service Stopped");
    }

}
