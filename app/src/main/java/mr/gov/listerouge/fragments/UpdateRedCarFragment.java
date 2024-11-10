package mr.gov.listerouge.fragments;

import static mr.gov.listerouge.Constant.APPNAME;
import static mr.gov.listerouge.Constant.ENTITY_KEY;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.location.Location;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import mr.gov.listerouge.GPSTracker;
import mr.gov.listerouge.R;
import mr.gov.listerouge.api.RetrofitClientInstance;
import mr.gov.listerouge.models.CreateList;
import mr.gov.listerouge.models.OneResponse;
import mr.gov.listerouge.models.RedlistRequest;
import mr.gov.listerouge.network.NetworkService;
import mr.gov.listerouge.tools.NetworkUtil;
import mr.gov.listerouge.tools.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateRedCarFragment extends Fragment {

    private static final int PICK_IMAGE = 1;
    private String selectedItem = null;
    private String nnitxt, serianl, appid, appname, appversion;
    private mr.gov.listerouge.models.DeviceInfo.Location loca;
    private EditText editTextDescription;
    private EditText editTextConduitContact;
    private EditText matricule;
    private GPSTracker gpsTracker;
    private ImageView imageViewPieceJustif;
    private FloatingActionButton buttonAdd;
    private AppCompatButton personnnejustficatifselect;
    private String selectedImageBitmap;
    private static final int REQUEST_LOAD = 200;
    private SharedPreferences sharedPreferences;
    private String personneid;
    private ProgressDialog progressDialog; // ProgressDialog declaration

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_update, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextDescription = view.findViewById(R.id.personneDescription_update);
        editTextConduitContact = view.findViewById(R.id.personneConduitContact_update);
        matricule = view.findViewById(R.id.personnenni_update);
        imageViewPieceJustif = view.findViewById(R.id.personnnejustficatif_update);
        personnnejustficatifselect = view.findViewById(R.id.personnnejustficatifselect_update);
        buttonAdd = view.findViewById(R.id.fab_add);
        gpsTracker = new GPSTracker(getContext());
        loca = new mr.gov.listerouge.models.DeviceInfo.Location();
        personnnejustficatifselect.setOnClickListener(v -> openGallery(REQUEST_LOAD));
        buttonAdd.setOnClickListener(v -> {
            String description = editTextDescription.getText().toString().trim();
            String conduitContact = editTextConduitContact.getText().toString().trim();
            String matriculetxt = matricule.getText().toString().trim();
            if (matriculetxt.isEmpty() && description.isEmpty() && conduitContact.isEmpty() && selectedImageBitmap == null) {
                showAlert("Please fill all fields and select an image");
                return;
            }
            if (loca != null) {
                if (nnitxt != null && !nnitxt.isEmpty() && serianl != null && !serianl.isEmpty()) {
                    showProgressDialog(); // Show ProgressDialog before network call
                    updateLr(ENTITY_KEY, APPNAME, appversion, serianl, appid, nnitxt,
                            "", description, conduitContact, selectedImageBitmap,"","","",matriculetxt);
                } else {
                    showAlert("NNI or Serial Number is missing");
                }
            } else {
                showGPSAlertDialog();
            }
        });

        sharedPreferences = getActivity().getSharedPreferences("data", Activity.MODE_PRIVATE);
        nnitxt = sharedPreferences.getString("nni", null);
        appid = sharedPreferences.getString("appid", null);
        serianl = sharedPreferences.getString("serial", null);
        appname = APPNAME;
        appversion = String.valueOf(getCurrentVersionCode());
        if (getArguments() != null) {
            String nni = getArguments().getString("nni");
            String mobile = getArguments().getString("idserial");
            personneid = getArguments().getString("idserial");
            matricule.setText(nni);
            editTextConduitContact.setText(mobile);
        }
        handleGPS();
    }

    private void openGallery(int code) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*"); // Allows all types of media
        startActivityForResult(Intent.createChooser(intent, "Select Media"), code);
    }

    private String convertBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); // adjust format and quality as needed
        byte[] imageBytes = baos.toByteArray();
        return android.util.Base64.encodeToString(imageBytes, android.util.Base64.DEFAULT);
    }

    private void updateLr(String entity, String appname, String appversion, String devicesn,
                          String appid, String nnioperator, String nni, String description,
                          String conduitContact, String pieceJustifBase64,String nud,
                          String requestid,String photo,String matricule) {
        if(pieceJustifBase64==null){
            pieceJustifBase64="";
        }
        CreateList list = new CreateList(loca,"",description,conduitContact,pieceJustifBase64,"","",matricule,"");
        NetworkService service = RetrofitClientInstance.getRetrofitInstance().create(NetworkService.class);
        Call<Void> call = service.updateLr(list,entity,appname,appversion,nnioperator,appid,devicesn,personneid);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                dismissProgressDialog(); // Dismiss ProgressDialog on response
                if (response.isSuccessful()) {
                    showSuccess("Cette personne a été ajoutée à la liste rouge");
                } else {
                    handleErrorResponse(response); // Handle error response
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                dismissProgressDialog(); // Dismiss ProgressDialog on failure
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                showAlert("Une erreur interne s'est produite. Veuillez contacter l'administrateur");
                t.printStackTrace();
            }
        });
    }

    /**
     * Method to fetch data for a specific entity based on the provided parameters.
     *
     * @param appname   Name of the application
     * @param appversion Version of the application
     * @param appId     ID of the application
     * @param id        Unique identifier for the entity
     */
    private void getOne(String appname, String appversion, String appId, String id) {
        RedlistRequest list = new RedlistRequest(serianl, loca);
        NetworkService service = RetrofitClientInstance.getRetrofitInstance().create(NetworkService.class);
        Call<OneResponse> call = service.getOneLr(list, ENTITY_KEY, appname, appversion, nnitxt, appId, id);
        call.enqueue(new Callback<OneResponse>() {
            @Override
            public void onResponse(Call<OneResponse> OneResponse, Response<OneResponse> response) {
                dismissProgressDialog(); // Dismiss ProgressDialog on response
                if (response.isSuccessful()) {
                    OneResponse oneresponse = response.body();
                    String description = oneresponse.getDescription();
                    String conduitContact = oneresponse.getConduitContact();
                    String nniText = oneresponse.getNni();
                    String pieceJustif = oneresponse.getPieceJustif();
                    populateFields(description, conduitContact, nniText, pieceJustif);
                } else {
                    handleErrorOneResponse(response); // Handle error response
                }
            }

            @Override
            public void onFailure(Call<OneResponse> call, Throwable t) {
                dismissProgressDialog(); // Dismiss ProgressDialog on failure
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                showAlert("Une erreur interne s'est produite. Veuillez contacter l'administrateur");
                t.printStackTrace();
            }
        });
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

    private void handleGPS() {
        if (!gpsTracker.isGPSEnabled()) {
            showGPSAlertDialog();
        } else {
            Location location = gpsTracker.getLocation();
            if (location != null) {
                loca.setCoordinates(new double[]{location.getLongitude(), location.getLatitude()});
                loca.setType("Point");
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
            if (getArguments() != null) {
                String id = getArguments().getString("idserial");
                getOne(appname, appversion, appid, id);
            }
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

    private void handleErrorResponse(Response<Void> response) {
        try {
            String errorBody = response.errorBody().string();
            JSONObject jsonObject = new JSONObject(errorBody);
            String errorMessage = jsonObject.getString("message");
            showAlert(errorMessage);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            showAlert("Une erreur s'est produite. Veuillez réessayer.");
        }
    }
    private void handleErrorOneResponse(Response<OneResponse> response) {
        try {
            String errorBody = response.errorBody().string();
            JSONObject jsonObject = new JSONObject(errorBody);
            String errorMessage = jsonObject.getString("message");
            showAlert(errorMessage);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            showAlert("Une erreur s'est produite. Veuillez réessayer.");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOAD && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri selectedImage = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    Bitmap rotatedBitmap = rotateBitmapIfRequired(bitmap, selectedImage);
                    imageViewPieceJustif.setImageBitmap(rotatedBitmap);
                    selectedImageBitmap = convertBitmapToBase64(rotatedBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    showAlert("Failed to load image.");
                }
            }
        }
    }

    private Bitmap rotateBitmapIfRequired(Bitmap bitmap, Uri selectedImage) throws IOException {
        InputStream input = getActivity().getContentResolver().openInputStream(selectedImage);
        ExifInterface ei;
        if (Build.VERSION.SDK_INT > 23) {
            ei = new ExifInterface(input);
        } else {
            ei = new ExifInterface(selectedImage.getPath());
        }
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(bitmap, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(bitmap, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(bitmap, 270);
            default:
                return bitmap;
        }
    }

    private Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    // ProgressDialog methods

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Chargement...");
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dismissProgressDialog(); // Dismiss ProgressDialog if fragment is destroyed
    }
    private void populateFields(String description, String conduitContact, String nniPersonne, String pieceJustifBase64) {
        editTextDescription.setText(description);
        editTextConduitContact.setText(conduitContact);
        matricule.setText(nniPersonne);
        selectedImageBitmap = pieceJustifBase64;
        if(pieceJustifBase64!=null){
            Bitmap decodedBitmap = Utils.decodeBase64(pieceJustifBase64); // Utilize your decoding method
            imageViewPieceJustif.setImageBitmap(decodedBitmap);
        }

    }
}