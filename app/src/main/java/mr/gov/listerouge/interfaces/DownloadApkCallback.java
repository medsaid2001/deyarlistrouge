package mr.gov.listerouge.interfaces;

public interface DownloadApkCallback {
    void onDownloadComplete(String result);
    void onProgressUpdate(int progress);
}