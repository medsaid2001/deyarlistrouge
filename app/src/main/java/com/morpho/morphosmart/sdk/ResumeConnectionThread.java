package com.morpho.morphosmart.sdk;
import android.content.Context;

import com.morpho.android.usb.USBManager;

import java.util.Observer;

class ResumeConnectionThread extends Thread {
    private static final int DEFAULT_REBOOT_TIMEOUT = 30000;
    private static final int INFINITE = 65535000;
    private byte[] hostCertificate = null;
    private IMsoSecu msoSecu = null;
    private MorphoDevice device = null;
    private String deviceName = "";
    private boolean deviceOpenedWithFD = false;
    private boolean deviceOpenedWithPipe = false;
    private boolean isOfferedSecurityMode = false;
    private boolean isTunnelingMode = false;
    private Observer observer = null;
    private boolean openDeviceStat = false;
    private IMsoPipe pipeCallBack = null;
    private String pipeIP;
    private int pipePort;
    private int pipeTimeout;
    private int timeOut = DEFAULT_REBOOT_TIMEOUT;

    @Override
    public void run() {
        if (device != null && (!deviceName.isEmpty() || deviceOpenedWithFD)) {
            if (deviceOpenedWithPipe) {
                int result = device.ping();
                if (result != 0) {
                    result = device.openDevicePipe(pipeCallBack, pipeIP, pipePort, deviceName, pipeTimeout);
                }
                if (result == 0) {
                    notifyObserver(true);
                    return;
                }
            }

            long startTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - startTime < timeOut) {
                try {
                    Thread.sleep(1000L);
                    if (deviceOpenedWithPipe) {
                        handleDeviceOpenedWithPipe();
                    } else {
                        handleDeviceNotOpenedWithPipe();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    notifyObserver(false);
                }
            }

            if (deviceOpenedWithPipe) {
                handleDeviceOpenedWithPipe();
            } else {
                handleDeviceOpenedWithFD();
            }

            if (!openDeviceStat) {
                notifyObserver(false);
            }
        } else {
            notifyObserver(false);
        }
    }

    private void handleDeviceOpenedWithPipe() {
        int result = device.ping();
        if (result != 0) {
            result = device.openDevicePipe(pipeCallBack, pipeIP, pipePort, deviceName, pipeTimeout);
        }
        if (result == 0) {
            notifyObserver(true);
        } else {
            notifyObserver(false);
        }
    }

    private void handleDeviceNotOpenedWithPipe() throws InterruptedException {
        int result = USBManager.getInstance().scanDevice();
        if (result == 1) {
            USBManager usbManager = USBManager.getInstance();
            usbManager.initialize((Context) USBManager.context, USBManager.ACTION_USB_PERMISSION);
        }
    }

    private void handleDeviceOpenedWithFD() {
        if (deviceOpenedWithFD) {
            try {
                int result = USBManager.getInstance().IsUsbDaemonStarted();
                if (result != 1) {
                    int[] vendorId = {-1};
                    int[] productId = {-1};
                    int[] busNumber = {-1};

                    result = USBManager.getInstance().getDeviceInformations(deviceName, vendorId, productId, busNumber);
                    if (busNumber[0] != -1) {
                        result = device.openUsbDeviceFD(vendorId[0], productId[0], busNumber[0], 0);
                    }
                } else {
                    result = device.openUsbDevice(deviceName, 0);
                    if (result == 0 && isOfferedSecurityMode) {
                        result = device.offeredSecuOpen(msoSecu);
                    }
                    if (result == 0 && isTunnelingMode) {
                        result = device.tunnelingOpen(msoSecu, hostCertificate);
                    }
                }
                if (result == 0) {
                    openDeviceStat = true;
                    notifyObserver(true);
                } else {
                    Thread.sleep(1000L);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                notifyObserver(false);
            }
        }
    }

    private void notifyObserver(boolean status) {
        if (observer != null) {
            observer.update(null, status);
        }
    }

    public void setDevice(MorphoDevice device) {
        this.device = device;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setDeviceOpenWithFD(boolean deviceOpenedWithFD) {
        this.deviceOpenedWithFD = deviceOpenedWithFD;
    }

    public void setDeviceOpenWithPipe(boolean deviceOpenedWithPipe) {
        this.deviceOpenedWithPipe = deviceOpenedWithPipe;
    }

    public void setObserver(Observer observer) {
        this.observer = observer;
    }

    public void setPipeParameters(IMsoPipe pipeCallBack, String pipeIP, int pipePort, int pipeTimeout) {
        this.pipeCallBack = pipeCallBack;
        this.pipeIP = pipeIP;
        this.pipePort = pipePort;
        this.pipeTimeout = pipeTimeout;
    }

    public void setSecurityParameters(boolean isTunnelingMode, boolean isOfferedSecurityMode, IMsoSecu msoSecu, byte[] hostCertificate) {
        this.isTunnelingMode = isTunnelingMode;
        this.isOfferedSecurityMode = isOfferedSecurityMode;
        this.msoSecu = msoSecu;
        this.hostCertificate = hostCertificate;
    }

    public void setTimeOut(int timeOutInSeconds) {
        if (timeOutInSeconds > 0) {
            int newTimeOut = timeOutInSeconds * 1000;
            this.timeOut = Math.max(newTimeOut, DEFAULT_REBOOT_TIMEOUT);
        }
    }
}
