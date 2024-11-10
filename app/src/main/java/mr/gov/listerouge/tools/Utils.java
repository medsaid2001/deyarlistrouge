package mr.gov.listerouge.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.Image;
import android.util.Base64;
import android.util.Log;

import androidx.exifinterface.media.ExifInterface;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Utils {

    private static final String TAG = "Utils";

    // Method to convert Android Image to Bitmap
    public static Bitmap convertImageToBitmap(Image image) {
        if (image == null) {
            Log.e(TAG, "Image is null");
            return null;
        }

        Image.Plane[] planes = image.getPlanes();
        ByteBuffer buffer = planes[0].getBuffer();
        int pixelStride = planes[0].getPixelStride();
        int rowStride = planes[0].getRowStride();
        int rowPadding = rowStride - pixelStride * image.getWidth();

        Bitmap bitmap = Bitmap.createBitmap(image.getWidth() + rowPadding / pixelStride,
                image.getHeight(), Bitmap.Config.ARGB_8888);
        bitmap.copyPixelsFromBuffer(buffer);

        image.close();

        return bitmap;
    }

    // Method to convert Image to Base64 string
    public static String imageToBase64(Image image) {
        Bitmap bitmap = convertImageToBitmap(image);
        if (bitmap == null) {
            Log.e(TAG, "Failed to convert image to bitmap");
            return null;
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    // Method to save Image to file in temporary cache directory
    public static File saveImageToTempFile(Context context, Image image) {
        File directory = context.getCacheDir();

        // Generate a unique filename
        String fileName = "temp_image_" + System.currentTimeMillis() + ".jpg";
        File file = new File(directory, fileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            ByteBuffer buffer = image.getPlanes()[0].getBuffer();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            fos.write(bytes);

            // Fix the rotation
            fixImageRotation(file.getAbsolutePath());

            return file;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            image.close();
        }
        return null;
    }

    // Method to load Bitmap from file
    public static Bitmap loadBitmapFromFile(String filePath) {
        return BitmapFactory.decodeFile(filePath);
    }

    // Method to convert Bitmap to Base64 string
    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // Compress the bitmap to the byte array output stream (JPEG format, quality 100)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        // Convert byte array to Base64 encoded string
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    // Method to fix the rotation of the image
    private static void fixImageRotation(String filePath) {
        try {
            ExifInterface exif = new ExifInterface(filePath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            Bitmap rotatedBitmap = null;

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotatedBitmap = rotateImage(bitmap, 90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotatedBitmap = rotateImage(bitmap, 180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotatedBitmap = rotateImage(bitmap, 270);
                    break;
                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    rotatedBitmap = bitmap;
            }

            if (rotatedBitmap != bitmap) {
                FileOutputStream fos = new FileOutputStream(filePath);
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to rotate the image
    private static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
    public static Bitmap convertToBlackAndWhite(Bitmap originalBitmap) {
        Bitmap bwBitmap = Bitmap.createBitmap(originalBitmap.getWidth(), originalBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bwBitmap);
        Paint paint = new Paint();
        paint.setColorFilter(new android.graphics.ColorMatrixColorFilter(new android.graphics.ColorMatrix(new float[]{
                0.33f, 0.33f, 0.33f, 0, 0,
                0.33f, 0.33f, 0.33f, 0, 0,
                0.33f, 0.33f, 0.33f, 0, 0,
                0, 0, 0, 1, 0
        })));
        canvas.drawBitmap(originalBitmap, 0, 0, paint);
        return bwBitmap;
    }

    // Function to reduce the size of an image without losing quality
    public static Bitmap resizeImage(Bitmap originalBitmap, int maxWidth, int maxHeight) {
        int width = originalBitmap.getWidth();
        int height = originalBitmap.getHeight();
        float aspectRatio = (float) width / height;

        if (width > maxWidth || height > maxHeight) {
            if (width > height) {
                width = maxWidth;
                height = Math.round(width / aspectRatio);
            } else {
                height = maxHeight;
                width = Math.round(height * aspectRatio);
            }
        } else {
            return originalBitmap;
        }

        return Bitmap.createScaledBitmap(originalBitmap, width, height, true);
    }
    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
