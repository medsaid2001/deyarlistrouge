package mr.gov.listerouge.fragments;

import static android.content.Context.MODE_PRIVATE;

import static mr.gov.listerouge.Constant.APPNAME;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import mr.gov.listerouge.GPSTracker;
import mr.gov.listerouge.R;
import mr.gov.listerouge.activity.LoginActivity;
import mr.gov.listerouge.api.OkHttpClientInstance;
import mr.gov.listerouge.interfaces.FinishCallback;
import mr.gov.listerouge.models.Coordinates;
import mr.gov.listerouge.tools.NetworkUtil;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoadingFragment extends Fragment implements FinishCallback {

    private FinishCallback callback;
    private LottieAnimationView animationView;
    private View viewContainer;
    GPSTracker gpsTracker;
    private ImageView imageView;
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private Context context;
    String appname ;
    String appid = null;
    int appcode;
    mr.gov.listerouge.models.Location loca;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewContainer = inflater.inflate(R.layout.fragment_loading, container, false);
        return viewContainer;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        animationView = viewContainer.findViewById(R.id.animationView);
        imageView = viewContainer.findViewById(R.id.imagetemp);
        callback = this;
        context = getContext();
        loca = new mr.gov.listerouge.models.Location();
        gpsTracker = new GPSTracker(context);
        Coordinates coordinates = new Coordinates();
        appname = getCurrentVersionName();
        appcode = getCurrentVersionCode();

        if (sharedPreferences == null) {
            sharedPreferences = getActivity().getSharedPreferences("data", MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
        appid = sharedPreferences.getString("appid",null);
        if (!gpsTracker.isGPSEnabled()) {
            showGPSAlertDialog();
        } else {
            Location location = gpsTracker.getLocation();
            if (location != null) {
                coordinates.setLng(location.getLatitude());
                coordinates.setLnt(location.getLongitude());
                loca.setCoordinates(coordinates);
            }
            if (getArguments() != null) {
                String sn = sharedPreferences.getString("serial", null);
                String nni = getArguments().getString("nni");

                String responseString = getArguments().getString("base64Image");
                int type = getArguments().getInt("type");
                if (type == 1 || type == 2) {
                    displayBase64Image(responseString);
                }
                if (type == 1) {

                    checkOnStart(responseString,1);
                } else if (type == 2) {
                    checkOnStart(responseString,2);
                } else if (type == 3) {
                    checkOnStart(null,3);
                    if (sn != null) {
                        getpersonne(nni, sn);
                    } else {
                        callback.onError("No Serial Number Found");
                    }
                } else if (type == 4) {
                    authenticate(responseString);
                }
                else if (type == 5) {
                    getnudinfo(nni,sn);
                }
            }
        }

    }

    private void showGPSAlertDialog() {
        androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(context);
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

        androidx.appcompat.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false); // Disable clicks outside the dialog
        alertDialog.show();
    }

    private void displayBase64Image(String base64Image) {
        if (base64Image != null) {
            try {
                byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
                if (decodedString != null) {
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    imageView.setVisibility(View.VISIBLE);
                    imageView.setImageBitmap(decodedByte);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void identify(String base64Image) {
        new Thread(() -> {
            try {
                OkHttpClient client = OkHttpClientInstance.getOkHttpClient();
                String ss = base64Image;
                JSONObject jsonBody = new JSONObject();
                JSONObject photoObject = new JSONObject();
                photoObject.put("data", ss);
                photoObject.put("format", "JPEG");
                jsonBody.put("photo", photoObject);
                jsonBody.put("photoSource","CAPTURE");
                jsonBody.put("appId",appid);
                JSONArray coordinates = new JSONArray();
                double[] cdn = {loca.getCoordinates().getLnt(), loca.getCoordinates().getLng()};

                coordinates.put(cdn[0]);
                coordinates.put(cdn[1]);
                JSONObject locationObject = new JSONObject();
                locationObject.put("type", "Point");
                locationObject.put("coordinates", coordinates);
                jsonBody.put("location", locationObject);
                String js =  jsonBody.toString();
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody requestb = RequestBody.create(mediaType, jsonBody.toString());

                Request request = new Request.Builder()
                        .url("https://api-houwiyeti.anrpts.gov.mr/houwiyetiapi/v1/partners/identify2")
                        .method("POST", requestb)
                        .addHeader("entity-Api-Key", "a96e90c5-d561-4a1d-8307-a22b8999cc9f")
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Accept", "application/json")
                        .addHeader("app-name", "deyar_redlist")
                        .addHeader("app-version", Integer.toString(appcode))
                        .build();

                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    ResultFragment detailsFragment = new ResultFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("responseString", responseBody);
                    detailsFragment.setArguments(bundle);
                    if (isAdded()) {
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame_layout, detailsFragment)
                                .commit();
                    }
                } else {
                    callback.onError(String.valueOf(response.code()));
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                callback.onError(e.getMessage());
            }
        }).start();
    }

    private void identify2(String json) {
        final String ss = json;
        new Thread(() -> {
            try {
                OkHttpClient client = OkHttpClientInstance.getOkHttpClient();
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), ss);
                JSONObject jsonBody = new JSONObject(json);
                jsonBody.put("photoSource","UPLOAD");
                jsonBody.put("appId",appid);
                JSONArray coordinates = new JSONArray();
                double[] cdn = {loca.getCoordinates().getLnt(), loca.getCoordinates().getLng()};

                coordinates.put(cdn[0]);
                coordinates.put(cdn[1]);
                JSONObject locationObject = new JSONObject();
                locationObject.put("type", "Point");
                locationObject.put("coordinates", coordinates);
                jsonBody.put("location", locationObject);
                String js =  jsonBody.toString();
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody requestb = RequestBody.create(mediaType, jsonBody.toString());

                Request request = new Request.Builder()
                        .url("https://api-houwiyeti.anrpts.gov.mr/houwiyetiapi/v1/partners/identify2")
                        .method("POST", requestb)
                        .addHeader("entity-Api-Key", "a96e90c5-d561-4a1d-8307-a22b8999cc9f")
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Accept", "application/json")
                        .addHeader("app-name", "deyar_redlist")
                        .addHeader("app-version", Integer.toString(appcode))
                        .build();


                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    callback.onSuccess();
                    String responseBody = response.body().string();
                    ResultFragment detailsFragment = new ResultFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("responseString", responseBody);
                    detailsFragment.setArguments(bundle);
                    if (isAdded()) {
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame_layout, detailsFragment)
                                .commit();
                    }
                } else {
                    callback.onError(String.valueOf(response.code()));
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                callback.onError(e.getMessage());
            }
        }).start();
    }
    private void getnudinfo(String nud,String serial) {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient.Builder().build();
                String url = "https://api-houwiyeti.anrpts.gov.mr/houwiyetiapi/v1/partners/getNudInfos" +
                        "?nud=" + nud ;

                JSONObject jsonBody = new JSONObject();
                jsonBody.put("deviceSn",serial);
                JSONArray coordinates = new JSONArray();
                double[] cdn = {loca.getCoordinates().getLnt(), loca.getCoordinates().getLng()};

                coordinates.put(cdn[0]);
                coordinates.put(cdn[1]);
                JSONObject locationObject = new JSONObject();
                locationObject.put("type", "Point");
                locationObject.put("coordinates", coordinates);
                jsonBody.put("position", locationObject);
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody requestb = RequestBody.create(mediaType, jsonBody.toString());
                Request request = new Request.Builder()
                        .url(url)
                        .method("POST", requestb)
                        .addHeader("entity-Api-Key", "a96e90c5-d561-4a1d-8307-a22b8999cc9f")
                        .addHeader("Accept", "application/json")
                        .addHeader("app-name", APPNAME)
                        .addHeader("app-version", Integer.toString(appcode))
                        .build();
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    NudInfoFragment nudInfoFragment = new NudInfoFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("responseString", responseBody);
                    nudInfoFragment.setArguments(bundle);

                    if (isAdded()) {
                        requireActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame_layout, nudInfoFragment)
                                .commit();
                    }
                } else {
                    callback.onError(String.valueOf(response.code()));
                }
            } catch (IOException e) {
                e.printStackTrace();
                callback.onError(e.getMessage());
            } catch (JSONException e) {
                callback.onError(e.getMessage());
            }
        }).start();
    }


    private void authenticate(String json) {
        final String ss = json;
        new Thread(() -> {
            try {
                OkHttpClient client = OkHttpClientInstance.getOkHttpClient();
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), ss);

                Request request = new Request.Builder()
                        .url("https://api-houwiyeti.anrpts.gov.mr/houwiyetiapi/v1/partners/identify2")
                        .method("POST", requestBody)
                        .addHeader("entity-Api-Key", "a96e90c5-d561-4a1d-8307-a22b8999cc9f")
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Accept", "application/json")
                        .addHeader("app-name", "deyar_redlist")
                        .addHeader("app-version", Integer.toString(appcode))
                        .build();

                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    if (responseBody.contains("false")) {
                        callback.onError("NON AUTHENTIFIÉ");
                    } else {
                        callback.onSuccess();
                    }
                } else {
                    callback.onError(String.valueOf(response.code()));
                }
            } catch (IOException e) {
                e.printStackTrace();
                callback.onError(e.getMessage());
            }
        }).start();
    }

    private void getpersonne(String nni, String serial) {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient.Builder().build();
                String url = "https://api-houwiyeti.anrpts.gov.mr/houwiyetiapi/v1/partners/getPersonSecDetails2" +
                        "?nni=" + nni ;

                JSONObject jsonBody = new JSONObject();
                jsonBody.put("deviceSn",serial);
                JSONArray coordinates = new JSONArray();
                double[] cdn = {loca.getCoordinates().getLnt(), loca.getCoordinates().getLng()};

                coordinates.put(cdn[0]);
                coordinates.put(cdn[1]);
                JSONObject locationObject = new JSONObject();
                locationObject.put("type", "Point");
                locationObject.put("coordinates", coordinates);
                jsonBody.put("position", locationObject);
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody requestb = RequestBody.create(mediaType, jsonBody.toString());
                Request request = new Request.Builder()
                        .url(url)
                        .method("POST", requestb)
                        .addHeader("entity-Api-Key", "a96e90c5-d561-4a1d-8307-a22b8999cc9f")
                        .addHeader("Accept", "application/json")
                        .addHeader("app-name", APPNAME)
                        .addHeader("app-version", Integer.toString(appcode))
                        .build();
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    DetailsFragment detailsFragment = new DetailsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("responseString", responseBody);
                    detailsFragment.setArguments(bundle);

                    if (isAdded()) {
                        requireActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame_layout, detailsFragment)
                                .commit();
                    }
                } else {
                    callback.onError(String.valueOf(response.code()));
                }
            } catch (IOException e) {
                e.printStackTrace();
                callback.onError(e.getMessage());
            } catch (JSONException e) {
                callback.onError(e.getMessage());
            }
        }).start();
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError(String message) {
        String mess;
        if (message.contains("404")) {
            mess = getResources().getString(R.string.error404);
        } else if (message.contains("500")) {
            mess = getResources().getString(R.string.error500);
        } else {
            mess = message;
        }

        if (isAdded()) {
            requireActivity().runOnUiThread(() -> {
                animationView.cancelAnimation();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View dialogView = inflater.inflate(R.layout.custom_alert, null);

                // Get references to UI elements
                TextView tvMessage = dialogView.findViewById(R.id.idmessagew);
                tvMessage.setText(mess);
                MaterialButton btnDone = dialogView.findViewById(R.id.btn_done);

                // Build and show the BottomSheetDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.BottomSheetDialogTheme);
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();
                // Set click listener for the button
                btnDone.setOnClickListener(v -> dialog.dismiss());
                dialog.show();
            });
        }
    }


    private void checkInternetAndProceed() {
        if (NetworkUtil.isInternetAvailable(context)) {
            // checkOnStart();
        } else {
            showNoInternetDialog();
        }
    }
    private void checkdevice(String json, int type,String bb) {
        final String ss = json;
        new Thread(() -> {
            try {
                OkHttpClient client = OkHttpClientInstance.getOkHttpClient();
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), ss);

                Request request = new Request.Builder()
                        .url("https://api-houwiyeti.anrpts.gov.mr/houwiyetiapi/v1/partners/checkAnrptsIDstatus2")
                        .method("POST", requestBody)
                        .addHeader("entity-Api-Key", "a96e90c5-d561-4a1d-8307-a22b8999cc9f")
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Accept", "application/json")
                        .addHeader("app-name", "deyar_redlist")
                        .addHeader("app-version", Integer.toString(appcode))
                        .build();

                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    String appid2 = response.body().string();
                    if(isAdded()){

                        JSONObject responseObject = new JSONObject(appid2);
                        String profile = responseObject.getString("profil");
                        String appid22 = responseObject.getString("id");
                        editor.putString("appid",appid22);
                        editor.putString("profile",profile);
                        editor.commit();
                        if(type==1){
                            identify(bb);
                        }
                        else if(type==2){
                            identify2(bb);
                        }

                    }


                } else {
                    callback.onError(String.valueOf(response.code()));
                }
            } catch (IOException e) {
                e.printStackTrace();
                callback.onError(e.getMessage());
            } catch (JSONException e) {
                callback.onError(e.getMessage());
            }
        }).start();
    }
    private void checkOnStart(String json2, int type) {
        String nni = sharedPreferences.getString("nni", null);
        String serialNumber = sharedPreferences.getString("serial", null);
        if (nni != null && serialNumber != null) {
            try {
                String json = createRequestBody(nni,serialNumber).toString();
                checkdevice(json,type,json2);
            }catch (Exception ex){
                callback.onError(ex.getMessage());
            }
        } else {
            callback.onError("nni non trouver");
        }
    }

    private void showNoInternetDialog() {
        new androidx.appcompat.app.AlertDialog.Builder(context)
                .setMessage("No internet connection. Please check your connection and try again.")
                .setPositiveButton("Retry", (dialog, which) -> checkInternetAndProceed())
                .setNegativeButton("Cancel", (dialog, which) -> navigateToActivity(LoginActivity.class))
                .setCancelable(false)
                .show();
    }
    private void navigateToActivity(Class<?> activityClass) {
        if (isAdded()) {
            requireActivity().runOnUiThread(() -> {
                Intent intent = new Intent(context, activityClass);
                startActivity(intent);
            });
        }
    }
    private JSONObject createRequestBody(String nni,String devicesn) throws JSONException {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("deviceSn", devicesn);
        jsonBody.put("nni", nni);
        jsonBody.put("appName", APPNAME);

        JSONObject locationObject = new JSONObject();
        locationObject.put("type", "Point");

        JSONArray coordinates = new JSONArray();
        double[] cdn = {loca.getCoordinates().getLnt(), loca.getCoordinates().getLng()};

        coordinates.put(cdn[0]);
        coordinates.put(cdn[1]);

        locationObject.put("coordinates", coordinates);
        jsonBody.put("location", locationObject);

        return jsonBody;
    }
    private String getCurrentVersionName() {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private int getCurrentVersionCode() {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }
}