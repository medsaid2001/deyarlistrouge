package mr.gov.listerouge.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Bundle;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;

import java.io.File;
import java.util.Arrays;

import mr.gov.listerouge.R;
import mr.gov.listerouge.interfaces.FinishCallback;
import mr.gov.listerouge.tools.Utils;

public class CameraFragment extends Fragment implements FinishCallback {
    private static final int REQUEST_PERMISSION_CODE = 1001;
    private static final String TAG = "CameraFragment";
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    private static final int REQUEST_CAMERA_PERMISSION = 200;

    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    private CameraDevice cameraDevice;
    private CameraCaptureSession cameraCaptureSession;
    private CaptureRequest.Builder captureRequestBuilder;
    private ImageReader imageReader;
    private Size imageDimension;

    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private LottieAnimationView captureButton;
    private Image image;
    private boolean isBusy;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews(view);
        setOnClickListeners();
        requestCameraPermission();
    }

    private void initializeViews(View view) {
        surfaceView = view.findViewById(R.id.surface_view);
        captureButton = view.findViewById(R.id.cameraid);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(surfaceCallback);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("cliquez sur l'écran pour annuler");
        progressDialog.setCancelable(true);
    }

    private void setOnClickListeners() {
        captureButton.setOnClickListener(v -> {
            captureButton.setEnabled(false);
            progressDialog.show();
            takePicture();
        });
    }

    private final SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            openCamera();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            closeCamera();
        }
    };

    private void takePicture() {
        if (cameraDevice == null) {
            handleError("CameraDevice is null");
            return;
        }

        try {
            CaptureRequest.Builder captureBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            captureBuilder.addTarget(imageReader.getSurface());
            captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);

            int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();
            captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATIONS.get(rotation));

            cameraCaptureSession.capture(captureBuilder.build(), new CameraCaptureSession.CaptureCallback() {
                @Override
                public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
                    processImage();
                }

                @Override
                public void onCaptureFailed(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull CaptureFailure failure) {
                   // handleError("Capture failed: " + failure);
                }
            }, null);
        } catch (CameraAccessException e) {
            handleError("Failed to capture image: " + e.getMessage());
        }
    }

    private void processImage() {
        new Thread(() -> {
            if (image != null) {
                try {
                    File imageFile = Utils.saveImageToTempFile(getContext(), image);
                    if (imageFile != null) {
                        Bitmap originalBitmap = Utils.loadBitmapFromFile(imageFile.getPath());
                        Bitmap bwBitmap = Utils.convertToBlackAndWhite(originalBitmap);
                        Bitmap resizedBitmap = Utils.resizeImage(bwBitmap, 2048,1152); // Adjust max width and height as needed
                        String base64 = Utils.bitmapToBase64(resizedBitmap);
                        getActivity().runOnUiThread(() -> {
                            replaceFragment(base64);
                        });
                    }
                } catch (Exception e) {
                   // handleError("Failed to process image: " + e.getMessage());
                } finally {
                    image.close();
                    getActivity().runOnUiThread(() -> {
                        captureButton.setEnabled(true);
                        progressDialog.dismiss();
                    });
                }
            } else {
                handleError("Failed to capture image");
            }
        }).start();
    }

    private void openCamera() {
        CameraManager manager = (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);
        try {
            String cameraId = manager.getCameraIdList()[0];
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            imageDimension = map.getOutputSizes(SurfaceHolder.class)[0];

            imageReader = ImageReader.newInstance(imageDimension.getWidth(), imageDimension.getHeight(), ImageFormat.JPEG, 1);
            imageReader.setOnImageAvailableListener(reader -> {
                image = reader.acquireLatestImage();
                if (image != null && !isBusy) {
                    isBusy = true;
                    processImage();
                }
            }, null);

            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestCameraPermission();
                return;
            }
            manager.openCamera(cameraId, stateCallback, null);
        } catch (CameraAccessException e) {
            handleError("Failed to open camera: " + e.getMessage());
        }
    }

    private final CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            cameraDevice = camera;
            createCameraPreview();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            handleError("Camera disconnected");
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            handleError("Camera error: " + error);
        }
    };

    private void createCameraPreview() {
        try {
            if (cameraDevice == null || !surfaceHolder.getSurface().isValid() || imageReader == null) {
                //Log.e(TAG, "CameraDevice or SurfaceHolder or ImageReader is not ready");
                return;
            }

            Surface surface = surfaceHolder.getSurface();
            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuilder.addTarget(surface);

            cameraDevice.createCaptureSession(Arrays.asList(surface, imageReader.getSurface()), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    if (cameraDevice == null) return;
                    cameraCaptureSession = session;
                    try {
                        captureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
                        cameraCaptureSession.setRepeatingRequest(captureRequestBuilder.build(), null, null);
                    } catch (CameraAccessException e) {
                        handleError("Failed to start camera preview: " + e.getMessage());
                    }
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                    handleError("Failed to configure camera");
                }
            }, null);
        } catch (CameraAccessException e) {
            handleError("Failed to create camera preview: " + e.getMessage());
        }
    }

    private void requestCameraPermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) ||
                shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        && shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(getActivity())
                    .setMessage("Camera and storage permissions are required")
                    .setPositiveButton("OK", (dialog, which) -> requestPermissions(
                            new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_CAMERA_PERMISSION))
                    .setNegativeButton("Cancel", (dialog, which) -> getActivity().finish())
                    .create()
                    .show();
        } else {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CAMERA_PERMISSION);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (surfaceHolder.getSurface().isValid()) {
            openCamera();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        closeCamera();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        closeCamera();
    }

    private void closeCamera() {
        if (cameraCaptureSession != null) {
            cameraCaptureSession.close();
            cameraCaptureSession = null;
        }
        if (cameraDevice != null) {
            cameraDevice.close();
            cameraDevice = null;
        }
        if (imageReader != null) {
            imageReader.close();
            imageReader = null;
        }
    }

    @Override
    public void onSuccess() {
        getActivity().runOnUiThread(() -> {
            captureButton.setEnabled(true);
            progressDialog.dismiss();
        });
    }

    @Override
    public void onError(String message) {
        getActivity().runOnUiThread(() -> {
            captureButton.setEnabled(true);
            progressDialog.dismiss();
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        });
    }

    private void replaceFragment(String base64) {
        if (isAdded()) {
            LoadingFragment detailsFragment = new LoadingFragment();
            Bundle bundle = new Bundle();
            bundle.putString("base64Image", base64);
            bundle.putInt("type", 1);
            detailsFragment.setArguments(bundle);
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, detailsFragment)
                    .commit();
        }
    }

    private void handleError(String errorMessage) {
        //Log.e(TAG, errorMessage);
        getActivity().runOnUiThread(() -> {
            captureButton.setEnabled(true);
            progressDialog.dismiss();
            Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
        });
    }
}

