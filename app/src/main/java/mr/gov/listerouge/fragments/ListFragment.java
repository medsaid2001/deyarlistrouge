package mr.gov.listerouge.fragments;

import static mr.gov.listerouge.Constant.APPNAME;
import static mr.gov.listerouge.Constant.ENTITY_KEY;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import mr.gov.listerouge.GPSTracker;
import mr.gov.listerouge.R;
import mr.gov.listerouge.adapter.ListRougeAdapter;
import mr.gov.listerouge.database.RedListItemDao;
import mr.gov.listerouge.api.RetrofitClientInstance;
import mr.gov.listerouge.database.AppDatabase;
import mr.gov.listerouge.interfaces.PersonInterface;
import mr.gov.listerouge.models.DeviceInfo;
import mr.gov.listerouge.models.RedListItem;
import mr.gov.listerouge.models.RedlistRequest;
import mr.gov.listerouge.network.NetworkService;
import mr.gov.listerouge.tools.NetworkUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFragment extends Fragment implements PersonInterface {

    private RecyclerView recyclerView;
    private ListRougeAdapter adapter;
    private List<RedListItem> itemList;
    private AppDatabase appDatabase;
    private RedListItemDao redListItemDao;
    private SharedPreferences sharedPreferences;
    private String nni;
    private SearchView searchView;
    private String  serianl, appid, appname, appversion;
    mr.gov.listerouge.models.DeviceInfo.Location loca;
    private int currentPage = 1;
    private int totalPages = 1;
    private boolean isLoading = false;
    private GPSTracker gpsTracker;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.listrougerecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        searchView = view.findViewById(R.id.searchView);
        gpsTracker = new GPSTracker(getContext());

        itemList = new ArrayList<>();
        adapter = new ListRougeAdapter(getContext(), itemList, this);
        recyclerView.setAdapter(adapter);

        // Initialize the database and DAO
        appDatabase = AppDatabase.getInstance(requireContext());
        redListItemDao = appDatabase.redListItemDao();

        sharedPreferences = requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        nni = sharedPreferences.getString("nni", null);
        redListItemDao.getAllItems().observe(getViewLifecycleOwner(), items -> {
            if (items != null && !items.isEmpty()) {
                adapter.setItems(items);
            } else {
                Toast.makeText(requireContext(), "No data available", Toast.LENGTH_SHORT).show();
            }
        });
        loca = new DeviceInfo.Location();
        handleGPS();
      // loadDataFromDatabase();
        sharedPreferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        nni = sharedPreferences.getString("nni", null);
        appid = sharedPreferences.getString("appid", null);
        serianl = sharedPreferences.getString("serial", null);
        appname = APPNAME;
        appversion = String.valueOf(getCurrentVersionCode());
        setupSearchView();
    }


    private void setupSearchView() {

    }

    @Override
    public void deleteone(int position, String id) {
        // Delete the item from the database
        new Thread(() -> {
            redListItemDao.deleteItemById(id);

            requireActivity().runOnUiThread(() -> {
                adapter.deleteItem(position);
                DeletePerson(appname,appversion,appid,id,position);
            });
        }).start();
    }
    private void DeletePerson(String appname, String appversion, String appId, String id,int position) {
        loca.setType("Point");
        RedlistRequest list = new RedlistRequest(serianl, loca);

        NetworkService service = RetrofitClientInstance.getRetrofitInstance().create(NetworkService.class);
        Call<Void> call = service.supprimerLr(list, ENTITY_KEY, appname, appversion, nni, appId, id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> OneResponse, Response<Void> response) {
                 if (response.isSuccessful()) {
                    showSuccess("Cette personne a été retirée de la liste rouge");
                } else {
                    handleErrorOneResponse(response); // Handle error response
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                //dismissProgressDialog(); // Dismiss ProgressDialog on failure
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                showAlert("Une erreur interne s'est produite. Veuillez contacter l'administrateur");
                t.printStackTrace();
            }
        });
    }
    private void handleErrorOneResponse(Response<Void> response) {
        JSONObject jsonObject = null;
        int code = response.code();
        if (code == 403) {
            showAlert("Vous n'avez pas accès pour effectuer cette demande");
        } else {
            try {
                String errorBody = response.errorBody().string();
                jsonObject = new JSONObject(errorBody);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (jsonObject != null) {
                String errorMessage = null;
                try {
                    errorMessage = jsonObject.getString("message");
                    showAlert(errorMessage);
                } catch (JSONException e) {
                    e.printStackTrace();
                    showAlert("Une erreur interne est survenue");
                }
            }
        }
    }
    private void showAlert(String text){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.custom_alert, null);
        // Get references to UI elements
        TextView tvMessage = dialogView.findViewById(R.id.idmessagew);
        tvMessage.setText(text);
        MaterialButton btnDone = dialogView.findViewById(R.id.btn_done);

        // Build and show the BottomSheetDialog
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext(), R.style.BottomSheetDialogTheme);
        builder.setView(dialogView);
        android.app.AlertDialog dialog = builder.create();
        // Set click listener for the button
        btnDone.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
    private void showSuccess(String text){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.custom_success_dialog, null);
        // Get references to UI elements
        TextView tvMessage = dialogView.findViewById(R.id.id_success_messagew);
        tvMessage.setText(text);
        MaterialButton btnDone = dialogView.findViewById(R.id.btn_success_done);

        // Build and show the BottomSheetDialog
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext(), R.style.BottomSheetDialogTheme);
        builder.setView(dialogView);
        android.app.AlertDialog dialog = builder.create();
        // Set click listener for the button
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                DashboardFragment detailsFragment = new DashboardFragment();

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, detailsFragment)
                        .commit();
            }
        });
        dialog.show();
    }
    private void handleGPS() {
        if (!gpsTracker.isGPSEnabled()) {
            showGPSAlertDialog();
        } else {
            Location location = gpsTracker.getLocation();
            if (location != null) {
                loca.setCoordinates(new double[]{location.getLongitude(),location.getLatitude()});
                checkInternetAndProceed();
            } else {
                showGPSAlertDialog();
            }
        }
    }
    private void showGPSAlertDialog() {
        new AlertDialog.Builder(getContext())
                .setMessage("GPS is disabled. Do you want to enable it?")
                .setPositiveButton("Yes", (dialog, id) -> startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 200))
                .setNegativeButton("No", (dialog, id) -> dialog.cancel())
                .setCancelable(false)
                .show();
    }

    private void checkInternetAndProceed() {
        if (NetworkUtil.isInternetAvailable(getContext())) {
            // Proceed with network operations
        } else {
            showNoInternetDialog();
        }
    }

    private void showNoInternetDialog() {
        new AlertDialog.Builder(getContext())
                .setMessage("No internet connection. Please check your connection and try again.")
                .setPositiveButton("Retry", (dialog, which) -> checkInternetAndProceed())
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .setCancelable(false)
                .show();
    }
    private int getCurrentVersionCode() {
        try {
            PackageInfo packageInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

}
