package mr.gov.listerouge.models;

import java.util.Arrays;

public class DeviceInfo {
    private String deviceSn;
    private String nni;
    private String profil;
    private Location location;
    private String deviceBrand;
    private String structure;
    private String deviceManufacturer;
    private String deviceModel;
    private String deviceName;
    private String deviceSystemName;
    private String deviceCarrier;
    private String deviceSystemVersion;
    private String appName;
    private String phoneNumber;
    // Getters and setters

    public String getDeviceSn() {
        return deviceSn;
    }

    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn;
    }

    public String getNni() {
        return nni;
    }

    public void setNni(String nni) {
        this.nni = nni;
    }

    public String getProfil() {
        return profil;
    }

    public void setProfil(String profil) {
        this.profil = profil;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getDeviceBrand() {
        return deviceBrand;
    }

    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand;
    }

    public String getDeviceManufacturer() {
        return deviceManufacturer;
    }

    public void setDeviceManufacturer(String deviceManufacturer) {
        this.deviceManufacturer = deviceManufacturer;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceSystemName() {
        return deviceSystemName;
    }

    public void setDeviceSystemName(String deviceSystemName) {
        this.deviceSystemName = deviceSystemName;
    }

    public String getDeviceCarrier() {
        return deviceCarrier;
    }

    public void setDeviceCarrier(String deviceCarrier) {
        this.deviceCarrier = deviceCarrier;
    }

    public String getDeviceSystemVersion() {
        return deviceSystemVersion;
    }

    public void setDeviceSystemVersion(String deviceSystemVersion) {
        this.deviceSystemVersion = deviceSystemVersion;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    // Inner classes for location details
    public static class Location {
        private String type;
        private double[] coordinates;

        // Getters and setters

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public double[] getCoordinates() {
            return this.coordinates;
        }

        public void setCoordinates( double[] coordinates) {
            this.coordinates = coordinates;
        }

        @Override
        public String toString() {
            return "Location{" +
                    "type='" + type + '\'' +
                    coordinates +
                    '}';
        }
    }

    public static class Coordinates {
        private double[] coordinates;

        // Getters and setters

        public double[] getCoordinates() {
            return new double[]{coordinates[0],coordinates[1]};
        }

        public void setCoordinates(double[] coordinates) {
            this.coordinates = coordinates;
        }

        @Override
        public String toString() {
            return  Arrays.toString(coordinates)    ;
        }
    }

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "deviceSn='" + deviceSn + '\'' +
                ", nni='" + nni + '\'' +
                ", profil='" + profil + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", location=" + location +
                ", deviceBrand='" + deviceBrand + '\'' +
                ", deviceManufacturer='" + deviceManufacturer + '\'' +
                ", deviceModel='" + deviceModel + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", deviceSystemName='" + deviceSystemName + '\'' +
                ", deviceCarrier='" + deviceCarrier + '\'' +
                ", deviceSystemVersion='" + deviceSystemVersion + '\'' +
                '}';
    }
}
