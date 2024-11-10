package mr.gov.listerouge.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
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
import mr.gov.listerouge.databinding.FragmentAuthenticateBinding;
import mr.gov.listerouge.interfaces.FinishCallback;


public class AuthenticateFragment extends Fragment implements FinishCallback {
    private static final int REQUEST_LOAD = 200;
    private static final int REQUEST_LOADF1 = 1;
    private static final int REQUEST_LOADF2 = 2;
    private static final int REQUEST_LOADF3 = 3;
    private static final int REQUEST_LOADF4 = 4;
    private static final int REQUEST_LOADF5 = 5;
    private static final int REQUEST_LOADF6 = 6;
    private static final int REQUEST_LOADF7 = 7;
    private static final int REQUEST_LOADF8 = 8;
    private static final int REQUEST_LOADF9 = 9;
    private static final int REQUEST_LOADF10 = 10;
    private String image;
    private String fp1;
    private String fp2;
    private String fp3;
    private String fp4;
    private String fp5;
    private String fp6;
    private String fp7;
    private String fp8;
    private String fp9;
    private String fp10;
    private boolean righthand_active = false;
    private boolean lefthand_active = false;
    Dialog dialog;
    FragmentAuthenticateBinding leftfrag;
    FinishCallback callback;
    public AuthenticateFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        leftfrag = FragmentAuthenticateBinding.inflate(inflater, container, false);
        return leftfrag.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        leftfrag.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImage(REQUEST_LOAD);
            }
        });
        dialog = new Dialog(getActivity());
        callback = this;
        leftfrag.identify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define your JSON request body
                JSONObject jsonBody = new JSONObject();

                try {
                    JSONObject img = putdata(image);
                    if (img != null) {
                        jsonBody.put("photo", img);
                    }
                     String nni = leftfrag.editnni.getText().toString();
                    if(nni!=null) {
                        JSONObject jj = pufingerprints();
                        if(jj.length() != 0) {
                            jsonBody.put("tenPrint", jj);
                        }
                        jsonBody.put("nni",nni );
                        LoadingFragment detailsFragment = new LoadingFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("base64Image", jsonBody.toString());


                        bundle.putInt("type", 4);
                        detailsFragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame_layout, detailsFragment)
                                .commit();
                    }else{

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        leftfrag.clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image = null;
                fp1 = null;
                fp2 = null;
                fp3 = null;
                fp4 = null;
                fp5 = null;
                fp6 = null;
                fp7 = null;
                fp8 = null;
                fp9 = null;
                fp10 = null;
                leftfrag.imageView2.setImageBitmap(null);
                leftfrag.rightlittle.getBackground().setTintList(null);
                leftfrag.rightringfinger.getBackground().setTintList(null);
                leftfrag.rightmiddlefinger.getBackground().setTintList(null);
                leftfrag.rightindex.getBackground().setTintList(null);
                leftfrag.rightthumbfinger.getBackground().setTintList(null);
                leftfrag.leftlittlefinger.getBackground().setTintList(null);
                leftfrag.leftring.getBackground().setTintList(null);
                leftfrag.leftmiddlefinger.getBackground().setTintList(null);
                leftfrag.leftindexfinger.getBackground().setTintList(null);
                leftfrag.leftThumbfinger.getBackground().setTintList(null);
                leftfrag.leftfinger.setImageBitmap(null);
                leftfrag.leftfinger2.setImageBitmap(null);
                leftfrag.leftfinger3.setImageBitmap(null);
                leftfrag.leftfinger4.setImageBitmap(null);
                leftfrag.leftfinger5.setImageBitmap(null);
                leftfrag.rightfinger.setImageBitmap(null);
                leftfrag.rightfinger2.setImageBitmap(null);
                leftfrag.rightfinger3.setImageBitmap(null);
                leftfrag.rightfinger4.setImageBitmap(null);
                leftfrag.rightfinger5.setImageBitmap(null);
            }
        });
        leftfrag.lefthandswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    enablelefthand();
                    lefthand_active = true;
                } else {
                    disablelefthand();
                    lefthand_active = false;
                }
            }
        });
        leftfrag.righthandswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    enablerighthand();
                    righthand_active = true;
                } else {
                    disablerighthand();
                    righthand_active = false;
                }
            }
        });

        leftfrag.identify.setEnabled(false);
        leftfrag.clear.setEnabled(false);
        initrightbutton();
        initleftbutton();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK && data != null) {

            Uri selectedImage = data.getData();
            if (selectedImage != null) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    bitmap = handleExifOrientation(bitmap, selectedImage);
                    // Flip the bitmap
                    Bitmap flippedBitmap = flipBitmap(bitmap);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    flippedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    String base64Image = convertToBase64(byteArray);
                    if (requestCode == REQUEST_LOAD) {
                        leftfrag.imageView2.setImageBitmap(flippedBitmap);
                        image = base64Image;
                    } else if (requestCode == REQUEST_LOADF1) {
                        leftfrag.leftfinger.setImageBitmap(flippedBitmap);
                        leftfrag.leftlittlefinger.getBackground().setTint(Color.parseColor("#FFFACD"));
                        fp1 = base64Image;
                    } else if (requestCode == REQUEST_LOADF2) {
                        leftfrag.leftfinger2.setImageBitmap(flippedBitmap);
                        leftfrag.leftring.getBackground().setTint(Color.parseColor("#FFFACD"));
                        fp2 = base64Image;
                    } else if (requestCode == REQUEST_LOADF3) {
                        leftfrag.leftfinger3.setImageBitmap(flippedBitmap);
                        leftfrag.leftmiddlefinger.getBackground().setTint(Color.parseColor("#FFFACD"));
                        fp3 = base64Image;
                    } else if (requestCode == REQUEST_LOADF4) {
                        leftfrag.leftfinger4.setImageBitmap(flippedBitmap);
                        leftfrag.leftindexfinger.getBackground().setTint(Color.parseColor("#FFFACD"));
                        fp4 = base64Image;
                    } else if (requestCode == REQUEST_LOADF5) {
                        leftfrag.leftfinger5.setImageBitmap(flippedBitmap);
                        leftfrag.leftThumbfinger.getBackground().setTint(Color.parseColor("#FFFACD"));
                        fp5 = base64Image;
                    } else if (requestCode == REQUEST_LOADF6) {
                        leftfrag.rightfinger.setImageBitmap(flippedBitmap);
                        leftfrag.rightlittle.getBackground().setTint(Color.parseColor("#FFFACD"));
                        fp6 = base64Image;
                    } else if (requestCode == REQUEST_LOADF7) {
                        leftfrag.rightfinger2.setImageBitmap(flippedBitmap);
                        leftfrag.rightringfinger.getBackground().setTint(Color.parseColor("#FFFACD"));
                        fp7 = base64Image;
                    } else if (requestCode == REQUEST_LOADF8) {
                        leftfrag.rightfinger3.setImageBitmap(flippedBitmap);
                        leftfrag.rightmiddlefinger.getBackground().setTint(Color.parseColor("#FFFACD"));
                        fp8 = base64Image;
                    } else if (requestCode == REQUEST_LOADF9) {
                        leftfrag.rightfinger4.setImageBitmap(flippedBitmap);
                        leftfrag.rightindex.getBackground().setTint(Color.parseColor("#FFFACD"));
                        fp9 = base64Image;
                    } else if (requestCode == REQUEST_LOADF10) {
                        leftfrag.rightfinger5.setImageBitmap(flippedBitmap);
                        leftfrag.rightthumbfinger.getBackground().setTint(Color.parseColor("#FFFACD"));
                        fp10 = base64Image;
                    }
                    leftfrag.identify.setEnabled(true);
                    leftfrag.clear.setEnabled(true);
                } catch (IOException e) {
                    e.printStackTrace();
                    callback.onError(e.getMessage());
                }
            }
        } else {
            Toast.makeText(getActivity(), "Try Again!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void initleftbutton(){

        leftfrag.leftlittlefinger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadImage(REQUEST_LOADF1);
                ;
            }
        });

        leftfrag.leftring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadImage(REQUEST_LOADF2);
                ;
            }
        });
        leftfrag.leftmiddlefinger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadImage(REQUEST_LOADF3);
            }
        });
        leftfrag.leftindexfinger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadImage(REQUEST_LOADF4);
                ;
            }
        });
        leftfrag.leftThumbfinger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadImage(REQUEST_LOADF5);
                ;
            }
        });

    }
    private  void initrightbutton(){

        leftfrag.rightlittle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadImage(REQUEST_LOADF6);
                ;
            }
        });
        leftfrag.rightringfinger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ; loadImage(REQUEST_LOADF7);
            }
        });
        leftfrag.rightmiddlefinger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadImage(REQUEST_LOADF8);
            }
        });
        leftfrag.rightindex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadImage(REQUEST_LOADF9);

            }
        });
        leftfrag.rightthumbfinger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadImage(REQUEST_LOADF10);
                ;
            }
        });
    }
    private void disablelefthand(){

        leftfrag.leftlittlefinger.setEnabled(false);
        leftfrag.leftring.setEnabled(false);
        leftfrag.leftmiddlefinger.setEnabled(false);
        leftfrag.leftindexfinger.setEnabled(false);
        leftfrag.leftThumbfinger.setEnabled(false);
    }
    private void enablelefthand(){

        leftfrag.leftlittlefinger.setEnabled(true);
        leftfrag.leftring.setEnabled(true);
        leftfrag.leftmiddlefinger.setEnabled(true);
        leftfrag.leftindexfinger.setEnabled(true);
        leftfrag.leftThumbfinger.setEnabled(true);
    }
    private void disablerighthand(){

        leftfrag.rightlittle.setEnabled(false);
        leftfrag.rightringfinger.setEnabled(false);
        leftfrag.rightmiddlefinger.setEnabled(false);
        leftfrag.rightindex.setEnabled(false);
        leftfrag.rightthumbfinger.setEnabled(false);
    }
    private void enablerighthand(){

        leftfrag.rightlittle.setEnabled(true);
        leftfrag.rightringfinger.setEnabled(true);
        leftfrag.rightmiddlefinger.setEnabled(true);
        leftfrag.rightindex.setEnabled(true);
        leftfrag.rightthumbfinger.setEnabled(true);
    }
    private void loadImage(int code) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, code);
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
    private String convertToBase64(byte[] bytes) {
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    private  JSONObject putdata(String data){
        JSONObject object = new JSONObject();
        try {
            if(data!=null) {
                object.put("data", data);
                object.put("format", "JPEG");
                return object;
            }else {
                return null;
            }
        } catch (JSONException e) {
            callback.onError(e.getMessage());
            return null;
        }

    }
    private JSONObject pufingerprints() throws JSONException {
        JSONObject tenprint = new JSONObject();
        if (righthand_active) {
            JSONObject littleobject = putdata(fp6);
            if (littleobject != null) {
                tenprint.put("rightLittle", littleobject);
            }

            JSONObject ringeobject = putdata(fp7);
            if (ringeobject != null) {
                tenprint.put("rightRing", ringeobject);
            }

            JSONObject middleobject = putdata(fp8);
            if (middleobject != null) {
                tenprint.put("rightMiddle", middleobject);
            }

            JSONObject indexobject = putdata(fp9);
            if (indexobject != null) {
                tenprint.put("rightIndex", indexobject);
            }

            JSONObject thumbobject = putdata(fp10);
            if (thumbobject != null) {
                tenprint.put("rightThumb", thumbobject);
            }
        }

        if (lefthand_active) {
            JSONObject littleobject2 = putdata(fp1);
            if (littleobject2 != null) {
                tenprint.put("leftLittle", littleobject2);
            }

            JSONObject ringeobject2 = putdata(fp2);
            if (ringeobject2 != null) {
                tenprint.put("leftRing", ringeobject2);
            }

            JSONObject middleobject2 = putdata(fp3);
            if (middleobject2 != null) {
                tenprint.put("leftMiddle", middleobject2);
            }

            JSONObject indexobject2 = putdata(fp4);
            if (indexobject2 != null) {
                tenprint.put("leftIndex", indexobject2);
            }

            JSONObject thumbobject2 = putdata(fp5);
            if (thumbobject2 != null) {
                tenprint.put("leftThumb", thumbobject2);
            }
        }

        return tenprint;
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError(String message) {
        showAlert(message);
    }
    private void showAlert(String text){
        dialog.setContentView(R.layout.load_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; // Apply the animation style

        TextView okay_text = dialog.findViewById(R.id.okay_text);
        TextView textview = dialog.findViewById(R.id.textview);
        textview.setText(text);
        okay_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}