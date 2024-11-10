package com.morpho.android.usb;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.facebook.shimmer.BuildConfig;
import com.morpho.morphosmart.sdk.MorphoDevice;


public class USBManager {
    public static String ACTION_USB_PERMISSION = "com.morpho.android.usb.USB_PERMISSION";
    public static final String SOFTWAREID_CBM = "CBM";
    public static final String SOFTWAREID_CBME3 = "CBM-E3";
    public static final String SOFTWAREID_CBMV3 = "CBM-V3";
    public static final String SOFTWAREID_FVP = "MSO FVP";
    public static final String SOFTWAREID_FVP_C = "MSO FVP_C";
    public static final String SOFTWAREID_FVP_CL = "MSO FVP_CL";
    public static final String SOFTWAREID_MASIGMA = "MA SIGMA";
    public static final String SOFTWAREID_MEP = "MEPUSB";
    public static final String SOFTWAREID_MSO100 = "MSO100";
    public static final String SOFTWAREID_MSO1300E3 = "MSO1300-E3";
    public static final String SOFTWAREID_MSO1300V3 = "MSO1300-V3";
    public static final String SOFTWAREID_MSO1350 = "MSO1350";
    public static final String SOFTWAREID_MSO1350E3 = "MSO1350-E3";
    public static final String SOFTWAREID_MSO1350V3 = "MSO1350-V3";
    public static final String SOFTWAREID_MSO300 = "MSO300";
    public static final String SOFTWAREID_MSO350 = "MSO350";
    public static boolean WakeLockEnabled = false;
    public static Context context = null;
    static volatile List<USBDevice> deviceList = null;
    private static USBManager instance = null;
    public static Map<Context, MorphoDevice.ScreenBroadcastReceiver> screenReceiverMap = new HashMap();
    private static final Map<USBDeviceAttributes, String> supportedDevices = new HashMap();
    public static boolean waitReboot = false;
    Queue<MphDevice> devQueue = new LinkedList();
    private List<UsbDevice> devices_pop;
    private UsbDeviceConnection g_usbConnection;

    public native int IsUsbDaemonStarted();

    public native String getUsbDaemonVersion();

    public native void initialize();

    static {
        System.loadLibrary("NativeMorphoSmartSDK_6.36.1.0");
        System.loadLibrary("MSO100");
        System.loadLibrary("usb1.0");
        supportedDevices.put(new USBDeviceAttributes(1947, 35), "MSO100");
        supportedDevices.put(new USBDeviceAttributes(1947, 36), "MSO300");
        supportedDevices.put(new USBDeviceAttributes(1947, 38), "MSO350");
        supportedDevices.put(new USBDeviceAttributes(1947, 71), "CBM");
        supportedDevices.put(new USBDeviceAttributes(1947, 82), "MSO1350");
        supportedDevices.put(new USBDeviceAttributes(8797, 1), "MSO FVP");
        supportedDevices.put(new USBDeviceAttributes(8797, 2), "MSO FVP_C");
        supportedDevices.put(new USBDeviceAttributes(8797, 3), "MSO FVP_CL");
        supportedDevices.put(new USBDeviceAttributes(8797, 7), "MEPUSB");
        supportedDevices.put(new USBDeviceAttributes(8797, 8), "CBM-E3");
        supportedDevices.put(new USBDeviceAttributes(8797, 9), "CBM-V3");
        supportedDevices.put(new USBDeviceAttributes(8797, 10), "MSO1300-E3");
        supportedDevices.put(new USBDeviceAttributes(8797, 11), "MSO1300-V3");
        supportedDevices.put(new USBDeviceAttributes(8797, 12), "MSO1350-E3");
        supportedDevices.put(new USBDeviceAttributes(8797, 13), "MSO1350-V3");
        supportedDevices.put(new USBDeviceAttributes(8797, 14), "MA SIGMA");
    }

    class MphDevice {
        private String devName;
        private UsbDevice usbDevice;
        private int usbEvent;

        public MphDevice() {
            this.devName = null;
            this.usbDevice = null;
            this.usbEvent = 0;
        }

        public MphDevice(String str, UsbDevice usbDevice2, int i) {
            this.devName = str;
            this.usbDevice = usbDevice2;
            this.usbEvent = i;
        }

        public String getDevName() {
            return this.devName;
        }

        public UsbDevice getUsbDevice() {
            return this.usbDevice;
        }

        public int getUsbEvent() {
            return this.usbEvent;
        }
    }

    public static synchronized USBManager getInstance() {
        USBManager uSBManager;
        synchronized (USBManager.class) {
            if (instance == null) {
                instance = new USBManager();
            }
            uSBManager = instance;
        }
        return uSBManager;
    }

    private USBManager() {
    }

    public int initialize(Activity activity, String str) {
        Log.i("MORPHO_USB", "context : " + activity);
        if (activity == null) {
            return -5;
        }
        return initialize(activity.getApplicationContext(), str);
    }

    public void checkWakeLockPermission(Context context2) {
        if (context2.getPackageManager().checkPermission("android.permission.WAKE_LOCK", context2.getPackageName()) != PackageManager.PERMISSION_GRANTED) {
            Log.i("MORPHO_USB", "WAKE_LOCK permission was not granted");
            Toast.makeText(context2.getApplicationContext(), "MorphoSmart SDK, WAKE_LOCK permission was not granted", Toast.LENGTH_LONG).show();
            return;
        }
        Log.i("MORPHO_USB", "WAKE_LOCK permission was granted");
    }

    public static void unRegister(Context context2, MorphoDevice.ScreenBroadcastReceiver screenBroadcastReceiver) {
        for (Context next : screenReceiverMap.keySet()) {
            if (next == context2) {
                try {
                    context2.unregisterReceiver(screenBroadcastReceiver);
                } catch (IllegalArgumentException e) {
                    Log.e("USBManager unregisterReceiver", e.getMessage());
                }
                screenReceiverMap.remove(next);
            }
        }
    }

    public int initialize(Context context2, String str, boolean z) {
        if (context2 == null) {
            return -5;
        }
        checkWakeLockPermission(context2);
        WakeLockEnabled = z;
        return initialize(context2, str);
    }

    public int initialize(Context context2, String str) {
        initialize();
        if (context2 == null) {
            return -5;
        }
        Log.i("MORPHO_USB", "context : " + context2);
        context = context2;
        ACTION_USB_PERMISSION = str;
        if (IsUsbDaemonStarted() == 1) {
            Log.e("MORPHO_USB", "Service started");
            return 0;
        }
        Log.e("MORPHO_USB", "Service not started");
        if (deviceList == null) {
            deviceList = new LinkedList();
        }
        clearDeviceList();
        grantePermission();
        return 0;
    }

    public void closeUsbDeviceConnection() {
        Log.i("USBManager", "closeUsbDeviceConnection : Start");
        if (this.g_usbConnection != null) {
            Log.i("USBManager", "closeUsbDeviceConnection : Closing Connection ... ");
            this.g_usbConnection.close();
            this.g_usbConnection = null;
        }
        Log.i("USBManager", "closeUsbDeviceConnection : End");
    }

    public int getDeviceInformations(String str, int[] iArr, int[] iArr2, int[] iArr3) {
        UsbDeviceConnection openDevice;
        Context context2 = context;
        if (context2 == null) {
            return -1;
        }
        UsbManager usbManager = (UsbManager) context2.getSystemService(Context.USB_SERVICE);
        for (UsbDevice next : usbManager.getDeviceList().values()) {
            if (isSupported(new USBDeviceAttributes(next.getVendorId(), next.getProductId()))) {
                boolean hasPermission = usbManager.hasPermission(next);
                Log.i("MORPHO_USB", "getDeviceInformations : serialNumber " + str + " hasPermission = " + hasPermission);
                if (hasPermission && (openDevice = usbManager.openDevice(next)) != null) {
                    if (openDevice.getSerial().equalsIgnoreCase(str)) {
                        this.g_usbConnection = openDevice;
                        int fileDescriptor = openDevice.getFileDescriptor();
                        String[] split = next.getDeviceName().split("/");
                        if (split.length >= 5) {
                            int parseInt = Integer.parseInt(split[4].toString());
                            int parseInt2 = Integer.parseInt(split[5].toString());
                            iArr[0] = parseInt;
                            iArr2[0] = parseInt2;
                            iArr3[0] = fileDescriptor;
                            return 0;
                        }
                    } else {
                        openDevice.close();
                    }
                }
            }
        }
        return -3;
    }

    public int getFileDescriptor(int i, int i2) {
        Context context2 = context;
        if (context2 == null) {
            return -1;
        }
        UsbManager usbManager = (UsbManager) context2.getSystemService(Context.USB_SERVICE);
        for (UsbDevice next : usbManager.getDeviceList().values()) {
            Log.i("getFileDescriptor", "getDeviceId : " + next.getDeviceId());
            if (isSupported(new USBDeviceAttributes(next.getVendorId(), next.getProductId()))) {
                if (!String.format("/dev/bus/usb/%03d/%03d", new Object[]{Integer.valueOf(i), Integer.valueOf(i2)}).equalsIgnoreCase(next.getDeviceName())) {
                    continue;
                } else if (!usbManager.hasPermission(next)) {
                    return -2;
                } else {
                    UsbDeviceConnection openDevice = usbManager.openDevice(next);
                    if (openDevice != null) {
                        return openDevice.getFileDescriptor();
                    }
                }
            }
        }
        return -3;
    }

    private void clearDeviceList() {
        try {
            if (deviceList != null) {
                for (int i = 0; i < deviceList.size(); i++) {
                    deviceList.get(i).close();
                }
            }
        } catch (Exception e) {
            Log.e("USBManager clearDeviceList", e.getMessage());
        }
        if (deviceList != null) {
            deviceList.clear();
        }
    }

    private int grantePermission() {
        Context context2 = context;
        if (context2 != null) {
            UsbManager usbManager = (UsbManager) context2.getSystemService(Context.USB_SERVICE);
            for (UsbDevice next : usbManager.getDeviceList().values()) {
                if (isSupported(new USBDeviceAttributes(next.getVendorId(), next.getProductId())) && !usbManager.hasPermission(next)) {
                    Log.i("MORPHO_USB", "Request permission for using the device");
                    usbManager.requestPermission(next, PendingIntent.getBroadcast(context2, 0, new Intent(ACTION_USB_PERMISSION), PendingIntent.FLAG_IMMUTABLE));
                }
            }
        }
        return 0;
    }

    public boolean isDevicesHasPermission() {
        if (IsUsbDaemonStarted() == 1) {
            return true;
        }
        Context context2 = context;
        if (context2 == null) {
            return false;
        }
        UsbManager usbManager = (UsbManager) context2.getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> deviceList2 = usbManager.getDeviceList();
        if (deviceList2.isEmpty()) {
            return true;
        }
        boolean z = true;
        for (UsbDevice next : deviceList2.values()) {
            if (isSupported(new USBDeviceAttributes(next.getDeviceName(), next.getVendorId(), next.getProductId(), 1)) && !usbManager.hasPermission(next)) {
                z = false;
            }
        }
        return z;
    }

    public int scanDevice() {
        if (IsUsbDaemonStarted() == 1) {
            Log.e("MORPHO_USB", "Service started");
            return 1;
        }
        Context context2 = context;
        if (context2 == null) {
            return 0;
        }
        UsbManager usbManager = (UsbManager) context2.getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> deviceList2 = usbManager.getDeviceList();
        Log.i("MORPHO_USB", "usbDeviceList.size() = " + deviceList2.size());
        for (UsbDevice next : usbManager.getDeviceList().values()) {
            if (isSupported(new USBDeviceAttributes(next.getDeviceName(), next.getVendorId(), next.getProductId(), 1))) {
                Log.i("MORPHO_USB", "Supported device : Vendor Id = " + next.getVendorId() + ", product Id = " + next.getProductId());
                return 1;
            }
        }
        return 0;
    }

    public synchronized String getDeviceModel(USBDeviceAttributes uSBDeviceAttributes) {
        if (uSBDeviceAttributes == null) {
            return BuildConfig.FLAVOR;
        }
        for (Map.Entry next : supportedDevices.entrySet()) {
            USBDeviceAttributes uSBDeviceAttributes2 = (USBDeviceAttributes) next.getKey();
            if (uSBDeviceAttributes2.getVendorId() == uSBDeviceAttributes.getVendorId() && uSBDeviceAttributes2.getProductId() == uSBDeviceAttributes.getProductId()) {
                return (String) next.getValue();
            }
        }
        return BuildConfig.FLAVOR;
    }

    public synchronized boolean isSupported(USBDeviceAttributes uSBDeviceAttributes) {
        if (uSBDeviceAttributes == null) {
            return false;
        }
        for (USBDeviceAttributes next : supportedDevices.keySet()) {
            if (next.getVendorId() == uSBDeviceAttributes.getVendorId() && next.getProductId() == uSBDeviceAttributes.getProductId()) {
                uSBDeviceAttributes.setDeviceType(next.getDeviceType());
                return true;
            }
        }
        return false;
    }

    private synchronized int addGrantedDevice(UsbDevice usbDevice, int i) {
        int i2;
        if (usbDevice == null) {
            return -6;
        }
        USBDevice uSBDevice = new USBDevice(new USBDeviceAttributes(usbDevice.getDeviceName(), usbDevice.getVendorId(), usbDevice.getProductId(), i), (UsbManager) context.getSystemService(Context.USB_SERVICE), usbDevice);
        try {
            i2 = uSBDevice.open();
            if (i2 == 0) {
                if (deviceList == null) {
                    deviceList = new LinkedList();
                }
                deviceList.add(uSBDevice);
            }
        } catch (Exception e) {
            Log.e("MORPHO_USB", e.getMessage());
            i2 = -6;
        }
        return i2;
    }

    public synchronized USBDeviceAttributes[] enumerate() {
        if (context == null) {
            return null;
        }
        UsbManager usbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> deviceList2 = usbManager.getDeviceList();
        if (deviceList2.isEmpty()) {
            return null;
        }
        USBDeviceAttributes[] uSBDeviceAttributesArr = new USBDeviceAttributes[deviceList2.size()];
        clearDeviceList();
        int i = -1;
        int i2 = 0;
        for (UsbDevice next : deviceList2.values()) {
            int interfaceCount = next.getInterfaceCount();
            int i3 = 0;
            while (true) {
                if (i3 >= interfaceCount) {
                    break;
                } else if ((next.getInterface(i3).getInterfaceClass() == 2 || next.getInterface(i3).getInterfaceClass() == 10) && next.getInterface(i3).getEndpointCount() == 2) {
                    i = i3;
                    break;
                } else {
                    i3++;
                }
            }
            if (i != -1) {
                USBDeviceAttributes uSBDeviceAttributes = new USBDeviceAttributes(next.getDeviceName(), next.getVendorId(), next.getProductId(), i);
                if (isSupported(uSBDeviceAttributes)) {
                    if (!usbManager.hasPermission(next)) {
                        Log.i("MORPHO_USB", "no permission for device " + next.getDeviceName());
                    } else if (addGrantedDevice(next, i) == 0) {
                        uSBDeviceAttributesArr[i2] = uSBDeviceAttributes;
                        uSBDeviceAttributes.setFreindlyName(getDeviceModel(uSBDeviceAttributes));
                        i2++;
                    }
                    i = -1;
                }
            }
        }
        return uSBDeviceAttributesArr;
    }

    public synchronized void listDevices() throws Exception {
        USBDeviceAttributes[] enumerate = enumerate();
        if (!(deviceList == null || enumerate == null)) {
            for (int i = 0; i < deviceList.size(); i++) {
                deviceList.get(i).CreateInterface(enumerate[i].getInterfaceNumber());
            }
        }
    }

    public synchronized int getNbDevices() throws Exception {
        if (deviceList == null) {
            return 0;
        }
        return deviceList.size();
    }

    public USBDevice getDevice(int i) {
        if (deviceList != null && deviceList.size() > i) {
            return deviceList.get(i);
        }
        return null;
    }
}
