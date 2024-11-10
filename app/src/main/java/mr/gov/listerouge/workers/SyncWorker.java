package mr.gov.listerouge.workers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import mr.gov.listerouge.GPSTracker;
import mr.gov.listerouge.api.ApiRedlistResponse;
import mr.gov.listerouge.database.RedListItemDao;
import mr.gov.listerouge.database.SyncTimeDao;
import mr.gov.listerouge.api.RetrofitClientInstance;
import mr.gov.listerouge.database.AppDatabase;
import mr.gov.listerouge.models.DeviceInfo;
import mr.gov.listerouge.models.RedListItem;
import mr.gov.listerouge.models.RedlistRequest;
import mr.gov.listerouge.models.SyncTime;
import mr.gov.listerouge.network.NetworkService;
import mr.gov.listerouge.tools.NetworkUtil;
import retrofit2.Call;
import retrofit2.Response;

public class SyncWorker extends Worker {

    private static final String TAG = "SyncWorker2";

    public SyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        // Perform the sync operation
        if (NetworkUtil.isInternetAvailable(getApplicationContext())) {
            // Fetch data and update the database, return appropriate result
            return fetchDataOnBackgroundThread();
        } else {
            return Result.retry();
        }
    }

    private Result fetchDataOnBackgroundThread() {
        final boolean[] result = {false};
        // Use Handler to execute location fetching on the main thread
        Handler mainHandler = new Handler(Looper.getMainLooper());
        mainHandler.post(() -> {
            result[0] = fetchData();
        });

        // Wait until the main thread finishes fetching location data
        try {
            Thread.sleep(2000); // Adjust sleep time as needed for asynchronous operations
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Return the result of the sync operation
        return result[0] ? Result.success() : Result.retry();
    }

    private boolean fetchData() {
        try {
            // Load shared preferences
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("data", Context.MODE_PRIVATE);
            String nni = sharedPreferences.getString("nni", null);
            String appId = sharedPreferences.getString("appid", null);
            String serial = sharedPreferences.getString("serial", null);
            String appVersion = String.valueOf(getCurrentVersionCode());

            if (nni == null || nni.isEmpty() || serial == null || serial.isEmpty()) {
                Log.e(TAG, "NNI or Serial Number is missing");
                return false;
            }

            // Get GPS location on main thread using GPSTracker
            GPSTracker gpsTracker = new GPSTracker(getApplicationContext());
            DeviceInfo.Location locationData = new DeviceInfo.Location();

            if (gpsTracker.isGPSEnabled()) {
                Location location = gpsTracker.getLocation();
                if (location != null) {
                    locationData.setType("Point");
                    locationData.setCoordinates(new double[]{location.getLongitude(), location.getLatitude()});
                } else {
                    Log.e(TAG, "Unable to obtain location");
                    return false;
                }
            } else {
                Log.e(TAG, "GPS is disabled");
                return false;
            }

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

            Response<ApiRedlistResponse> response = call.execute(); // Synchronous call
            if (response.isSuccessful() && response.body() != null) {
                List<RedListItem> items = response.body().getData();

                // Insert data into the database
                AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());
                RedListItemDao redListItemDao = appDatabase.redListItemDao();
                redListItemDao.insertAll(items);

                Log.d(TAG, "Inserted " + items.size() + " items into the database.");

                // Update sync time
                SyncTimeDao syncTimeDao = appDatabase.syncTimeDao();
                SyncTime syncTime = new SyncTime();
                syncTime.setId(1); // Single row
                syncTime.setLastSyncTime(System.currentTimeMillis());
                syncTimeDao.insertOrUpdate(syncTime);

                return true;
            } else {
                Log.e(TAG, "API Error: Code " + response.code() + ", Message: " + response.message());
                if (response.errorBody() != null) {
                    Log.e(TAG, "Error body: " + response.errorBody().string());
                }
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private int getCurrentVersionCode() {
        try {
            PackageInfo packageInfo = getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(getApplicationContext().getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
