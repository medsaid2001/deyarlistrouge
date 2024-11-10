package mr.gov.listerouge.interfaces;

import mr.gov.listerouge.models.DeviceInfo;

public interface WaitingCallback {
    public void onSuccessRegister(int code ,String response);
    public void onSuccessCheck(int code ,String response);
    public void onFailureCheck(String message);
    public void onFailureRegister(String message);
    public void newRegister(DeviceInfo deviceInfo);
}
