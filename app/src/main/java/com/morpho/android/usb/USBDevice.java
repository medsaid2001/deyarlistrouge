package com.morpho.android.usb;

import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.util.Log;

import java.io.UnsupportedEncodingException;



public class USBDevice {
    public static UsbManager mManager;
    public USBDeviceAttributes mAttributes = null;
    public UsbDeviceConnection mConnection = null;
    public UsbDevice mDevice = null;
    private byte[] mDeviceDescriptor = null;
    public UsbEndpoint mEndpointIn = null;
    public UsbEndpoint mEndpointOut = null;
    public UsbInterface mInterface = null;
    public int mMaxPacketInSize = 0;
    public int mMaxPacketOutSize = 0;

    public synchronized boolean hasPermission() {
        if (this.mDevice != null) {
            if (mManager != null) {
                return mManager.hasPermission(this.mDevice);
            }
        }
        return false;
    }

    public synchronized int open() throws Exception {
        if (this.mDevice == null || mManager == null) {
            throw new Exception("Failuire to open device: either usb manager or connection null");
        }
        this.mInterface = this.mDevice.getInterface(getAttributes().getInterfaceNumber());
        this.mConnection = mManager.openDevice(this.mDevice);
        if (this.mConnection != null) {
            this.mConnection.claimInterface(this.mInterface, true);
            Log.d("MORPHO_USB", "device opened !");
            return 0;
        }
        Log.d("MORPHO_USB", "device not connected !");
        return -6;
    }

    public synchronized UsbInterface CreateInterface(int i) {
        return this.mDevice.getInterface(i);
    }

    public USBDevice(USBDeviceAttributes uSBDeviceAttributes, UsbManager usbManager, UsbDevice usbDevice) {
        this.mDevice = usbDevice;
        this.mAttributes = uSBDeviceAttributes;
        mManager = usbManager;
    }

    public synchronized String getProductString() throws Exception {
        if (getAttributes().getProduct() == null) {
            byte[] bArr = new byte[64];
            getStringDescriptor(bArr, getDeviceDescriptor()[15]);
            getAttributes().setProduct(convertDescriptorUnicodeLEToString(bArr));
        }
        return getAttributes().getProduct();
    }

    private synchronized String convertDescriptorUnicodeLEToString(byte[] bArr) throws UnsupportedEncodingException {
        byte[] bArr2;
        try {
            int i = bArr[0] - 2;
            bArr2 = new byte[i];
            System.arraycopy(bArr, 2, bArr2, 0, i);
        } catch (Exception e) {
            Log.e("USBDevice.convertDescriptorUnicodeLEToString", e.getMessage());
            return "not available";
        }
        return new String(bArr2, "UTF-16LE");
    }

    public synchronized int findEndPoint() {
        if (this.mInterface == null) {
            return -3;
        }
        if (this.mInterface.getEndpointCount() != 2) {
            return -2;
        }
        UsbEndpoint endpoint = this.mInterface.getEndpoint(0);
        UsbEndpoint endpoint2 = this.mInterface.getEndpoint(1);
        if ((endpoint.getAttributes() & 3) == 2 && (endpoint2.getAttributes() & 3) == 2) {
            if ((endpoint.getAddress() & 128) != 0 && (endpoint2.getAddress() & 128) == 0) {
                this.mEndpointOut = endpoint2;
                this.mEndpointIn = endpoint;
                this.mMaxPacketInSize = endpoint.getMaxPacketSize();
                this.mMaxPacketOutSize = endpoint2.getMaxPacketSize();
                return 0;
            } else if ((endpoint.getAddress() & 128) == 0 && (endpoint2.getAddress() & 128) != 0) {
                this.mEndpointOut = endpoint;
                this.mEndpointIn = endpoint2;
                this.mMaxPacketInSize = endpoint2.getMaxPacketSize();
                this.mMaxPacketOutSize = endpoint.getMaxPacketSize();
                return 0;
            }
        }
        return -1;
    }

    public synchronized USBDeviceAttributes getAttributes() {
        return this.mAttributes;
    }

    private synchronized int getStringDescriptor(byte[] bArr, int i) throws Exception {
        return getDescriptor(bArr, 768, i);
    }

    private synchronized byte[] getDeviceDescriptor() throws Exception {
        if (this.mDeviceDescriptor == null) {
            this.mDeviceDescriptor = new byte[18];
            getDescriptor(this.mDeviceDescriptor, 256, 1);
        }
        return this.mDeviceDescriptor;
    }

    private synchronized int getDescriptor(byte[] bArr, int i, int i2) throws Exception {
        int controlTransfer;
        if (bArr != null) {
            if (!(this.mConnection == null || this.mInterface == null)) {
                if (this.mConnection.claimInterface(this.mInterface, true)) {
                    int length = bArr.length;
                    byte[] bArr2 = new byte[bArr.length];
                    controlTransfer = this.mConnection.controlTransfer(128, 6, i | i2, USBConstants.USB_DEVICE_DESCRIPTOR_LANGUAGE_ENGLISH, bArr2, length, 1000);
                    if (controlTransfer >= 0) {
                        System.arraycopy(bArr2, 0, bArr, 0, bArr2.length);
                    } else {
                        throw new Exception("getDescriptor operation is unsuccessful. Descriptor index=" + i2 + "Descriptor type=" + i);
                    }
                } else {
                    throw new Exception("Could not claim the USB device interface");
                }
            }
        }
        throw new Exception("Error get the feature report from the device: either connection or interface null");
    }

    public synchronized int close() {
        try {
            if (this.mConnection != null) {
                this.mConnection.releaseInterface(this.mInterface);
                this.mConnection.close();
                this.mInterface = null;
                this.mConnection = null;
            }
        } catch (Exception e) {
            Log.e("USBDevice close", e.getMessage());
            return -5;
        }
        return 0;
    }

    public synchronized UsbInterface claimInterface() {
        return this.mInterface;
    }

    public synchronized int write(byte[] bArr, int i, int i2) {
        if (this.mConnection == null) {
            return -6;
        }
        this.mConnection.claimInterface(this.mInterface, true);
        long currentTimeMillis = System.currentTimeMillis() + ((long) i2);
        int i3 = 0;
        while (true) {
            if (i3 >= i) {
                break;
            }
            int bulkTransfer = this.mConnection.bulkTransfer(this.mEndpointOut, bArr, i3, i - i3, i2);
            if (bulkTransfer <= 0) {
                i3 = bulkTransfer;
                break;
            } else if (System.currentTimeMillis() > currentTimeMillis) {
                i3 = -391;
                break;
            } else {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i3 += bulkTransfer;
            }
        }
        if (i3 > 0 && i > 0 && i % this.mMaxPacketOutSize == 0) {
            Log.d("MORPHO_USB", "Send ZLP ..");
            i3 = this.mConnection.bulkTransfer(this.mEndpointOut, bArr, 0, 1);
            if (i3 < 0) {
                this.mConnection.releaseInterface(this.mInterface);
                return -7;
            }
        }
        this.mConnection.releaseInterface(this.mInterface);
        return i3;
    }

    public synchronized int read(byte[] bArr, int i, int i2) {
        if (this.mConnection == null) {
            return -6;
        }
        this.mConnection.claimInterface(this.mInterface, true);
        if (this.mEndpointIn == null) {
            Log.e("MORPHO_USB", "null read endpoint !");
        }
        int bulkTransfer = this.mConnection.bulkTransfer(this.mEndpointIn, bArr, i, i2);
        this.mConnection.releaseInterface(this.mInterface);
        return bulkTransfer;
    }

    public synchronized int getStringSimple(byte[] bArr, int i) throws Exception {
        try {
        } catch (Exception e) {
            Log.e("USBDevice getStringSimple", e.getMessage());
            return -4;
        }
        return getStringDescriptor(bArr, i);
    }

    public synchronized String getSerialNumberString() {
        if (this.mConnection == null) {
            return "Serial not available";
        }
        return this.mConnection.getSerial();
    }

    public synchronized int getMaxPacketInSize() {
        return this.mMaxPacketInSize;
    }

    public int getMaxPacketOutSize() {
        return this.mMaxPacketOutSize;
    }
}
