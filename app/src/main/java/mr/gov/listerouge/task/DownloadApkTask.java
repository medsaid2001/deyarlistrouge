package mr.gov.listerouge.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import mr.gov.listerouge.interfaces.DownloadApkCallback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DownloadApkTask extends AsyncTask<String, Integer, String> {

    private static final String TAG = "DownloadApkTask";
    private Context context;
    private ProgressBar progressBar;
    private TextView tagLine;
    private String currentVersionName;
    private int currentVersionCode;
    private DownloadApkCallback callback;
    private ProgressDialog progressDialog;

    public DownloadApkTask(Context context, ProgressBar progressBar, TextView tagLine, DownloadApkCallback callback) {
        this.context = context;
        this.progressBar = progressBar;
        this.tagLine = tagLine;
        this.callback = callback;
        this.currentVersionName = getCurrentVersionName();
        this.currentVersionCode = getCurrentVersionCode();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Checking for updates...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... urls) {
        String updateJsonUrl = urls[0];
        try {
            // Make a POST request using OkHttp to fetch update.json
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            JSONObject jsonOb = new JSONObject();
            try {
                jsonOb.put("key", "123-123-987-456");
                jsonOb.put("file", "update.json");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            RequestBody body = RequestBody.create(mediaType, jsonOb.toString());
            Request request = new Request.Builder()
                    .url(updateJsonUrl)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                return "Failed to download config file";
            }

            String jsonString = response.body().string();
            JSONObject jsonObject = new JSONObject(jsonString);
            int appVersion = jsonObject.getInt("appversion_redlist");
            String apkUrl = jsonObject.getString("app_url_redlist");
            String app_name = jsonObject.getString("appname_redlist");

            if (appVersion > currentVersionCode) {
                // Download the APK
                downloadApk(apkUrl, app_name + ".apk");
            } else {
                return "App is up-to-date";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Update failed";
        }
        return "Update completed";
    }

    private void downloadApk(String apkUrl, String apkname) {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("key", "123-123-987-456");
            jsonObject.put("file", apkname);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        RequestBody body = RequestBody.create(mediaType, jsonObject.toString());
        Request request = new Request.Builder()
                .url(apkUrl)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                publishProgress(-2);  // Notify failure
                return;
            }
            InputStream input = response.body().byteStream();
            int fileLength = (int) response.body().contentLength();

            // Define the path where the APK will be saved
            File file = new File(context.getExternalFilesDir(null), "downloaded_app.apk");
            try (FileOutputStream output = new FileOutputStream(file)) {
                byte[] data = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    if (isCancelled()) {
                        input.close();
                        return;
                    }
                    total += count;
                    // Publishing the progress....
                    publishProgress(1, (int) (total * 100 / fileLength));  // Update progress for APK download
                    output.write(data, 0, count);
                }

                output.flush();
            }

            publishProgress(1, 100);  // Notify progress as complete
            installApk(file);

        } catch (Exception e) {
            e.printStackTrace();
            publishProgress(-2);  // Notify failure
        }
    }

    private void installApk(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri apkUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
        intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
        switch (progress[0]) {
            case 0:  // Config file download progress
                tagLine.setText("Downloading config file...");
                break;
            case 1:  // APK download progress
                progressBar.setIndeterminate(false);
                progressBar.setProgress(progress[1]);
                tagLine.setText("Downloading APK: " + progress[1] + "%");
                break;
            case -2:  // Failure
                progressBar.setVisibility(View.GONE);
                tagLine.setText("Update failed");
                break;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        progressBar.setVisibility(View.GONE);
        tagLine.setText(result);
        Toast.makeText(context, result, Toast.LENGTH_SHORT).show();

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        // Notify the callback
        if (callback != null) {
            callback.onDownloadComplete(result);
        }
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
