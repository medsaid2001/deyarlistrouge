package mr.gov.listerouge.activity;

import static mr.gov.listerouge.Constant.APPNAME;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.airbnb.lottie.LottieAnimationView;
import com.fredporciuncula.phonemoji.PhonemojiTextInputEditText;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import mr.gov.listerouge.Constant;
import mr.gov.listerouge.GPSTracker;
import mr.gov.listerouge.R;
import mr.gov.listerouge.api.OkHttpClientInstance;
import mr.gov.listerouge.interfaces.WaitingCallback;
import mr.gov.listerouge.models.Coordinates;
import mr.gov.listerouge.models.DeviceInfo;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
public class LoginActivity extends AppCompatActivity implements WaitingCallback {
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "LoginActivity";
    GPSTracker gpsTracker;
    String selectedItem =null;
    SharedPreferences.Editor editor;
    private boolean showOneTapUI = true;
    private boolean isfirsttime;
    AppCompatButton login;
    LottieAnimationView googleion;
    SharedPreferences sharedPreferences;
    private EditText nni;
    private PhonemojiTextInputEditText phoneEditText;
    private AppCompatButton cirLoginButton;
    private ProgressBar progressBar;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    WaitingCallback callback;
    private Spinner spinner;
    mr.gov.listerouge.models.Location loca;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        gpsTracker = new GPSTracker(this);
        callback = this;
        if(sharedPreferences==null){
            sharedPreferences=  this.getSharedPreferences("data", MODE_PRIVATE);
        }
        editor = sharedPreferences.edit();

        isfirsttime = sharedPreferences.getBoolean(Constant.IS_FIRST_TIME, true);
        gpsTracker = new GPSTracker(LoginActivity.this);
        loca = new mr.gov.listerouge.models.Location();
        Coordinates coordinates = new Coordinates();
        cirLoginButton = findViewById(R.id.cirLoginButton);
        progressBar = findViewById(R.id.progressBar);
        nni = findViewById(R.id.nni);
        phoneEditText = findViewById(R.id.phoneEditText);
        spinner = findViewById(R.id.structureSpinner);

        cirLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = phoneEditText.getText().toString().trim();
                String nnitxt = nni.getText().toString().trim();
                DeviceInfo deviceInfo = null;
                if (selectedItem.isEmpty() || phone.isEmpty() || nnitxt.isEmpty() || phoneEditText.getError() != null || nni.getError() != null ) {
                    Toast.makeText(LoginActivity.this, "Please fill in both fields", Toast.LENGTH_SHORT).show();
                    cirLoginButton.setEnabled(true);
                } else {


                    if (!gpsTracker.isGPSEnabled()) {
                        Toast.makeText(LoginActivity.this, "Please enable gps", Toast.LENGTH_SHORT).show();
                        cirLoginButton.setEnabled(true);
                        showGPSAlertDialog();
                    }else {
                        Location location = gpsTracker.getLocation();
                        if (location != null) {
                            coordinates.setLng(location.getLatitude());
                            coordinates.setLnt(location.getLongitude());
                            loca.setCoordinates(coordinates);
                            cirLoginButton.setEnabled(false);
                            cirLoginButton.setText("");
                            progressBar.setVisibility(View.VISIBLE);
                            deviceInfo = gatherDeviceInfoAndSendRequest(nnitxt,phone);

                            editor.putString("nni", deviceInfo.getNni());
                            editor.putString("structure", deviceInfo.getStructure());
                            editor.putString("serial", deviceInfo.getDeviceSn());
                            editor.putString("mobile", deviceInfo.getPhoneNumber());
                            editor.commit();
                            check(deviceInfo);
                        } else {
                            Toast.makeText(LoginActivity.this, "can not get gps cordinates", Toast.LENGTH_SHORT).show();
                            cirLoginButton.setEnabled(true);
                        }
                    }

                }
            }
        });
        phoneEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        nni.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});

        // Add TextWatcher for validation
        phoneEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()  > 12 && s.length()  < 20) {

                    phoneEditText.setError(null);
                } else {
                    phoneEditText.setError("Maximum length is 20");
                }
            }
        });
        nni.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 10) {
                    nni.setError(null); // Clear the error
                } else {
                    nni.setError("Maximum length is 10");
                }
            }
        });
        String[] spinnerItems = getResources().getStringArray(R.array.structure_array);

        // Create an ArrayAdapter using the string array and a built-in spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,  // Use a predefined layout for the spinner item
                spinnerItems);  // String array with the items

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the Spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = (String) parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedItem = null;
            }
        });
    }


    private void checkonstart(){
        String nnitxt = sharedPreferences.getString("nni",null);
        String serialnumber = sharedPreferences.getString("serial",null);
        if(nnitxt!=null&& serialnumber!=null){
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Processing...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            DeviceInfo deviceInfo = gatherDeviceInfoAndSendRequest(nnitxt,serialnumber);
            if(deviceInfo!=null){
                new Thread(() -> {
                    try {
                        check(deviceInfo);
                    } catch (Exception e) {
                        runOnUiThread(() -> {
                            e.printStackTrace();

                        });
                    } finally {
                        // Dismiss the progress dialog
                        progressDialog.dismiss();
                    }
                }).start();

            }
        }
    }
    private void register(String jsonstring) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = OkHttpClientInstance.getOkHttpClient();

                    MediaType mediaType = MediaType.parse("application/json");
                    RequestBody requestBody = RequestBody.create(mediaType, jsonstring);

                    // Build your request with headers and body
                    Request request = new Request.Builder()
                            .url("https://api-houwiyeti.anrpts.gov.mr/houwiyetiapi/v1/partners/registerAnrptsId2")
                            .method("POST", requestBody)
                            .addHeader("entity-Api-Key", "a96e90c5-d561-4a1d-8307-a22b8999cc9f")
                            .addHeader("appVersion", "0")
                            .addHeader("Content-Type", "application/json")
                            .addHeader("Accept", "application/json")
                            .build();

                    // Execute the request
                    Response response = client.newCall(request).execute();

                    if (response.isSuccessful()) {
                        String responseBody = response.body().string();
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                editor.putInt("Status", 1);
                                handleSuccessfulRegisterResponse(responseBody);
                            }
                        });
                    } else {
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                editor.putInt("Status", 0);
                                try{

                                    String responseBody = response.body().string();
                                    handleerrorRegisterResponse(responseBody);
                                }catch (Exception ex){
                                    callback.onFailureCheck("error code "+response.code()+"\n " + response.body());
                                }                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            editor.putInt("Status", 2);
                            callback.onFailureRegister(e.getMessage());
                        }
                    });
                }
            }
        });
    }
    private void showGPSAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled. Do you want to enable it?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false); // Disable clicks outside the dialog
        alertDialog.show();
    }
    private void getLocationAndDisplay() {
        Location location = gpsTracker.getLocation();

        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
        } else {
        }
    }
    private DeviceInfo gatherDeviceInfoAndSendRequest(String nni,String phone) {
        // Create a DeviceInfo object
        DeviceInfo deviceInfo = new DeviceInfo();

        // Fallback if Build.SERIAL is null or empty
        String serialNumber = Build.SERIAL;
        if (serialNumber == null || serialNumber.isEmpty() || serialNumber.equals("unknown") ) {
            serialNumber = generateStaticSerial();
        }

        // Set device information
        deviceInfo.setDeviceSn(serialNumber);
        deviceInfo.setNni(nni); // Replace this with the actual logic to get NNI if needed
        deviceInfo.setProfil("security"); // Replace this with the actual logic to get Profile if needed

        // Set location information (assuming loca is available)
        if (loca != null && loca.getCoordinates() != null) {
            DeviceInfo.Location location = new DeviceInfo.Location();
            location.setType("Point");
            DeviceInfo.Coordinates coordinates = new DeviceInfo.Coordinates();
            coordinates.setCoordinates(new double[]{ loca.getCoordinates().getLng(), loca.getCoordinates().getLnt() });
            location.setCoordinates(new double[]{ loca.getCoordinates().getLng(), loca.getCoordinates().getLnt() });
            deviceInfo.setLocation(location);
        }

        // Set other device details
        deviceInfo.setDeviceBrand(Build.BRAND);
        deviceInfo.setDeviceManufacturer(Build.MANUFACTURER);
        deviceInfo.setDeviceModel(Build.MODEL);
        deviceInfo.setDeviceName(Build.DEVICE);
        deviceInfo.setDeviceSystemName("Android");
        deviceInfo.setStructure(selectedItem);
        deviceInfo.setPhoneNumber(phone);
        deviceInfo.setAppName(APPNAME);
        deviceInfo.setDeviceSystemVersion(Build.VERSION.RELEASE);
        deviceInfo.setDeviceCarrier(getCarrierName());

        return deviceInfo;
    }

    private String generateStaticSerial() {
        // Generate a static identifier based on device attributes
        String serialBase = Build.BOARD + Build.BRAND + Build.DEVICE + Build.DISPLAY + Build.HARDWARE + Build.ID +
                Build.MANUFACTURER + Build.MODEL + Build.PRODUCT + Build.TAGS + Build.TYPE + Build.USER;

        // Use SHA-256 hash to ensure uniqueness and consistent length
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
            return null; // Handle error or fallback accordingly
        }
    }
    private String getCarrierName() {
        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        return manager != null ? manager.getNetworkOperatorName() : "Unknown";
    }
    private void check(DeviceInfo deviceInfo) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = OkHttpClientInstance.getOkHttpClient();
                    JSONObject jsonBody = createRequestBody(deviceInfo);

                    MediaType mediaType = MediaType.parse("application/json");
                    RequestBody requestBody = RequestBody.create(mediaType, jsonBody.toString());

                    Request request = new Request.Builder()
                            .url("https://api-houwiyeti.anrpts.gov.mr/houwiyetiapi/v1/partners/checkAnrptsIdStatus2")
                            .method("POST", requestBody)
                            .addHeader("entity-Api-Key", "a96e90c5-d561-4a1d-8307-a22b8999cc9f")
                            .addHeader("Content-Type", "application/json")
                            .addHeader("Accept", "application/json")
                            .addHeader("app-name", "deyar_gps")
                            .addHeader("app-version", Integer.toString(getCurrentVersionCode()))
                            .build();

                    Response response = client.newCall(request).execute();

                    if (response.isSuccessful()) {
                        String responseBody = response.body().string();
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                editor.putInt("Status", 2);
                                handleSuccessfulCheckResponse(responseBody,deviceInfo);
                            }
                        });
                    } else {
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    String responseBody = response.body().string();
                                    handleerrorCheckResponse(responseBody,deviceInfo);
                                }catch (Exception ex){
                                    callback.onFailureCheck("error code "+response.code()+"\n " + response.body());
                                }
                            }
                        });
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    mainHandler.post(() -> callback.onFailureCheck(e.getMessage()));
                }
            }
        });
    }

    private JSONObject createRequestBody(DeviceInfo deviceInfo) throws JSONException {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("deviceSn", deviceInfo.getDeviceSn());
        jsonBody.put("nni", deviceInfo.getNni());
        jsonBody.put("appName", deviceInfo.getAppName());

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
        try {
            JSONObject responseObject = new JSONObject(responseBody);
            String type = responseObject.getString("type");
            int ccd = responseObject.getInt("code");
            if (type.equals("successCode") && ccd == 1) {

                String profile = responseObject.getString("profil");
                String appid = responseObject.getString("id");
                editor.putString("appid",appid);
                editor.putString("profile",profile);
                editor.commit();
                homeActivity();
            } else if (type.equals("errorCode") && ccd == 1) {
                waitingActivity();
            } else if (type.equals("errorCode") && ccd == 6) {
                newRegister(deviceInfo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void handleerrorCheckResponse(String responseBody, DeviceInfo deviceInfo) {
        try {
            JSONObject responseObject = new JSONObject(responseBody);
            String type = responseObject.getString("type");
            int ccd = responseObject.getInt("code");
            if (type.equals("errorCode") && ccd == 1) {
                editor.putInt("Status", 1);
                waitingActivity();
            } else if (type.equals("errorCode") && ccd == 6) {
                editor.putInt("Status", 0);
                newRegister(deviceInfo);
            }
            else if (type.equals("errorCode") && ccd == 4) {
                editor.putInt("Status", 1);
                waitingActivity();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void handleSuccessfulRegisterResponse(String responseBody) {
        try {

            if (responseBody!=null) {
                editor.putString("appid", responseBody);
                waitingActivity();
            }
        } catch (Exception e) {
            cancel();
            e.printStackTrace();
        }
    }
    private void handleerrorRegisterResponse(String responseBody) {
        try {
            JSONObject responseObject = new JSONObject(responseBody);
            String type = responseObject.getString("type");
            int ccd = responseObject.getInt("code");
            if (type.equals("errorCode") && ccd == 6) {
                editor.putInt("Status", 1);
                waitingActivity();
            }
        } catch (JSONException e) {
            cancel();
            e.printStackTrace();
        }
    }
    private void updateUIAndStartActivity(Class<?> activityClass) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                cirLoginButton.setEnabled(true);
                cirLoginButton.setText("CONNEXION");

                Intent intent = new Intent(LoginActivity.this, activityClass);
                startActivity(intent);
            }
        });
    }

    private void homeActivity() {
        updateUIAndStartActivity(HomeActivity.class);
    }

    private void waitingActivity() {
        updateUIAndStartActivity(WaitingActivity.class);
    }
    @Override
    public void onSuccessRegister(int code, String response) {
        waitingActivity();
    }

    @Override
    public void onSuccessCheck(int code, String response) {
        homeActivity();
    }

    @Override
    public void onFailureCheck(String message) {
        cancel();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailureRegister(String message) {
        cancel();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void newRegister(DeviceInfo deviceInfo) {
        deviceInfo.setAppName(APPNAME);
        Gson gson = new Gson();
        String json = gson.toJson(deviceInfo);
        register(json);
    }
    private void cancel(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                cirLoginButton.setEnabled(true);
                cirLoginButton.setText("CONNEXION");
            }
        });
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
}