package mr.gov.listerouge.activity;

import static mr.gov.listerouge.Constant.APPNAME;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import mr.gov.listerouge.GPSTracker;
import mr.gov.listerouge.R;
import mr.gov.listerouge.StrictModeManager;
import mr.gov.listerouge.api.OkHttpClientInstance;
import mr.gov.listerouge.interfaces.DownloadApkCallback;
import mr.gov.listerouge.models.Coordinates;
import mr.gov.listerouge.models.DeviceInfo;
import mr.gov.listerouge.task.DownloadApkTask;
import mr.gov.listerouge.tools.NetworkUtil;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SplashActivity extends AppCompatActivity implements DownloadApkCallback {

    private static final int SPLASH_TIME_OUT = 5000;
    DownloadApkTask task;
    private static final String TAG = "SplashActivity";

    private GPSTracker gpsTracker;
    private ProgressDialog progressDialog;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private ProgressBar progressBar;
    private mr.gov.listerouge.models.Location loca;
    private TextView tagline;

    // WeakReference to hold the activity
    private WeakReference<SplashActivity> weakReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictModeManager.enableStrictMode();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        initializeFields();
        setupAnimations();
        gpsTracker = new GPSTracker(this);
        loca = new mr.gov.listerouge.models.Location();

        weakReference = new WeakReference<>(this); // Initialize WeakReference to the activity

        handleFakeGPS();
    }
    private void handleFakeGPS() {
        // Generate fake coordinates
        double fakeLongitude = 0000;
        double fakeLatitude = 0000;

        Coordinates coordinates = new Coordinates();
        coordinates.setLng(fakeLongitude);
        coordinates.setLnt(fakeLatitude);
        loca.setCoordinates(coordinates);
        checkInternetAndProceed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
        Log.d(TAG, "onDestroy called");
    }

    private void enableStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());
    }

    private void initializeFields() {
        progressBar = findViewById(R.id.progressBar);
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        loca = new mr.gov.listerouge.models.Location();
    }

    private void setupAnimations() {
        Animation topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animantion);
        Animation middleAnimation = AnimationUtils.loadAnimation(this, R.anim.middle_animation);
        Animation bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animantion);
        tagline = findViewById(R.id.tagLine);
        int[] viewIds = {R.id.first_line, R.id.second_line, R.id.third_line, R.id.fourth_line, R.id.fifth_line, R.id.sixth_line};
        for (int id : viewIds) {
            findViewById(id).setAnimation(topAnimation);
        }
        findViewById(R.id.a).setAnimation(middleAnimation);
        findViewById(R.id.tagLine).setAnimation(bottomAnimation);
    }

    private void handleGPS() {
        if (!gpsTracker.isGPSEnabled()) {
            showGPSAlertDialog();
            navigateToActivity(LoginActivity.class);
        } else {
            Location location = gpsTracker.getLocation();
            if (location != null) {
                Coordinates coordinates = new Coordinates();
                coordinates.setLng(location.getLongitude());
                coordinates.setLnt(location.getLatitude());
                loca.setCoordinates(coordinates);
                checkInternetAndProceed();
            } else {
                navigateToActivity(LoginActivity.class);
            }
        }
    }

    private void showGPSAlertDialog() {
        new AlertDialog.Builder(this)
                .setMessage("GPS is disabled. Do you want to enable it?")
                .setPositiveButton("Yes", (dialog, id) -> startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 200))
                .setNegativeButton("No", (dialog, id) -> dialog.cancel())
                .setCancelable(false)
                .show();
    }

    private void checkInternetAndProceed() {
        if (NetworkUtil.isInternetAvailable(this)) {
            checkOnStart();
        } else {
            showNoInternetDialog();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Cancel the task when the activity is paused
        if (task != null && !task.isCancelled()) {
            task.cancel(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Check if task is null or not running before starting it
        checkOnStart();
    }

    @Override
    public void onBackPressed() {
        // Cancel the task and handle back press
        if (task != null && !task.isCancelled()) {
            task.cancel(true);
        }
        super.onBackPressed(); // Proceed with normal back button behavior
    }

    private void checkOnStart() {
        String updateJsonUrl = "https://anrpts.gov.mr/files/get-file";
        if (task == null || task.getStatus() == DownloadApkTask.Status.FINISHED) {
            // Create a new task if no task is running or the previous one has finished
            task = new DownloadApkTask(this, progressBar, tagline, this);
            task.execute(updateJsonUrl);
        } else if (task.getStatus() == DownloadApkTask.Status.RUNNING) {
            // Task is already running, you may handle this case based on your app's logic
            // Optionally, you can decide to execute the task again or not
            // task.execute(updateJsonUrl);
        }
    }

    private void showNoInternetDialog() {
        new AlertDialog.Builder(this)
                .setMessage("No internet connection. Please check your connection and try again.")
                .setPositiveButton("Retry", (dialog, which) -> checkInternetAndProceed())
                .setNegativeButton("Cancel", (dialog, which) -> navigateToActivity(LoginActivity.class))
                .setCancelable(false)
                .show();
    }

    private ProgressDialog createProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Processing...");
        progressDialog.setCancelable(false);
        return progressDialog;
    }

    private DeviceInfo gatherDeviceInfo(String nni, String phone) {
        DeviceInfo deviceInfo = new DeviceInfo();
        String serialNumber = Build.SERIAL;
        if (serialNumber == null || serialNumber.isEmpty() || "unknown".equals(serialNumber)) {
            serialNumber = generateStaticSerial();
        }

        deviceInfo.setDeviceSn(serialNumber);
        deviceInfo.setNni(nni);
        deviceInfo.setProfil("security");

        if (loca != null && loca.getCoordinates() != null) {
            DeviceInfo.Location location = new DeviceInfo.Location();
            location.setType("Point");
            location.setCoordinates(new double[]{loca.getCoordinates().getLng(), loca.getCoordinates().getLnt()});
            deviceInfo.setLocation(location);
        }

        deviceInfo.setDeviceBrand(Build.BRAND);
        deviceInfo.setDeviceManufacturer(Build.MANUFACTURER);
        deviceInfo.setDeviceModel(Build.MODEL);
        deviceInfo.setDeviceName(Build.DEVICE);
        deviceInfo.setDeviceSystemName("Android");
        deviceInfo.setPhoneNumber(phone);
        deviceInfo.setDeviceSystemVersion(Build.VERSION.RELEASE);
        deviceInfo.setDeviceCarrier(getCarrierName());

        return deviceInfo;
    }

    private String generateStaticSerial() {
        String serialBase = Build.BOARD + Build.BRAND + Build.DEVICE + Build.DISPLAY + Build.HARDWARE + Build.ID +
                Build.MANUFACTURER + Build.MODEL + Build.PRODUCT + Build.TAGS + Build.TYPE + Build.USER;

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(serialBase.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "Hash generation failed", e);
            return null;
        }
    }

    private String getCarrierName() {
        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        return manager != null ? manager.getNetworkOperatorName() : "Unknown";
    }

    private void showProgressDialog() {
        SplashActivity activity = weakReference.get();
        if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
            activity.runOnUiThread(() -> {
                if (progressDialog == null) {
                    progressDialog = createProgressDialog();
                }
                progressDialog.show();
            });
        }
    }

    private void dismissProgressDialog() {
        SplashActivity activity = weakReference.get();
        if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
            activity.runOnUiThread(() -> {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            });
        }
    }

    private void checkDeviceInfo(DeviceInfo deviceInfo) {
        OkHttpClient client = OkHttpClientInstance.getOkHttpClient();
        try {
            JSONObject jsonBody = createRequestBody(deviceInfo);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonBody.toString());
            Request request = new Request.Builder()
                    .url("https://api-houwiyeti.anrpts.gov.mr/houwiyetiapi/v1/partners/checkAnrptsIdStatus2")
                    .post(requestBody)
                    .addHeader("entity-Api-Key", "a96e90c5-d561-4a1d-8307-a22b8999cc9f")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .addHeader("app-name", "deyar_gps")
                    .addHeader("app-version", Integer.toString(getCurrentVersionCode()))
                    .build();
            showProgressDialog(); // Show progress dialog before making the request

            SplashActivity activity = weakReference.get();
            if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
                try (Response response = client.newCall(request).execute()) {
                    if (response.isSuccessful()) {
                        String responseBody = response.body().string();
                        activity.runOnUiThread(() -> handleSuccessfulCheckResponse(responseBody, deviceInfo));
                    } else {
                        activity.runOnUiThread(activity::navigateToLoginActivity);
                    }
                }
            }
        } catch (IOException | JSONException e) {
            Log.e(TAG, "Device info check failed", e);
            navigateToLoginActivity();
        } finally {
            dismissProgressDialog(); // Dismiss progress dialog after the request is done
        }
    }

    private JSONObject createRequestBody(DeviceInfo deviceInfo) throws JSONException {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("deviceSn", deviceInfo.getDeviceSn());
        jsonBody.put("nni", deviceInfo.getNni());
        jsonBody.put("appName", APPNAME);

        JSONObject locationObject = new JSONObject();
        locationObject.put("type", "Point");

        JSONArray coordinates = new JSONArray();
        double[] cdn = deviceInfo.getLocation().getCoordinates();
        coordinates.put(cdn[0]);
        coordinates.put(cdn[1]);

        locationObject.put("coordinates", coordinates);
        jsonBody.put("location", locationObject);

        return jsonBody;
    }

    private void handleSuccessfulCheckResponse(String responseBody, DeviceInfo deviceInfo) {
        SplashActivity activity = weakReference.get();
        if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
            try {
                JSONObject responseObject = new JSONObject(responseBody);
                String type = responseObject.getString("type");
                int ccd = responseObject.getInt("code");
                if ("successCode".equals(type) && ccd == 1) {
                    String profile = responseObject.getString("profil");
                    String appid = responseObject.getString("id");
                    editor.putString("profile", profile);
                    editor.putString("appid",appid );
                    editor.commit();
                    activity.navigateToActivity(HomeActivity.class);
                } else if ("errorCode".equals(type) && ccd == 1) {
                    activity.navigateToActivity(WaitingActivity.class);
                } else if ("errorCode".equals(type) && ccd == 6) {
                    activity.navigateToActivity(LoginActivity.class);
                }
            } catch (JSONException e) {
                Log.e(TAG, "Response handling failed", e);
            }
        }
    }

    private void navigateToActivity(Class<?> activityClass) {
        SplashActivity activity = weakReference.get();
        if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
            activity.runOnUiThread(() -> {
                Intent intent = new Intent(activity, activityClass);
                startActivity(intent);
            });
        }
    }

    private void navigateToLoginActivity() {
        navigateToActivity(LoginActivity.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200) {
            navigateToLoginActivity();
        }
    }

    @Override
    public void onDownloadComplete(String result) {
        if ("App is up-to-date".equals(result)) {
            String nni = sharedPreferences.getString("nni", null);
            String serialNumber = sharedPreferences.getString("serial", null);
            if (nni != null && serialNumber != null) {
                ProgressDialog progressDialog = createProgressDialog();
                DeviceInfo deviceInfo = gatherDeviceInfo(nni, serialNumber);
                if (deviceInfo != null) {
                    executorService.execute(() -> checkDeviceInfo(deviceInfo));
                }
            } else {
                navigateToActivity(LoginActivity.class);
            }
        }
    }

    private int getCurrentVersionCode() {
        try {
            PackageInfo packageInfo = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void onProgressUpdate(int progress) {
        runOnUiThread(() -> tagline.setText(progress + "%"));
    }
}