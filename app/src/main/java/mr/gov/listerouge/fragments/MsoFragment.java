package mr.gov.listerouge.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.morpho.android.usb.USBManager;
import com.morpho.morphosmart.sdk.CallbackMask;
import com.morpho.morphosmart.sdk.CallbackMessage;
import com.morpho.morphosmart.sdk.Coder;
import com.morpho.morphosmart.sdk.CompressionAlgorithm;
import com.morpho.morphosmart.sdk.CustomInteger;
import com.morpho.morphosmart.sdk.DetectionMode;
import com.morpho.morphosmart.sdk.ErrorCodes;
import com.morpho.morphosmart.sdk.LatentDetection;
import com.morpho.morphosmart.sdk.MorphoDevice;
import com.morpho.morphosmart.sdk.MorphoImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import mr.gov.listerouge.R;
import mr.gov.listerouge.databinding.FragmentMsoBinding;
import mr.gov.listerouge.interfaces.FinishCallback;
import mr.gov.listerouge.tools.Utils;
import com.morpho.morphosmart.sdk.MorphoImageHeader;

public class MsoFragment extends Fragment implements FinishCallback, Observer {
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
    public Handler mHandler = new Handler();
    Observer observer;
    int detectionMode;
    ImageView imagefinger,progress;
    String strMessage = new String();
    private MorphoDevice morphoDevice;
    private int callbackCmd = (((((CallbackMask.MORPHO_CALLBACK_IMAGE_CMD.getValue() | CallbackMask.MORPHO_CALLBACK_ENROLLMENT_CMD.getValue()) | CallbackMask.MORPHO_CALLBACK_COMMAND_CMD.getValue()) | CallbackMask.MORPHO_CALLBACK_CODEQUALITY.getValue()) | CallbackMask.MORPHO_CALLBACK_DETECTQUALITY.getValue()) | CallbackMask.MORPHO_CALLBACK_BUSY_WARNING.getValue());

    Coder coder;
    int currentCaptureBitmapId;
    FragmentMsoBinding leftfrag;
    FinishCallback callback;
    public MsoFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        leftfrag = FragmentMsoBinding.inflate(inflater, container, false);
        return leftfrag.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            USBManager.getInstance().initialize(getContext(), "mr.gov.listerouge.USB_ACTION", true);
        }catch (Exception ex){

        }
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

                    JSONObject jj = pufingerprints();
                    if(jj.length() != 0) {
                        jsonBody.put("tenPrint", jj);
                    }

                    LoadingFragment detailsFragment = new LoadingFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("base64Image", jsonBody.toString());
                    bundle.putInt("type", 2);
                    detailsFragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, detailsFragment)
                            .commit();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
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
        observer = this;
        morphoDevice = new MorphoDevice();
        coder = Coder.MORPHO_DEFAULT_CODER;
        detectionMode = DetectionMode.MORPHO_ENROLL_DETECT_MODE.getValue();
        leftfrag.identify.setEnabled(false);
        leftfrag.clear.setEnabled(false);
        initrightbutton();
        initleftbutton();
    }


    private void initleftbutton(){

        leftfrag.leftlittlefinger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImage( REQUEST_LOADF1);
            }
        });

        leftfrag.leftring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImage(REQUEST_LOADF2);
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
            }
        });
        leftfrag.leftThumbfinger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImage(REQUEST_LOADF5);
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
    private void loadImage(int requestCode) {
        connectDevice(requestCode);
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
        Dialog dialog = new Dialog(getActivity());
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

    @Override
    public void update(Observable o, Object arg) {
        try {

            CallbackMessage callbackMessage = (CallbackMessage) arg;
            int messageType = callbackMessage.getMessageType();

            switch (messageType) {
                case 1:
                    handleMoveMessage(callbackMessage);
                    break;
                case 2:
                    handleImageMessage(callbackMessage);
                    break;
                case 3:
                    handleProgressBarMessage(callbackMessage);
                    break;
                case 5:
                    handleDeviceBusyMessage();
                    break;
                default:
                    // Handle unknown message type if needed
                    break;
            }
        } catch (Exception e) {
            alert(e.getMessage());
        }
    }
    public void morphoDeviceGetImage(final Observer paramObserver,int requestCode)
    {
        new Thread(new Runnable()
        {
            public void run()
            {
                Looper.prepare();
                int timeout = 30;
                CompressionAlgorithm compressionAlgorithm = CompressionAlgorithm.MORPHO_NO_COMPRESS;
                MorphoImage[] morphoImages = new MorphoImage[1];
                morphoImages[0] = new MorphoImage();
                int enrollmentCmdValue = CallbackMask.MORPHO_CALLBACK_IMAGE_CMD.getValue();

                int fingerprintQualityThreshold = 40;

                int compressRatio = 0;


                int detectionMode = DetectionMode.MORPHO_ENROLL_DETECT_MODE.getValue();


                LatentDetection latentDetection =  LatentDetection.LATENT_DETECT_ENABLE;

                int result = morphoDevice.getImage(
                        timeout,
                        fingerprintQualityThreshold,
                        compressionAlgorithm,
                        compressRatio,
                        detectionMode,
                        latentDetection,
                        morphoImages[0],
                        callbackCmd & enrollmentCmdValue,
                        paramObserver
                );

                if (result == 0) {
                    MorphoImage image =    morphoImages[0];
                    Bitmap resized = convertToBitmap(image);

                    String base64Image =  Utils.bitmapToBase64(resized);

                    if(isAdded()){
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(base64Image.isEmpty() || base64Image ==null){
                                    Toast.makeText(getContext(), "Image is null", Toast.LENGTH_SHORT).show();
                                }

                                if (requestCode == REQUEST_LOADF1) {
                                    leftfrag.leftfinger.setImageBitmap(resized);
                                    leftfrag.leftlittlefinger.getBackground().setTint(Color.parseColor("#00bfa5"));
                                    fp1 = base64Image;
                                } else if (requestCode == REQUEST_LOADF2) {
                                    leftfrag.leftfinger2.setImageBitmap(resized);
                                    leftfrag.leftring.getBackground().setTint(Color.parseColor("#00bfa5"));
                                    fp2 = base64Image;
                                } else if (requestCode == REQUEST_LOADF3) {
                                    leftfrag.leftfinger3.setImageBitmap(resized);
                                    leftfrag.leftmiddlefinger.getBackground().setTint(Color.parseColor("#00bfa5"));
                                    fp3 = base64Image;
                                } else if (requestCode == REQUEST_LOADF4) {
                                    leftfrag.leftfinger4.setImageBitmap(resized);
                                    leftfrag.leftindexfinger.getBackground().setTint(Color.parseColor("#00bfa5"));
                                    fp4 = base64Image;
                                } else if (requestCode == REQUEST_LOADF5) {
                                    leftfrag.leftfinger5.setImageBitmap(resized);
                                    leftfrag.leftThumbfinger.getBackground().setTint(Color.parseColor("#00bfa5"));
                                    fp5 = base64Image;
                                } else if (requestCode == REQUEST_LOADF6) {
                                    leftfrag.rightfinger.setImageBitmap(resized);
                                    leftfrag.rightlittle.getBackground().setTint(Color.parseColor("#00bfa5"));
                                    fp6 = base64Image;
                                } else if (requestCode == REQUEST_LOADF7) {
                                    leftfrag.rightfinger2.setImageBitmap(resized);
                                    leftfrag.rightringfinger.getBackground().setTint(Color.parseColor("#00bfa5"));
                                    fp7 = base64Image;
                                } else if (requestCode == REQUEST_LOADF8) {
                                    leftfrag.rightfinger3.setImageBitmap(resized);
                                    leftfrag.rightmiddlefinger.getBackground().setTint(Color.parseColor("#00bfa5"));
                                    fp8 = base64Image;
                                } else if (requestCode == REQUEST_LOADF9) {
                                    leftfrag.rightfinger4.setImageBitmap(resized);
                                    leftfrag.rightindex.getBackground().setTint(Color.parseColor("#00bfa5"));
                                    fp9 = base64Image;
                                } else if (requestCode == REQUEST_LOADF10) {
                                    leftfrag.rightfinger5.setImageBitmap(resized);
                                    leftfrag.rightthumbfinger.getBackground().setTint(Color.parseColor("#00bfa5"));
                                    fp10 = base64Image;
                                }
                                leftfrag.identify.setEnabled(true);
                                leftfrag.clear.setEnabled(true);
                            }
                        });
                    }

                }

                Looper.loop();
            }
        }).start();
    }
    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // Compress the bitmap to the byte array output stream (JPEG format, quality 100)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        // Convert byte array to Base64 encoded string
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }



    public void updateSensorProgressBar(int i) {
        try {

            ShapeDrawable shapeDrawable = new ShapeDrawable(new RoundRectShape(new float[]{5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f}, null, null));

            int color;
            if (i <= 25) {
                color = Color.GREEN; // Example color for progress <= 25
            } else if (i <= 75) {
                color = Color.YELLOW; // Example color for progress <= 75
            } else {
                color = Color.RED; // Example color for progress > 75
            }

            shapeDrawable.getPaint().setColor(color);
            leftfrag.verticalProgressbar.setProgressDrawable(new ClipDrawable(shapeDrawable, Gravity.BOTTOM, ClipDrawable.VERTICAL));
            leftfrag.verticalProgressbar.setProgress(i);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle or log the exception appropriately
        }
    }


    private void updateSensorMessage(String str, ImageView imageView) {
        try {
            leftfrag.textViewMessage.setText(str);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle or log the exception appropriately
        }
    }

    /* access modifiers changed from: private */
    public void updateImage(Bitmap bitmap) {
        try {
            leftfrag.imageView2.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    /* access modifiers changed from: private */
    @SuppressLint({"NewApi"})
    public void updateImageBackground(int i, int i2) {
        Drawable drawable;
        if (i2 <= 25) {
            drawable = getResources().getDrawable(R.drawable.red_border);
        } else if (i2 <= 75) {
            drawable = getResources().getDrawable(R.drawable.yellow_border);
        } else {
            drawable = getResources().getDrawable(R.drawable.green_border);
        }
        if (progress != null) {
            progress.setBackground(drawable);
        }
    }

   

    private void handleMoveMessage(CallbackMessage callbackMessage) {
        try {
            int message = (Integer) callbackMessage.getMessage();
            switch (message) {
                case 0:
                    this.strMessage = "move-no-finger";
                    break;
                case 1:
                    this.strMessage = "move-finger-up";
                    break;
                case 2:
                    this.strMessage = "move-finger-down";
                    break;
                case 3:
                    this.strMessage = "move-finger-left";
                    break;
                case 4:
                    this.strMessage = "move-finger-right";
                    break;
                case 5:
                    this.strMessage = "press-harder";
                    break;
                case 6:
                    this.strMessage = "move-latent";
                    break;
                case 7:
                    this.strMessage = "remove-finger";
                    break;
                case 8:
                    this.strMessage = "finger-ok";
                    break;
                default:
                    // Handle unknown message value if needed
                    break;
            }
            postUpdateSensorMessage();
        } catch (Exception e) {
            alert(e.getMessage());
        }
    }

    private void handleImageMessage(CallbackMessage callbackMessage) {
        try {
            MorphoImage morphoImageFromLive = MorphoImage.getMorphoImageFromLive((byte[]) callbackMessage.getMessage());
            final Bitmap bitmap = Bitmap.createBitmap(morphoImageFromLive.getMorphoImageHeader().getNbColumn(), morphoImageFromLive.getMorphoImageHeader().getNbRow(), Bitmap.Config.ALPHA_8);
            bitmap.copyPixelsFromBuffer(ByteBuffer.wrap(morphoImageFromLive.getImage(), 0, morphoImageFromLive.getImage().length));
            postUpdateImage(bitmap);
        } catch (Exception e) {
            alert(e.getMessage());
        }
    }

    private void handleProgressBarMessage(CallbackMessage callbackMessage) {
        try {
            final int progress = (Integer) callbackMessage.getMessage();
            postUpdateSensorProgressBar(progress);
            postUpdateImageBackground(this.currentCaptureBitmapId, progress);
        } catch (Exception e) {
            alert(e.getMessage());
        }
    }

    private void handleDeviceBusyMessage() {
        this.strMessage = "device-is-busy";
        postUpdateSensorMessage();
    }



    private void postUpdateSensorMessage() {
        this.mHandler.post(new Runnable() {
            @Override
            public void run() {
                updateSensorMessage(strMessage,leftfrag.imageView2);
            }
        });
    }

    private void postUpdateImage(final Bitmap bitmap) {
        this.mHandler.post(new Runnable() {
            @Override
            public void run() {
                updateImage(bitmap);
            }
        });
    }

    private void postUpdateSensorProgressBar(final int progress) {
        this.mHandler.post(new Runnable() {
            @Override
            public void run() {
                updateSensorProgressBar(progress);
            }
        });
    }

    private void postUpdateImageBackground(final int captureBitmapId, final int progress) {
        this.mHandler.post(new Runnable() {
            @Override
            public void run() {
                updateImageBackground(captureBitmapId, progress);
            }
        });
    }
    public void alert(String str) {
        AlertDialog create = new AlertDialog.Builder(getContext()).create();
        create.setTitle(R.string.app_name);
        create.setMessage(str);
        create.setButton(-1, "Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        create.show();
    }
    public static Bitmap loadBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }
    public Bitmap convertToBitmap(MorphoImage morphoImageFromLive) {
        try {
            // Get image dimensions
            int width = morphoImageFromLive.getMorphoImageHeader().getNbColumn();
            int height = morphoImageFromLive.getMorphoImageHeader().getNbRow();

            // Create a Bitmap with ARGB_8888 configuration
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

            // Get the image data as a byte array
            byte[] imageData = morphoImageFromLive.getImage(); // Assuming getImage() returns byte[]

            // Set the color palette for grayscale
            int[] pixels = new int[width * height];
            for (int i = 0; i < width * height; i++) {
                int gray = (imageData[i] & 0xFF); // Convert byte to unsigned value
                pixels[i] = Color.rgb(gray, gray, gray); // Grayscale color
            }

            // Set the pixel colors in the bitmap
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private Bitmap getBitmapFromImageView(MorphoImage morphoImageFromLive) {
        if (morphoImageFromLive != null) {
            MorphoImageHeader header = morphoImageFromLive.getMorphoImageHeader();
            int width = header.getNbColumn();
            int height = header.getNbRow();
            Bitmap alphaBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8);
            ByteBuffer buffer = ByteBuffer.wrap(morphoImageFromLive.getImage());
            alphaBitmap.copyPixelsFromBuffer(buffer);
            Bitmap argbBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int alpha = alphaBitmap.getPixel(x, y) & 0xFF; // Extract the alpha value
                    int argb = (alpha << 24) | (0x00FFFFFF); // Create ARGB color with alpha value and white color
                    pixels[y * width + x] = argb;
                }
            }
            argbBitmap.setPixels(pixels, 0, width, 0, 0, width, height);

            // Save the Bitmap to a temporary file
            try {
                File tempFile = File.createTempFile("temp_image", ".jpg", getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES));
                FileOutputStream out = new FileOutputStream(tempFile);
                argbBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();

                // Load the JPEG file
                Bitmap loadedBitmap = BitmapFactory.decodeFile(tempFile.getAbsolutePath());
                return loadedBitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void connectDevice(int requestCode) {

        CustomInteger customInteger = new CustomInteger();
        int result = morphoDevice.initUsbDevicesNameEnum(customInteger);
        int numDevices = customInteger.getValueOf();

        if (result != 0) {
            showErrorDialog(ErrorCodes.getError(result, morphoDevice.getInternalError()));
        } else if (numDevices > 0) {
            String sensorName = morphoDevice.getUsbDeviceName(0);


            result = morphoDevice.openUsbDevice(sensorName, 0);
            if (result != 0) {
                showErrorDialog(ErrorCodes.getError(result, morphoDevice.getInternalError()));
            }
            else{
                morphoDeviceGetImage(observer,requestCode);
            }
        }
    }
    private void showErrorDialog(String message) {
        new AlertDialog.Builder(getContext())
                .setTitle("error")
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("ok", null)
                .show();
    }
}