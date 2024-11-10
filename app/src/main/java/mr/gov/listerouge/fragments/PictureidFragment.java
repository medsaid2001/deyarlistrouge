package mr.gov.listerouge.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import mr.gov.listerouge.R;
import mr.gov.listerouge.databinding.FragmentPictureidBinding;
import mr.gov.listerouge.interfaces.FinishCallback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PictureidFragment extends Fragment {

    FragmentPictureidBinding leftfrag;
    FinishCallback callback;

    private static final int REQUEST_IMAGE_PICK = 1;

    public PictureidFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        leftfrag = FragmentPictureidBinding.inflate(inflater, container, false);
        return leftfrag.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        leftfrag.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_IMAGE_PICK);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK && requestCode == REQUEST_IMAGE_PICK && data != null) {
            Uri selectedImage = data.getData();
            if (selectedImage != null) {
                try {
                    // Get the image bitmap
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    bitmap = handleExifOrientation(bitmap, selectedImage);
                    // Flip the bitmap
                    Bitmap flippedBitmap = flipBitmap(bitmap);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    flippedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    String base64Image = convertToBase64(byteArray);
                    leftfrag.imageView2.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Failed to get image", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "Try Again!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String convertToBase64(byte[] bytes) {
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    private void postImage(String base64Image) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();

                    // Define your JSON request body
                    JSONObject jsonBody = new JSONObject();
                    JSONObject photoObject = new JSONObject();
                    photoObject.put("data", base64Image);
                    photoObject.put("format", "JPEG");
                    jsonBody.put("photo", photoObject);

                    // Build your request body
                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonBody.toString());

                    // Build your request with headers and body
                    Request request = new Request.Builder()
                            .url("https://api-houwiyeti.anrpts.gov.mr/houwiyetiapi/v1/partners/identify")
                            .addHeader("entity-Api-Key", "a96e90c5-d561-4a1d-8307-a22b8999cc9f") // Add your header
                            .post(requestBody)
                            .build();

                    // Execute the request
                    Response response = client.newCall(request).execute();

                    // Handle the response here
                    if (response.isSuccessful()) {
                        String responseBody = response.body().string();
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DetailsFragment detailsFragment = new DetailsFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("responseString", responseBody);
                                detailsFragment.setArguments(bundle);
                                getActivity().getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.frame_layout, detailsFragment)
                                        .commit();
                            }
                        });
                    } else {
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
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

}
