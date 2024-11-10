package com.morpho.android.usb;

import android.os.Parcel;
import android.os.Parcelable;

import com.facebook.shimmer.BuildConfig;


public class USBDeviceAttributes implements Parcelable {
    public static Creator<USBDeviceAttributes> CREATOR = new Creator<USBDeviceAttributes>() {
        public USBDeviceAttributes createFromParcel(Parcel parcel) {
            return new USBDeviceAttributes(parcel, (USBDeviceAttributes) null);
        }

        public USBDeviceAttributes[] newArray(int i) {
            return new USBDeviceAttributes[i];
        }
    };
    private int deviceType;
    private final int interfaceNumber;
    private String mFreindlyName;
    private String manufacturer;
    private final String path;
    private String product;
    private final int productId;
    private final int vendorId;

    public int describeContents() {
        return 0;
    }

    public int getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(int i) {
        this.deviceType = i;
    }

    public USBDeviceAttributes(int i, int i2) {
        this.manufacturer = null;
        this.product = null;
        this.mFreindlyName = BuildConfig.FLAVOR;
        this.vendorId = i;
        this.productId = i2;
        this.path = null;
        this.interfaceNumber = 0;
        this.deviceType = -1;
    }

    public USBDeviceAttributes(int i, int i2, int i3) {
        this.manufacturer = null;
        this.product = null;
        this.mFreindlyName = BuildConfig.FLAVOR;
        this.vendorId = i;
        this.productId = i2;
        this.path = null;
        this.interfaceNumber = 0;
        this.deviceType = i3;
    }

    private USBDeviceAttributes(Parcel parcel) {
        this.manufacturer = null;
        this.product = null;
        this.mFreindlyName = BuildConfig.FLAVOR;
        if (parcel != null) {
            this.path = parcel.readString();
            this.vendorId = parcel.readInt();
            this.productId = parcel.readInt();
            this.interfaceNumber = parcel.readInt();
            return;
        }
        this.vendorId = 0;
        this.productId = 0;
        this.path = BuildConfig.FLAVOR;
        this.interfaceNumber = 0;
    }

    /* synthetic */ USBDeviceAttributes(Parcel parcel, USBDeviceAttributes uSBDeviceAttributes) {
        this(parcel);
    }

    public int getVendorId() {
        return this.vendorId;
    }

    public String getProduct() {
        return this.product;
    }

    public int getInterfaceNumber() {
        return this.interfaceNumber;
    }

    public int getProductId() {
        return this.productId;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public String getPath() {
        return this.path;
    }

    public void setProduct(String str) {
        this.product = str;
    }

    public USBDeviceAttributes(String str, int i, int i2, int i3) {
        this.manufacturer = null;
        this.product = null;
        this.mFreindlyName = BuildConfig.FLAVOR;
        this.path = str;
        this.vendorId = i;
        this.productId = i2;
        this.interfaceNumber = i3;
    }

    public void writeToParcel(Parcel parcel, int i) {
        if (parcel != null) {
            parcel.writeString(getPath());
            parcel.writeInt(getInterfaceNumber());
            parcel.writeInt(getProductId());
            parcel.writeInt(getVendorId());
        }
    }

    public String getFreindlyName() {
        return this.mFreindlyName;
    }

    public void setFreindlyName(String str) {
        this.mFreindlyName = str;
    }
}
