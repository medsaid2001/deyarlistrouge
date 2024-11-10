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
import android.widget.Spinner;
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
import mr.gov.listerouge.network.NetworkService;
import mr.gov.listerouge.tools.NetworkUtil;
import mr.gov.listerouge.tools.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddredcarFragment extends Fragment {

    private static final int PICK_IMAGE = 1;
    String selectedItem =null;
    private String nnitxt, serianl, appid, appname, appversion,matriculetxt;
    mr.gov.listerouge.models.DeviceInfo.Location loca;
    private EditText editTextDescription;
    private EditText editTextConduitContact;
    private EditText matricule;
    private GPSTracker gpsTracker;
    private ImageView imageViewPieceJustif;
    private AppCompatButton fab_add;
    private AppCompatButton personnnejustficatifselect;
    private String selectedImageBitmap;
    private static final int REQUEST_LOAD = 200;
    private SharedPreferences sharedPreferences;
    private Spinner spinner;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_addredcar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextDescription = view.findViewById(R.id.editTextDescription);
        editTextConduitContact = view.findViewById(R.id.editTextConduitContact);
        matricule = view.findViewById(R.id.nieditview);
        imageViewPieceJustif = view.findViewById(R.id.imageViewPieceJustif);
        personnnejustficatifselect = view.findViewById(R.id.buttonSelectImage);
        fab_add = view.findViewById(R.id.createredcar);
        gpsTracker = new GPSTracker(getContext());
        loca = new mr.gov.listerouge.models.DeviceInfo.Location();
        personnnejustficatifselect.setOnClickListener(v -> openGallery(REQUEST_LOAD));
        fab_add.setOnClickListener(v -> {
            String description = editTextDescription.getText().toString().trim();
            String conduitContact = editTextConduitContact.getText().toString().trim();
            String matriculetxt = matricule.getText().toString().trim();

            if (matriculetxt.isEmpty() &description.isEmpty() & conduitContact.isEmpty() & selectedImageBitmap == null) {
                Toast.makeText(getContext(), "Veuillez remplir tous les champs et sélectionner une image", Toast.LENGTH_SHORT).show();
                return;
            }
            //String entity,String appname,String appversion,String devicesn,
            //           String appid,String nnioperator
            if(loca!=null) {
                if (nnitxt != null && !nnitxt.isEmpty() && serianl != null && !serianl.isEmpty()) {
                    createLr(ENTITY_KEY, APPNAME,appversion,serianl,appid,nnitxt,
                            "", description, conduitContact, selectedImageBitmap,
                    "","",matriculetxt,"");
                } else {
                    // Handle the case where nni or serialNumber is null or empty
                    Toast.makeText(getContext(), "NNI or Serial Number is missing", Toast.LENGTH_SHORT).show();
                }
            }else{
                showGPSAlertDialog();
            }

        });
        if (getArguments() != null) {
            String nni2 = getArguments().getString("nni");
            String mobile = getArguments().getString("mobile");
            matricule.setText(nni2);
            editTextConduitContact.setText(mobile);
        }
        sharedPreferences = getActivity().getSharedPreferences("data", Activity.MODE_PRIVATE);
        nnitxt = sharedPreferences.getString("nni", null);
        appid = sharedPreferences.getString("appid", null);
        serianl = sharedPreferences.getString("serial", null);
        matriculetxt = sharedPreferences.getString("matricule", null);
        matricule.setText(matriculetxt);
        appname = APPNAME;
        appversion = String.valueOf(getCurrentVersionCode());
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


    private void createLr(String entity,String appname,String appversion,String devicesn,
                          String appid,String nnioperator
            ,String nni, String description, String conduitContact, String pieceJustifBase64,String nud,
                          String reqiestod,String matricule,String photo) {
        if(pieceJustifBase64==null){
            pieceJustifBase64="";
        }
        CreateList list = new CreateList(loca,nni,description,conduitContact,pieceJustifBase64,
                "","","","");
        NetworkService service = RetrofitClientInstance.getRetrofitInstance().create(NetworkService.class);
        Call<Void> call = service.createLr(list,entity,appname,appversion,nnioperator,appid,devicesn);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    showSuccess("Cette personne a été ajoutée à la liste rouge");
                } else {
                    try {
                        // Convert error body to string and show in the alert
                        String errorBody = response.errorBody().string();
                        JSONObject object = new JSONObject(errorBody);
                        String message = object.getString("message");
                        showAlert("Impossible d'ajouter cette personne à la liste rouge. " + message);
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                        showAlert("Impossible d'ajouter cette personne à la liste rouge. Une erreur est survenue.");
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
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
                loca.setCoordinates(new double[]{location.getLongitude(),location.getLatitude()});
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            if (selectedImage != null) {
                // Show progress dialog
                ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Processing image...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                new Thread(() -> {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                        bitmap = handleExifOrientation(bitmap, selectedImage);
                        if (requestCode == REQUEST_LOAD) {
                            Bitmap flippedBitmap = flipBitmap(bitmap);
                            Bitmap fixedImage = fixMirroredImage(flippedBitmap);
                            Bitmap bwBitmap = Utils.convertToBlackAndWhite(fixedImage);
                            Bitmap resizedBitmap = Utils.resizeImage(bwBitmap, 2048,1152); // Adjust max width and height as needed
                            selectedImageBitmap = Utils.bitmapToBase64(resizedBitmap);
                            if(isAdded()){
                                requireActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        imageViewPieceJustif.setImageBitmap(resizedBitmap);

                                    }
                                });
                            }
                            progressDialog.dismiss();
                        }
                    } catch (IOException e) {

                    } finally {
                        // Dismiss the progress dialog
                        progressDialog.dismiss();
                    }
                }).start();
            }
        } else {
            Toast.makeText(getActivity(), "Try Again!!", Toast.LENGTH_SHORT).show();
        }
    }
    private Bitmap handleExifOrientation(Bitmap bitmap, Uri imageUri) throws IOException {
        InputStream input = getActivity().getContentResolver().openInputStream(imageUri);
        ExifInterface exif = new ExifInterface(input);
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.postRotate(90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.postRotate(180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.postRotate(270);
                break;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.preScale(-1.0f, 1.0f);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.preScale(1.0f, -1.0f);
                break;
            default:
                return bitmap;
        }

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
    private Bitmap flipBitmap(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.preScale(-1.0f, 1.0f); // Flip horizontally
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private Bitmap fixMirroredImage(Bitmap original) {
        Matrix matrix = new Matrix();
        matrix.preScale(-1.0f, 1.0f); // Flip horizontally
        return Bitmap.createBitmap(original, 0, 0, original.getWidth(), original.getHeight(), matrix, true);
    }
}