package mr.gov.listerouge.models;

public class RedlistRequest {
    private String deviceSn;
    private DeviceInfo.Location position;
    public RedlistRequest(String deviceSn, DeviceInfo.Location position) {
        this.deviceSn = deviceSn;
        this.position = position;
    }
}
