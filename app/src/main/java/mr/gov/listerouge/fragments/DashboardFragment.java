package mr.gov.listerouge.fragments;

import static android.content.Context.MODE_PRIVATE;
import static mr.gov.listerouge.Constant.APPNAME;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import mr.gov.listerouge.GPSTracker;
import mr.gov.listerouge.R;
import mr.gov.listerouge.api.RetrofitClientInstance;
import mr.gov.listerouge.databinding.FragmentDashboardBinding;
import mr.gov.listerouge.models.Coordinates;
import mr.gov.listerouge.network.NetworkService;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;

public class DashboardFragment extends Fragment {

    private Context context;
    private boolean hasFetchedPersonneData = false;

    private boolean isHidden = false;
    private SharedPreferences sharedPreferences;
      private FragmentDashboardBinding dashboard;
      private static final String PREF_NNI = "nni";
    private static final String PREF_PERSONNE_DATA = "personne_data";
    GPSTracker gpsTracker;
    mr.gov.listerouge.models.Location loca;
    int appcode;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retain the fragment instance across configuration changes
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dashboard = FragmentDashboardBinding.inflate(inflater, container, false);
        return dashboard.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState == null) {
            // This block will only be executed the first time the fragment is created, not during recreation
            if (sharedPreferences == null) {
                assert getActivity() != null;
                sharedPreferences = getActivity().getSharedPreferences("data", MODE_PRIVATE);
            }
            context = getContext();
            setupListeners();

            appcode = getCurrentVersionCode();
            String sn = sharedPreferences.getString("serial", null);
            String nni = sharedPreferences.getString(PREF_NNI, null);
            loca = new mr.gov.listerouge.models.Location();
            gpsTracker = new GPSTracker(context);
            Coordinates coordinates = new Coordinates();

            if (nni != null) {
                String personneData = sharedPreferences.getString(PREF_PERSONNE_DATA, null);
                if (personneData != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(personneData);
                        populateData(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (!gpsTracker.isGPSEnabled()) {
                        showGPSAlertDialog();
                    } else {
                        Location location = gpsTracker.getLocation();
                        if (location != null && !hasFetchedPersonneData) {
                            hasFetchedPersonneData = true; // Set flag to true once the data is fetched
                            coordinates.setLng(location.getLatitude());
                            coordinates.setLnt(location.getLongitude());
                            loca.setCoordinates(coordinates);
                            getpersonne(nni, sn);
                        }
                    }
                }
            }
        }
    }


    private void setupListeners() {
        dashboard.camerraid.setOnClickListener(v -> replaceFragment(new IdentificationFragment()));
       dashboard.piictureid.setOnClickListener(v -> replaceFragment(new AuthentificationFragment()));
        dashboard.viewhide.setOnClickListener(v -> toggleTextVisibility());
        dashboard.lisstrouge.setOnClickListener(v -> replaceFragment(new ListRougeDashFragment()));
        dashboard.cartegrise.setOnClickListener(v -> replaceFragment(new SearchCarFragment()));
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
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
                    JSONObject jsonObjectResponse = new JSONObject(responseBody);


                    if (isAdded()) {
                        savePersonneData(jsonObjectResponse);
                        populateData(jsonObjectResponse);
                    }
                } else {
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
            }
        }).start();
    }
    private void savePersonneData(JSONObject jsonObject) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_PERSONNE_DATA, jsonObject.toString());
        editor.apply();
    }
    private void populateData(JSONObject jsonObject) {
        if (isAdded()) {
            requireActivity().runOnUiThread(() -> {
                try {
                    JSONObject personne = jsonObject.getJSONObject("personne");
                    dashboard.animationView.setVisibility(View.GONE);
                    dashboard.viewcard.setVisibility(View.VISIBLE);
                    dashboard.viewhide.setVisibility(View.VISIBLE);
                    dashboard.conlayout.setBackgroundResource(R.drawable.button_gradient);
                    dashboard.nni.setText(personne.getString("nni"));
                    String fullname = personne.getString("prenomFr") + " " + personne.getString("patronymeFr");
                    dashboard.fullname.setText(fullname);
                    dashboard.placeofbirth.setText(personne.getString("lieuNaissanceFr"));
                    setFormattedDate(personne.getString("dateNaissance"));
                    setTextStyles();
                    setImageFromBase64(jsonObject.getString("photo"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void setFormattedDate(String dateString) {
        SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            Date date = datetimeFormat.parse(dateString);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(date);
            dashboard.dateofbirth.setText(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void setTextStyles() {
        int textColor = Color.BLACK;
        dashboard.fullname.setTextColor(textColor);
        dashboard.nni.setTextColor(textColor);
        dashboard.placeofbirth.setTextColor(textColor);
        dashboard.dateofbirth.setTextColor(textColor);

        Typeface customFont = ResourcesCompat.getFont(getContext(), R.font.montserrat_bold);
        dashboard.fullname.setTypeface(customFont);
        dashboard.nni.setTypeface(customFont);
        dashboard.placeofbirth.setTypeface(customFont);
        dashboard.dateofbirth.setTypeface(customFont);
    }

    private void setImageFromBase64(String base64Image) {
        Bitmap bitmap = decodeBase64(base64Image);
        dashboard.photoid.setImageBitmap(bitmap);
    }

    private void toggleTextVisibility() {
        if (isHidden) {
            dashboard.nni.setTextColor(Color.BLACK);
            dashboard.nni.setText(sharedPreferences.getString(PREF_NNI, ""));
        } else {
            dashboard.nni.setText("**********");
        }
        isHidden = !isHidden;
    }

    private Bitmap decodeBase64(String base64Str) {
        byte[] decodedBytes = Base64.decode(base64Str, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
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

}
