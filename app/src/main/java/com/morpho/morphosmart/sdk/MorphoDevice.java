package com.morpho.morphosmart.sdk;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.runtime.internal.Conversions;
import org.aspectj.runtime.reflect.Factory;

import java.util.ArrayList;
import java.util.Observer;

import com.facebook.shimmer.BuildConfig;
import com.morpho.android.annotation.WakeLockAspect;
import com.morpho.android.annotation.WakeLockabble;
import com.morpho.android.usb.USBManager;


@SuppressLint({"UseValueOf"})
public class MorphoDevice implements Cloneable {
    public static String ACTION_SCREEN_RECEIVER = "com.morpho.android.local.RECEIVER";

    public static final int CONFIG_KEY_USER_TAG = 4627;
    public static final int CONFIG_SENSOR_WIN_POSITION_TAG = 3600;
    public static final int CONFIG_UI_CONFIG_TAG = 5136;
    public static final int CONFIG_UI_RESET_TAG = 5137;
    public static final int CONFIG_USB_ENABLE_FULL_SPEED_TAG = 1810;
    private static /* synthetic */ JoinPoint.StaticPart ajctjp_0 = null;
    private static   /* synthetic */ JoinPoint.StaticPart ajctjp_1 = null;
    private static   /* synthetic */ JoinPoint.StaticPart ajctjp_10 = null;
    private static  JoinPoint.StaticPart ajctjp_11 = null;
    private static  JoinPoint.StaticPart ajctjp_12 = null;
    private static  JoinPoint.StaticPart ajctjp_13 = null;
    private static  JoinPoint.StaticPart ajctjp_2 = null;
    private static  JoinPoint.StaticPart ajctjp_3 = null;
    private static  JoinPoint.StaticPart ajctjp_4 = null;
    private static  JoinPoint.StaticPart ajctjp_5 = null;
    private static  JoinPoint.StaticPart ajctjp_6 = null;
    private static  JoinPoint.StaticPart ajctjp_7 = null;
    private static  JoinPoint.StaticPart ajctjp_8 = null;
    private static  JoinPoint.StaticPart ajctjp_9 = null;
    private static MorphoDeviceNative morphoDeviceNative = new MorphoDeviceNative();
    private byte[] _hostCertificate;
    private IMsoSecu _msoSecu;
    private ResumeConnectionThread _reboot = null;
    private Context context = null;
    protected boolean cppMemOwn = false;
    private int deviceAddress = -1;
    private int deviceBus = -1;
    private int deviceFD = -1;
    private boolean deviceOpenedWithFD = false;
    private boolean deviceOpenedWithPipe = false;
    private String deviceSN = BuildConfig.FLAVOR;
    private boolean isOfferedSecurityMode = false;
    private boolean isTunnelingMode = false;
    private Long morphoDevicePointerCPP = new Long(0);
    private IMsoPipe pipeCallBack = null;
    private String pipeIP;
    private int pipePort;
    private int pipeTimeout;
    private ScreenBroadcastReceiver screenReceiver = null;

    private static /* synthetic */ void ajcpreClinit() {
        Factory factory = new Factory("MorphoDevice.java", MorphoDevice.class);
        ajctjp_0 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, (Signature) factory.makeMethodSig("1", "openDevicePipe", "com.morpho.morphosmart.sdk.MorphoDevice", "com.morpho.morphosmart.sdk.IMsoPipe:java.lang.String:int:java.lang.String:int", "callBack:ip:port:serialNumber:timeout", BuildConfig.FLAVOR, "int"), 120);
        ajctjp_1 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, (Signature) factory.makeMethodSig("1", "capture", "com.morpho.morphosmart.sdk.MorphoDevice", "int:int:int:int:com.morpho.morphosmart.sdk.TemplateType:com.morpho.morphosmart.sdk.TemplateFVPType:int:com.morpho.morphosmart.sdk.EnrollmentType:com.morpho.morphosmart.sdk.LatentDetection:com.morpho.morphosmart.sdk.Coder:int:com.morpho.morphosmart.sdk.CompressionAlgorithm:int:com.morpho.morphosmart.sdk.TemplateList:int:java.util.Observer", "timeout:acquisitionThreshold:advancedSecurityLevelsRequired:fingerNumber:templateType:templateFVPType:maxSizeTemplate:enrollType:latentDetection:coderChoice:detectModeChoice:compressAlgo:compressRate:templateList:callbackCmd:callback", BuildConfig.FLAVOR, "int"), 402);
        ajctjp_10 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, (Signature) factory.makeMethodSig("1", "comReceive", "com.morpho.morphosmart.sdk.MorphoDevice", "int", "timeOut", BuildConfig.FLAVOR, "[B"), 1670);
        ajctjp_11 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, (Signature) factory.makeMethodSig("1", "loadKs", "com.morpho.morphosmart.sdk.MorphoDevice", "[B", "key", BuildConfig.FLAVOR, "int"), 1689);
        ajctjp_12 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, (Signature) factory.makeMethodSig("1", "loadMocKey", "com.morpho.morphosmart.sdk.MorphoDevice", "[B:[B:[B", "key_enc_Ciffered_by_Certificate:key_enc_Ciffered_by_Certificate_Signature:hostCertificate", BuildConfig.FLAVOR, "int"), 1782);
        ajctjp_13 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, (Signature) factory.makeMethodSig("1", "loadMocKey", "com.morpho.morphosmart.sdk.MorphoDevice", "[B", "key_enc_Ciffered_by_KencTrans", BuildConfig.FLAVOR, "int"), 1808);
        ajctjp_2 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, (Signature) factory.makeMethodSig("1", "capture", "com.morpho.morphosmart.sdk.MorphoDevice", "int:int:int:int:com.morpho.morphosmart.sdk.TemplateType:com.morpho.morphosmart.sdk.TemplateFVPType:int:com.morpho.morphosmart.sdk.EnrollmentType:com.morpho.morphosmart.sdk.LatentDetection:com.morpho.morphosmart.sdk.Coder:int:com.morpho.morphosmart.sdk.TemplateList:int:java.util.Observer", "timeout:acquisitionThreshold:advancedSecurityLevelsRequired:fingerNumber:templateType:templateFVPType:maxSizeTemplate:enrollType:latentDetection:coderChoice:detectModeChoice:templateList:callbackCmd:callback", BuildConfig.FLAVOR, "int"), 425);
        ajctjp_3 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, (Signature) factory.makeMethodSig("1", "openUsbDevice", "com.morpho.morphosmart.sdk.MorphoDevice", "java.lang.String:int", "sensorName:timeOut", BuildConfig.FLAVOR, "int"), 613);
        ajctjp_4 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, (Signature) factory.makeMethodSig("1", "openUsbDeviceFD", "com.morpho.morphosmart.sdk.MorphoDevice", "int:int:int:int", "bus:address:fd:timeOut", BuildConfig.FLAVOR, "int"), 647);
        ajctjp_5 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, (Signature) factory.makeMethodSig("1", "setConfigParam", "com.morpho.morphosmart.sdk.MorphoDevice", "int:[B", "tag:paramValue", BuildConfig.FLAVOR, "int"), 684);
        ajctjp_6 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, (Signature) factory.makeMethodSig("1", "getImage", "com.morpho.morphosmart.sdk.MorphoDevice", "int:int:com.morpho.morphosmart.sdk.CompressionAlgorithm:int:int:com.morpho.morphosmart.sdk.LatentDetection:com.morpho.morphosmart.sdk.MorphoImage:int:java.util.Observer", "timeOut:acquisitionThreshold:compressAlgo:compressRate:detectModeChoice:latentDetection:morphoImage:callbackCmd:callback", BuildConfig.FLAVOR, "int"), 1099);
        ajctjp_7 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, (Signature) factory.makeMethodSig("1", "verify", "com.morpho.morphosmart.sdk.MorphoDevice", "int:int:com.morpho.morphosmart.sdk.Coder:int:int:com.morpho.morphosmart.sdk.TemplateList:int:java.util.Observer:com.morpho.morphosmart.sdk.ResultMatching", "timeOut:far:coder:detectModeChoice:matchingStrategy:templateList:callbackCmd:callback:resultMatching", BuildConfig.FLAVOR, "int"), 1538);
        ajctjp_8 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, (Signature) factory.makeMethodSig("1", "verifyMatch", "com.morpho.morphosmart.sdk.MorphoDevice", "int:com.morpho.morphosmart.sdk.TemplateList:com.morpho.morphosmart.sdk.TemplateList:com.morpho.morphosmart.sdk.CustomInteger", "far:templateListSearch:templateListReference:matchingScore", BuildConfig.FLAVOR, "int"), 1581);
        ajctjp_9 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, (Signature) factory.makeMethodSig("1", "comSend", "com.morpho.morphosmart.sdk.MorphoDevice", "int:[B", "timeOut:data", BuildConfig.FLAVOR, "int"), 1648);
    }

    @WakeLockabble
    public int capture(int i, int i2, int i3, int i4, TemplateType templateType, TemplateFVPType templateFVPType, int i5, EnrollmentType enrollmentType, LatentDetection latentDetection, Coder coder, int i6, CompressionAlgorithm compressionAlgorithm, int i7, TemplateList templateList, int i8, Observer observer) {
        JoinPoint makeJP = Factory.makeJP(ajctjp_1, (Object) this, (Object) this, new Object[]{Conversions.intObject(i), Conversions.intObject(i2), Conversions.intObject(i3), Conversions.intObject(i4), templateType, templateFVPType, Conversions.intObject(i5), enrollmentType, latentDetection, coder, Conversions.intObject(i6), compressionAlgorithm, Conversions.intObject(i7), templateList, Conversions.intObject(i8), observer});
        return Conversions.intValue(capture_aroundBody3$advice(this, i, i2, i3, i4, templateType, templateFVPType, i5, enrollmentType, latentDetection, coder, i6, compressionAlgorithm, i7, templateList, i8, observer, makeJP, WakeLockAspect.aspectOf(), (ProceedingJoinPoint) makeJP));
    }

    @WakeLockabble
    public int capture(int i, int i2, int i3, int i4, TemplateType templateType, TemplateFVPType templateFVPType, int i5, EnrollmentType enrollmentType, LatentDetection latentDetection, Coder coder, int i6, TemplateList templateList, int i7, Observer observer) {
        JoinPoint makeJP = Factory.makeJP(ajctjp_2, (Object) this, (Object) this, new Object[]{Conversions.intObject(i), Conversions.intObject(i2), Conversions.intObject(i3), Conversions.intObject(i4), templateType, templateFVPType, Conversions.intObject(i5), enrollmentType, latentDetection, coder, Conversions.intObject(i6), templateList, Conversions.intObject(i7), observer});
        return Conversions.intValue(capture_aroundBody5$advice(this, i, i2, i3, i4, templateType, templateFVPType, i5, enrollmentType, latentDetection, coder, i6, templateList, i7, observer, makeJP, WakeLockAspect.aspectOf(), (ProceedingJoinPoint) makeJP));
    }

    @WakeLockabble
    public byte[] comReceive(int i) {
        JoinPoint makeJP = Factory.makeJP(ajctjp_10, (Object) this, (Object) this, Conversions.intObject(i));
        return (byte[]) comReceive_aroundBody21$advice(this, i, makeJP, WakeLockAspect.aspectOf(), (ProceedingJoinPoint) makeJP);
    }

    @WakeLockabble
    public int comSend(int i, byte[] bArr) {
        JoinPoint makeJP = Factory.makeJP(ajctjp_9, this, this, Conversions.intObject(i), bArr);
        return Conversions.intValue(comSend_aroundBody19$advice(this, i, bArr, makeJP, WakeLockAspect.aspectOf(), (ProceedingJoinPoint) makeJP));
    }

    @WakeLockabble
    public int getImage(int i, int i2, CompressionAlgorithm compressionAlgorithm, int i3, int i4, LatentDetection latentDetection, MorphoImage morphoImage, int i5, Observer observer) {
        JoinPoint makeJP = Factory.makeJP(ajctjp_6, (Object) this, (Object) this, new Object[]{Conversions.intObject(i), Conversions.intObject(i2), compressionAlgorithm, Conversions.intObject(i3), Conversions.intObject(i4), latentDetection, morphoImage, Conversions.intObject(i5), observer});
        return Conversions.intValue(getImage_aroundBody13$advice(this, i, i2, compressionAlgorithm, i3, i4, latentDetection, morphoImage, i5, observer, makeJP, WakeLockAspect.aspectOf(), (ProceedingJoinPoint) makeJP));
    }

    public String getKCV(int i) {
        return null;
    }

    @WakeLockabble
    public int loadKs(byte[] bArr) {
        JoinPoint makeJP = Factory.makeJP(ajctjp_11, (Object) this, (Object) this, (Object) bArr);
        return Conversions.intValue(loadKs_aroundBody23$advice(this, bArr, makeJP, WakeLockAspect.aspectOf(), (ProceedingJoinPoint) makeJP));
    }

    @WakeLockabble
    public int loadMocKey(byte[] bArr) {
        JoinPoint makeJP = Factory.makeJP(ajctjp_13, (Object) this, (Object) this, (Object) bArr);
        return Conversions.intValue(loadMocKey_aroundBody27$advice(this, bArr, makeJP, WakeLockAspect.aspectOf(), (ProceedingJoinPoint) makeJP));
    }

    @WakeLockabble
    public int loadMocKey(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        JoinPoint makeJP = Factory.makeJP(ajctjp_12, (Object) this, (Object) this, new Object[]{bArr, bArr2, bArr3});
        return Conversions.intValue(loadMocKey_aroundBody25$advice(this, bArr, bArr2, bArr3, makeJP, WakeLockAspect.aspectOf(), (ProceedingJoinPoint) makeJP));
    }

    @WakeLockabble
    public int openDevicePipe(IMsoPipe iMsoPipe, String str, int i, String str2, int i2) {
        JoinPoint makeJP = Factory.makeJP(ajctjp_0, (Object) this, (Object) this, new Object[]{iMsoPipe, str, Conversions.intObject(i), str2, Conversions.intObject(i2)});
        return Conversions.intValue(openDevicePipe_aroundBody1$advice(this, iMsoPipe, str, i, str2, i2, makeJP, WakeLockAspect.aspectOf(), (ProceedingJoinPoint) makeJP));
    }

    @WakeLockabble
    public int openUsbDevice(String str, int i) {
        JoinPoint makeJP = Factory.makeJP(ajctjp_3, this, this, str, Conversions.intObject(i));
        return Conversions.intValue(openUsbDevice_aroundBody7$advice(this, str, i, makeJP, WakeLockAspect.aspectOf(), (ProceedingJoinPoint) makeJP));
    }

    @WakeLockabble
    public int openUsbDeviceFD(int i, int i2, int i3, int i4) {
        JoinPoint makeJP = Factory.makeJP(ajctjp_4, (Object) this, (Object) this, new Object[]{Conversions.intObject(i), Conversions.intObject(i2), Conversions.intObject(i3), Conversions.intObject(i4)});
        return Conversions.intValue(openUsbDeviceFD_aroundBody9$advice(this, i, i2, i3, i4, makeJP, WakeLockAspect.aspectOf(), (ProceedingJoinPoint) makeJP));
    }

    @WakeLockabble
    public int setConfigParam(int i, byte[] bArr) {
        JoinPoint makeJP = Factory.makeJP(ajctjp_5, this, this, Conversions.intObject(i), bArr);
        return Conversions.intValue(setConfigParam_aroundBody11$advice(this, i, bArr, makeJP, WakeLockAspect.aspectOf(), (ProceedingJoinPoint) makeJP));
    }

    @WakeLockabble
    public int verify(int i, int i2, Coder coder, int i3, int i4, TemplateList templateList, int i5, Observer observer, ResultMatching resultMatching) {
        JoinPoint makeJP = Factory.makeJP(ajctjp_7, (Object) this, (Object) this, new Object[]{Conversions.intObject(i), Conversions.intObject(i2), coder, Conversions.intObject(i3), Conversions.intObject(i4), templateList, Conversions.intObject(i5), observer, resultMatching});
        return Conversions.intValue(verify_aroundBody15$advice(this, i, i2, coder, i3, i4, templateList, i5, observer, resultMatching, makeJP, WakeLockAspect.aspectOf(), (ProceedingJoinPoint) makeJP));
    }

    @WakeLockabble
    public int verifyMatch(int i, TemplateList templateList, TemplateList templateList2, CustomInteger customInteger) {
        JoinPoint makeJP = Factory.makeJP(ajctjp_8, (Object) this, (Object) this, new Object[]{Conversions.intObject(i), templateList, templateList2, customInteger});
        return Conversions.intValue(verifyMatch_aroundBody17$advice(this, i, templateList, templateList2, customInteger, makeJP, WakeLockAspect.aspectOf(), (ProceedingJoinPoint) makeJP));
    }

    static {
        ajcpreClinit();
    }

    public enum MorphoDevicePrivacyModeStatus {
        PRIVACY_MODE_DISABLED(1),
        PRIVACY_MODE_ENABLED(2),
        PRIVACY_MODE_STANDALONE_ENABLED(3),
        PRIVACY_MODE_PARTIAL_ENABLED(18);
        
        private final int value;

        private MorphoDevicePrivacyModeStatus(int i) {
            this.value = i;
        }

        public int getValue() {
            return this.value;
        }
    }

    public enum MorphoDevicePrivacyModeDBProcessingChoice {
        PRIVACY_MODE_DB_PROCESSING_NOTHING(0),
        PRIVACY_MODE_DB_PROCESSING_ERASE(1),
        PRIVACY_MODE_DB_PROCESSING_CIPHER_TRANCIPHER(2);
        
        private final int value;

        private MorphoDevicePrivacyModeDBProcessingChoice(int i) {
            this.value = i;
        }

        public int getValue() {
            return this.value;
        }
    }

    private static  Object capture_aroundBody3$advice(MorphoDevice morphoDevice, int i, int i2, int i3, int i4, TemplateType templateType, TemplateFVPType templateFVPType, int i5, EnrollmentType enrollmentType, LatentDetection latentDetection, Coder coder, int i6, CompressionAlgorithm compressionAlgorithm, int i7, TemplateList templateList, int i8, Observer observer, JoinPoint joinPoint, WakeLockAspect wakeLockAspect, ProceedingJoinPoint proceedingJoinPoint) {
        try {
            wakeLockAspect.acquireWakeLock();
            String declaringTypeName = proceedingJoinPoint.getSignature().getDeclaringTypeName();
            String name = proceedingJoinPoint.getSignature().getName();
            String ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG = WakeLockAspect.TAG;
            Log.e(ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG, "Entering method [" + declaringTypeName + "." + name + "]");
            return Conversions.intObject(capture_aroundBody2(morphoDevice, i, i2, i3, i4, templateType, templateFVPType, i5, enrollmentType, latentDetection, coder, i6, compressionAlgorithm, i7, templateList, i8, observer, proceedingJoinPoint));
        } catch (Throwable th) {
            throw th;
        } finally {
            wakeLockAspect.releaseWakeLock();
        }
    }

    private static  Object capture_aroundBody5$advice(MorphoDevice morphoDevice, int i, int i2, int i3, int i4, TemplateType templateType, TemplateFVPType templateFVPType, int i5, EnrollmentType enrollmentType, LatentDetection latentDetection, Coder coder, int i6, TemplateList templateList, int i7, Observer observer, JoinPoint joinPoint, WakeLockAspect wakeLockAspect, ProceedingJoinPoint proceedingJoinPoint) {
        try {
            wakeLockAspect.acquireWakeLock();
            String declaringTypeName = proceedingJoinPoint.getSignature().getDeclaringTypeName();
            String name = proceedingJoinPoint.getSignature().getName();
            String ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG = WakeLockAspect.TAG;
            Log.e(ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG, "Entering method [" + declaringTypeName + "." + name + "]");
            return Conversions.intObject(capture_aroundBody4(morphoDevice, i, i2, i3, i4, templateType, templateFVPType, i5, enrollmentType, latentDetection, coder, i6, templateList, i7, observer, proceedingJoinPoint));
        } catch (Throwable th) {
            throw th;
        } finally {
            wakeLockAspect.releaseWakeLock();
        }
    }

    private static  Object comReceive_aroundBody21$advice(MorphoDevice morphoDevice, int i, JoinPoint joinPoint, WakeLockAspect wakeLockAspect, ProceedingJoinPoint proceedingJoinPoint) {
        try {
            wakeLockAspect.acquireWakeLock();
            String declaringTypeName = proceedingJoinPoint.getSignature().getDeclaringTypeName();
            String name = proceedingJoinPoint.getSignature().getName();
            String ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG = WakeLockAspect.TAG;
            Log.e(ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG, "Entering method [" + declaringTypeName + "." + name + "]");
            return comReceive_aroundBody20(morphoDevice, i, proceedingJoinPoint);
        } catch (Throwable th) {
            throw th;
        } finally {
            wakeLockAspect.releaseWakeLock();
        }
    }

    private static  Object comSend_aroundBody19$advice(MorphoDevice morphoDevice, int i, byte[] bArr, JoinPoint joinPoint, WakeLockAspect wakeLockAspect, ProceedingJoinPoint proceedingJoinPoint) {
        try {
            wakeLockAspect.acquireWakeLock();
            String declaringTypeName = proceedingJoinPoint.getSignature().getDeclaringTypeName();
            String name = proceedingJoinPoint.getSignature().getName();
            String ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG = WakeLockAspect.TAG;
            Log.e(ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG, "Entering method [" + declaringTypeName + "." + name + "]");
            return Conversions.intObject(comSend_aroundBody18(morphoDevice, i, bArr, proceedingJoinPoint));
        } catch (Throwable th) {
            throw th;
        } finally {
            wakeLockAspect.releaseWakeLock();
        }
    }

    private static  Object getImage_aroundBody13$advice(MorphoDevice morphoDevice, int i, int i2, CompressionAlgorithm compressionAlgorithm, int i3, int i4, LatentDetection latentDetection, MorphoImage morphoImage, int i5, Observer observer, JoinPoint joinPoint, WakeLockAspect wakeLockAspect, ProceedingJoinPoint proceedingJoinPoint) {
        try {
            wakeLockAspect.acquireWakeLock();
            String declaringTypeName = proceedingJoinPoint.getSignature().getDeclaringTypeName();
            String name = proceedingJoinPoint.getSignature().getName();
            String ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG = WakeLockAspect.TAG;
            Log.e(ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG, "Entering method [" + declaringTypeName + "." + name + "]");
            return Conversions.intObject(getImage_aroundBody12(morphoDevice, i, i2, compressionAlgorithm, i3, i4, latentDetection, morphoImage, i5, observer, proceedingJoinPoint));
        } catch (Throwable th) {
            throw th;
        } finally {
            wakeLockAspect.releaseWakeLock();
        }
    }

    private static  Object loadKs_aroundBody23$advice(MorphoDevice morphoDevice, byte[] bArr, JoinPoint joinPoint, WakeLockAspect wakeLockAspect, ProceedingJoinPoint proceedingJoinPoint) {
        try {
            wakeLockAspect.acquireWakeLock();
            String declaringTypeName = proceedingJoinPoint.getSignature().getDeclaringTypeName();
            String name = proceedingJoinPoint.getSignature().getName();
            String ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG = WakeLockAspect.TAG;
            Log.e(ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG, "Entering method [" + declaringTypeName + "." + name + "]");
            return Conversions.intObject(loadKs_aroundBody22(morphoDevice, bArr, proceedingJoinPoint));
        } catch (Throwable th) {
            throw th;
        } finally {
            wakeLockAspect.releaseWakeLock();
        }
    }

    private static  Object loadMocKey_aroundBody25$advice(MorphoDevice morphoDevice, byte[] bArr, byte[] bArr2, byte[] bArr3, JoinPoint joinPoint, WakeLockAspect wakeLockAspect, ProceedingJoinPoint proceedingJoinPoint) {
        try {
            wakeLockAspect.acquireWakeLock();
            String declaringTypeName = proceedingJoinPoint.getSignature().getDeclaringTypeName();
            String name = proceedingJoinPoint.getSignature().getName();
            String ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG = WakeLockAspect.TAG;
            Log.e(ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG, "Entering method [" + declaringTypeName + "." + name + "]");
            return Conversions.intObject(loadMocKey_aroundBody24(morphoDevice, bArr, bArr2, bArr3, proceedingJoinPoint));
        } catch (Throwable th) {
            throw th;
        } finally {
            wakeLockAspect.releaseWakeLock();
        }
    }

    private static  Object loadMocKey_aroundBody27$advice(MorphoDevice morphoDevice, byte[] bArr, JoinPoint joinPoint, WakeLockAspect wakeLockAspect, ProceedingJoinPoint proceedingJoinPoint) {
        try {
            wakeLockAspect.acquireWakeLock();
            String declaringTypeName = proceedingJoinPoint.getSignature().getDeclaringTypeName();
            String name = proceedingJoinPoint.getSignature().getName();
            String ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG = WakeLockAspect.TAG;
            Log.e(ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG, "Entering method [" + declaringTypeName + "." + name + "]");
            return Conversions.intObject(loadMocKey_aroundBody26(morphoDevice, bArr, proceedingJoinPoint));
        } catch (Throwable th) {
            throw th;
        } finally {
            wakeLockAspect.releaseWakeLock();
        }
    }

    private static  Object openDevicePipe_aroundBody1$advice(MorphoDevice morphoDevice, IMsoPipe iMsoPipe, String str, int i, String str2, int i2, JoinPoint joinPoint, WakeLockAspect wakeLockAspect, ProceedingJoinPoint proceedingJoinPoint) {
        try {
            wakeLockAspect.acquireWakeLock();
            String declaringTypeName = proceedingJoinPoint.getSignature().getDeclaringTypeName();
            String name = proceedingJoinPoint.getSignature().getName();
            String ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG = WakeLockAspect.TAG;
            Log.e(ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG, "Entering method [" + declaringTypeName + "." + name + "]");
            return Conversions.intObject(openDevicePipe_aroundBody0(morphoDevice, iMsoPipe, str, i, str2, i2, proceedingJoinPoint));
        } catch (Throwable th) {
            throw th;
        } finally {
            wakeLockAspect.releaseWakeLock();
        }
    }

    private static  Object openUsbDeviceFD_aroundBody9$advice(MorphoDevice morphoDevice, int i, int i2, int i3, int i4, JoinPoint joinPoint, WakeLockAspect wakeLockAspect, ProceedingJoinPoint proceedingJoinPoint) {
        try {
            wakeLockAspect.acquireWakeLock();
            String declaringTypeName = proceedingJoinPoint.getSignature().getDeclaringTypeName();
            String name = proceedingJoinPoint.getSignature().getName();
            String ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG = WakeLockAspect.TAG;
            Log.e(ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG, "Entering method [" + declaringTypeName + "." + name + "]");
            return Conversions.intObject(openUsbDeviceFD_aroundBody8(morphoDevice, i, i2, i3, i4, proceedingJoinPoint));
        } catch (Throwable th) {
            throw th;
        } finally {
            wakeLockAspect.releaseWakeLock();
        }
    }

    private static  Object openUsbDevice_aroundBody7$advice(MorphoDevice morphoDevice, String str, int i, JoinPoint joinPoint, WakeLockAspect wakeLockAspect, ProceedingJoinPoint proceedingJoinPoint) {
        try {
            wakeLockAspect.acquireWakeLock();
            String declaringTypeName = proceedingJoinPoint.getSignature().getDeclaringTypeName();
            String name = proceedingJoinPoint.getSignature().getName();
            String ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG = WakeLockAspect.TAG;
            Log.e(ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG, "Entering method [" + declaringTypeName + "." + name + "]");
            return Conversions.intObject(openUsbDevice_aroundBody6(morphoDevice, str, i, proceedingJoinPoint));
        } catch (Throwable th) {
            throw th;
        } finally {
            wakeLockAspect.releaseWakeLock();
        }
    }

    private static  Object setConfigParam_aroundBody11$advice(MorphoDevice morphoDevice, int i, byte[] bArr, JoinPoint joinPoint, WakeLockAspect wakeLockAspect, ProceedingJoinPoint proceedingJoinPoint) {
        try {
            wakeLockAspect.acquireWakeLock();
            String declaringTypeName = proceedingJoinPoint.getSignature().getDeclaringTypeName();
            String name = proceedingJoinPoint.getSignature().getName();
            String ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG = WakeLockAspect.TAG;
            Log.e(ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG, "Entering method [" + declaringTypeName + "." + name + "]");
            return Conversions.intObject(setConfigParam_aroundBody10(morphoDevice, i, bArr, proceedingJoinPoint));
        } catch (Throwable th) {
            throw th;
        } finally {
            wakeLockAspect.releaseWakeLock();
        }
    }

    private static  Object verifyMatch_aroundBody17$advice(MorphoDevice morphoDevice, int i, TemplateList templateList, TemplateList templateList2, CustomInteger customInteger, JoinPoint joinPoint, WakeLockAspect wakeLockAspect, ProceedingJoinPoint proceedingJoinPoint) {
        try {
            wakeLockAspect.acquireWakeLock();
            String declaringTypeName = proceedingJoinPoint.getSignature().getDeclaringTypeName();
            String name = proceedingJoinPoint.getSignature().getName();
            String ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG = WakeLockAspect.TAG;
            Log.e(ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG, "Entering method [" + declaringTypeName + "." + name + "]");
            return Conversions.intObject(verifyMatch_aroundBody16(morphoDevice, i, templateList, templateList2, customInteger, proceedingJoinPoint));
        } catch (Throwable th) {
            throw th;
        } finally {
            wakeLockAspect.releaseWakeLock();
        }
    }

    private static  Object verify_aroundBody15$advice(MorphoDevice morphoDevice, int i, int i2, Coder coder, int i3, int i4, TemplateList templateList, int i5, Observer observer, ResultMatching resultMatching, JoinPoint joinPoint, WakeLockAspect wakeLockAspect, ProceedingJoinPoint proceedingJoinPoint) {
        try {
            wakeLockAspect.acquireWakeLock();
            String declaringTypeName = proceedingJoinPoint.getSignature().getDeclaringTypeName();
            String name = proceedingJoinPoint.getSignature().getName();
            String ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG = WakeLockAspect.TAG;
            Log.e(ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG, "Entering method [" + declaringTypeName + "." + name + "]");
            return Conversions.intObject(verify_aroundBody14(morphoDevice, i, i2, coder, i3, i4, templateList, i5, observer, resultMatching, proceedingJoinPoint));
        } catch (Throwable th) {
            throw th;
        } finally {
            wakeLockAspect.releaseWakeLock();
        }
    }

    private static  int openDevicePipe_aroundBody0(MorphoDevice morphoDevice, IMsoPipe iMsoPipe, String str, int i, String str2, int i2, JoinPoint joinPoint) {
        if (!morphoDevice.cppMemOwn) {
            return -98;
        }
        if (str == null || str2 == null) {
            return -5;
        }
        morphoDevice.deviceSN = str2;
        morphoDevice.deviceOpenedWithPipe = true;
        morphoDevice.pipeCallBack = iMsoPipe;
        morphoDevice.pipeIP = str;
        morphoDevice.pipePort = i;
        morphoDevice.pipeTimeout = i2;
        return morphoDeviceNative.openDevicePipe(morphoDevice.morphoDevicePointerCPP.longValue(), iMsoPipe, str, i, str2, i2);
    }

    public MorphoDevice() {
        long cPPInstance = morphoDeviceNative.getCPPInstance();
        if (cPPInstance != 0) {
            this.cppMemOwn = true;
            this.morphoDevicePointerCPP = Long.valueOf(cPPInstance);
            return;
        }
        try {
            throw new MorphoSmartException("cppPtr is null");
        } catch (MorphoSmartException e) {
            e.printStackTrace();
        }
    }

    private void createScreenReceiver() {
        USBManager.getInstance();
        this.screenReceiver = new ScreenBroadcastReceiver();
        this.screenReceiver.setDevice(this);
        IntentFilter intentFilter = new IntentFilter(ACTION_SCREEN_RECEIVER);
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        this.context = USBManager.context;
        Context context2 = this.context;
        if (context2 != null) {
            context2.registerReceiver(this.screenReceiver, intentFilter);
            USBManager.screenReceiverMap.put(this.context, this.screenReceiver);
        }
    }

    public MorphoDevice(MorphoDevice morphoDevice) {
        if (morphoDevice == null) {
            try {
                throw new MorphoSmartException("MorphoDevice object is null");
            } catch (MorphoSmartException e) {
                e.printStackTrace();
            }
        } else if (!morphoDevice.cppMemOwn) {
            long cPPInstance = morphoDeviceNative.getCPPInstance(morphoDevice.morphoDevicePointerCPP.longValue());
            if (cPPInstance != 0) {
                this.cppMemOwn = true;
                this.morphoDevicePointerCPP = Long.valueOf(cPPInstance);
                return;
            }
            try {
                throw new MorphoSmartException("cppPtr is null");
            } catch (MorphoSmartException e2) {
                e2.printStackTrace();
            }
        } else {
            try {
                throw new MorphoSmartException("cppMemOwn is true");
            } catch (MorphoSmartException e3) {
                e3.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        if (this.cppMemOwn) {
            closeDevice();
            morphoDeviceNative.deleteInstance(this.morphoDevicePointerCPP.longValue());
            this.cppMemOwn = false;
        }
    }

    public int ping() {
        if (!this.cppMemOwn) {
            return -98;
        }
        return morphoDeviceNative.ping(this.morphoDevicePointerCPP.longValue());
    }

    public Object clone() {
        return new MorphoDevice(this);
    }

    public void setMorphoDeviceNativePointerCPP(long j) {
        this.morphoDevicePointerCPP = Long.valueOf(j);
        this.cppMemOwn = true;
    }

    public int getDatabase(int i, MorphoDatabase morphoDatabase) {
        if (!this.cppMemOwn) {
            return -98;
        }
        if (morphoDatabase == null) {
            return -5;
        }
        return morphoDeviceNative.getDatabase(this.morphoDevicePointerCPP.longValue(), i, morphoDatabase);
    }

    public int cancelLiveAcquisition() {
        if (!this.cppMemOwn) {
            return -98;
        }
        return morphoDeviceNative.cancelLiveAcquisition(this.morphoDevicePointerCPP.longValue());
    }

    private static  int capture_aroundBody2(MorphoDevice morphoDevice, int i, int i2, int i3, int i4, TemplateType templateType, TemplateFVPType templateFVPType, int i5, EnrollmentType enrollmentType, LatentDetection latentDetection, Coder coder, int i6, CompressionAlgorithm compressionAlgorithm, int i7, TemplateList templateList, int i8, Observer observer, JoinPoint joinPoint) {
        MorphoDevice morphoDevice2 = morphoDevice;
        if (!morphoDevice2.cppMemOwn) {
            return -98;
        }
        if (templateType == null || templateFVPType == null || enrollmentType == null || latentDetection == null || coder == null || templateList == null || compressionAlgorithm == null) {
            return -5;
        }
        return morphoDeviceNative.capture(morphoDevice2.morphoDevicePointerCPP.longValue(), i, i2, i3, i4, templateType.getCode(), templateFVPType.getCode(), i5, enrollmentType.getValue(), latentDetection.getValue(), coder.getCode(), i6, templateList, i8, observer, compressionAlgorithm.getCode(), i7);
    }

    private static  int capture_aroundBody4(MorphoDevice morphoDevice, int i, int i2, int i3, int i4, TemplateType templateType, TemplateFVPType templateFVPType, int i5, EnrollmentType enrollmentType, LatentDetection latentDetection, Coder coder, int i6, TemplateList templateList, int i7, Observer observer, JoinPoint joinPoint) {
        MorphoDevice morphoDevice2 = morphoDevice;
        if (!morphoDevice2.cppMemOwn) {
            return -98;
        }
        if (templateType == null || templateFVPType == null || enrollmentType == null || latentDetection == null || coder == null || templateList == null) {
            return -5;
        }
        return morphoDeviceNative.capture(morphoDevice2.morphoDevicePointerCPP.longValue(), i, i2, i3, i4, templateType.getCode(), templateFVPType.getCode(), i5, enrollmentType.getValue(), latentDetection.getValue(), coder.getCode(), i6, templateList, i7, observer, CompressionAlgorithm.MORPHO_NO_COMPRESS.getCode(), 0);
    }

    public int closeDevice() {
        USBManager.getInstance();
        USBManager.unRegister(this.context, this.screenReceiver);
        if (!this.cppMemOwn) {
            return -98;
        }
        this.deviceSN = BuildConfig.FLAVOR;
        this.deviceBus = -1;
        this.deviceAddress = -1;
        this.deviceFD = -1;
        this.deviceOpenedWithFD = false;
        this.deviceOpenedWithPipe = false;
        return morphoDeviceNative.closeDevice(this.morphoDevicePointerCPP.longValue());
    }

    public int enableCS(boolean z) {
        if (!this.cppMemOwn) {
            return -98;
        }
        return morphoDeviceNative.enableCS(this.morphoDevicePointerCPP.longValue(), z);
    }

    public int enableDataEncryption(boolean z, String str) {
        if (!this.cppMemOwn) {
            return -98;
        }
        if (!z || str != null) {
            return morphoDeviceNative.enableDataEncryptionByString(this.morphoDevicePointerCPP.longValue(), z, str);
        }
        return -5;
    }

    public int enableDataEncryption(boolean z, byte[] bArr) {
        if (!this.cppMemOwn) {
            return -98;
        }
        if (!z || bArr != null) {
            return morphoDeviceNative.enableDataEncryptionByByteArray(this.morphoDevicePointerCPP.longValue(), z, bArr);
        }
        return -5;
    }

    public int resumeConnection(int i, Observer observer) {
        if (!this.cppMemOwn) {
            return -98;
        }
        ResumeConnectionThread resumeConnectionThread = this._reboot;
        if (resumeConnectionThread != null && resumeConnectionThread.isAlive()) {
            return -96;
        }
        this._reboot = new ResumeConnectionThread();
        this._reboot.setTimeOut(i);
        this._reboot.setDevice(this);
        this._reboot.setObserver(observer);
        this._reboot.setDeviceName(this.deviceSN);
        this._reboot.setDeviceOpenWithFD(this.deviceOpenedWithFD);
        this._reboot.setDeviceOpenWithPipe(this.deviceOpenedWithPipe);
        this._reboot.setPipeParameters(this.pipeCallBack, this.pipeIP, this.pipePort, this.pipeTimeout);
        this._reboot.setSecurityParameters(this.isTunnelingMode, this.isOfferedSecurityMode, this._msoSecu, this._hostCertificate);
        this._reboot.start();
        return 0;
    }

    public String getProductDescriptor() {
        return morphoDeviceNative.getProductDescriptor(this.morphoDevicePointerCPP.longValue());
    }

    public String getSensorDescriptor() {
        return morphoDeviceNative.getSensorDescriptor(this.morphoDevicePointerCPP.longValue());
    }

    public String getSoftwareDescriptor() {
        return morphoDeviceNative.getSoftwareDescriptor(this.morphoDevicePointerCPP.longValue());
    }

    private static  int openUsbDevice_aroundBody6(MorphoDevice morphoDevice, String str, int i, JoinPoint joinPoint) {
        if (!morphoDevice.cppMemOwn) {
            return -98;
        }
        if (str == null) {
            return -5;
        }
        morphoDevice.createScreenReceiver();
        morphoDevice.deviceSN = str;
        return morphoDeviceNative.openUsbDevice(morphoDevice.morphoDevicePointerCPP.longValue(), str, i);
    }

    private static  int openUsbDeviceFD_aroundBody8(MorphoDevice morphoDevice, int i, int i2, int i3, int i4, JoinPoint joinPoint) {
        if (!morphoDevice.cppMemOwn) {
            return -98;
        }
        morphoDevice.createScreenReceiver();
        int openUsbDeviceFD = morphoDeviceNative.openUsbDeviceFD(morphoDevice.morphoDevicePointerCPP.longValue(), i, i2, i3, i4);
        if (openUsbDeviceFD == 0) {
            morphoDevice.deviceSN = morphoDeviceNative.getOpenedUsbDeviceName(morphoDevice.morphoDevicePointerCPP.longValue());
            morphoDevice.deviceBus = i;
            morphoDevice.deviceAddress = i2;
            morphoDevice.deviceFD = i3;
            morphoDevice.deviceOpenedWithFD = true;
        }
        return openUsbDeviceFD;
    }

    private static  int setConfigParam_aroundBody10(MorphoDevice morphoDevice, int i, byte[] bArr, JoinPoint joinPoint) {
        if (!morphoDevice.cppMemOwn) {
            return -98;
        }
        if (bArr == null) {
            return -5;
        }
        return morphoDeviceNative.setConfigParam(morphoDevice.morphoDevicePointerCPP.longValue(), i, bArr);
    }

    public int SecuReadCertificate(int i, ArrayList<Byte> arrayList) {
        if (!this.cppMemOwn) {
            return -98;
        }
        if (arrayList == null || i < 0) {
            return -5;
        }
        return morphoDeviceNative.SecuReadCertificate(this.morphoDevicePointerCPP.longValue(), i, arrayList);
    }

    public int setSecurityLevel(SecurityLevel securityLevel) {
        if (!this.cppMemOwn) {
            return -98;
        }
        if (securityLevel == null) {
            return -5;
        }
        return morphoDeviceNative.setSecurityLevel(this.morphoDevicePointerCPP.longValue(), securityLevel.getValue());
    }

    public int getSecurityLevel() {
        if (!this.cppMemOwn) {
            return -98;
        }
        return morphoDeviceNative.getSecurityLevel(this.morphoDevicePointerCPP.longValue());
    }

    public int setLoggingMode(MorphoLogMode morphoLogMode) {
        if (!this.cppMemOwn) {
            return -98;
        }
        if (morphoLogMode == null) {
            return -5;
        }
        return morphoDeviceNative.setLoggingMode(this.morphoDevicePointerCPP.longValue(), morphoLogMode.getCode());
    }

    public int setLoggingLevelOfGroup(int i, MorphoLogLevel morphoLogLevel) {
        if (!this.cppMemOwn) {
            return -98;
        }
        if (morphoLogLevel == null) {
            return -5;
        }
        return morphoDeviceNative.setLoggingLevelOfGroup(this.morphoDevicePointerCPP.longValue(), i, morphoLogLevel.getCode());
    }

    public int setStrategyAcquisitionMode(StrategyAcquisitionMode strategyAcquisitionMode) {
        if (!this.cppMemOwn) {
            return -98;
        }
        if (strategyAcquisitionMode == null) {
            return -5;
        }
        return morphoDeviceNative.setStrategyAcquisitionMode(this.morphoDevicePointerCPP.longValue(), strategyAcquisitionMode.getCode());
    }

    public StrategyAcquisitionMode getStrategyAcquisitionMode() {
        if (!this.cppMemOwn) {
            return null;
        }
        return StrategyAcquisitionMode.getValue(morphoDeviceNative.getStrategyAcquisitionMode(this.morphoDevicePointerCPP.longValue()));
    }

    public int getComType() {
        if (!this.cppMemOwn) {
            return -98;
        }
        return morphoDeviceNative.getComType(this.morphoDevicePointerCPP.longValue());
    }

    public byte[] getConfigParam(int i) {
        if (!this.cppMemOwn) {
            return null;
        }
        return morphoDeviceNative.getConfigParam(this.morphoDevicePointerCPP.longValue(), i);
    }

    public String getStringDescriptorBin(DescriptorID descriptorID) {
        if (descriptorID == null) {
            try {
                throw new MorphoSmartException("DescriptorID object is null");
            } catch (MorphoSmartException e) {
                e.printStackTrace();
            }
        }
        return morphoDeviceNative.getStringDescriptorBin(this.morphoDevicePointerCPP.longValue(), descriptorID.getValue());
    }

    public int getIntDescriptorBin(DescriptorID descriptorID) {
        if (descriptorID == null) {
            try {
                throw new MorphoSmartException("DescriptorID object is null");
            } catch (MorphoSmartException e) {
                e.printStackTrace();
            }
        }
        return morphoDeviceNative.getIntDescriptorBin(this.morphoDevicePointerCPP.longValue(), descriptorID.getValue());
    }

    public String getFFDLogs() {
        if (!this.cppMemOwn) {
            try {
                throw new MorphoSmartException("MorphoDevice object is null");
            } catch (MorphoSmartException e) {
                e.printStackTrace();
            }
        }
        return morphoDeviceNative.getFFDLogs(this.morphoDevicePointerCPP.longValue());
    }

    public int getInternalError() {
        if (!this.cppMemOwn) {
            return -98;
        }
        return morphoDeviceNative.getInternalError(this.morphoDevicePointerCPP.longValue());
    }

    private static  int getImage_aroundBody12(MorphoDevice morphoDevice, int i, int i2, CompressionAlgorithm compressionAlgorithm, int i3, int i4, LatentDetection latentDetection, MorphoImage morphoImage, int i5, Observer observer, JoinPoint joinPoint) {
        MorphoDevice morphoDevice2 = morphoDevice;
        if (!morphoDevice2.cppMemOwn) {
            return -98;
        }
        if (compressionAlgorithm == null || latentDetection == null || morphoImage == null) {
            return -5;
        }
        MorphoDeviceNative morphoDeviceNative2 = morphoDeviceNative;
        long longValue = morphoDevice2.morphoDevicePointerCPP.longValue();
        return morphoDeviceNative2.getImage(longValue, i, i2, compressionAlgorithm.getCode(), i3, i4, latentDetection.getValue(), morphoImage, i5, observer);
    }

    public int getSecuConfig(SecuConfig secuConfig) {
        if (!this.cppMemOwn) {
            return -98;
        }
        if (secuConfig == null) {
            return -5;
        }
        return morphoDeviceNative.getSecuConfig(this.morphoDevicePointerCPP.longValue(), secuConfig);
    }

    public byte[] getUnlockSeed() {
        if (!this.cppMemOwn) {
            return null;
        }
        return morphoDeviceNative.getUnlockSeed(this.morphoDevicePointerCPP.longValue());
    }

    public String getUsbDeviceName(int i) {
        if (!this.cppMemOwn) {
            return BuildConfig.FLAVOR;
        }
        return morphoDeviceNative.getUsbDeviceName(this.morphoDevicePointerCPP.longValue(), i);
    }

    public String getOpenedUsbDeviceName() {
        if (!this.cppMemOwn) {
            return BuildConfig.FLAVOR;
        }
        return morphoDeviceNative.getOpenedUsbDeviceName(this.morphoDevicePointerCPP.longValue());
    }

    public int initUsbDevicesNameEnum(CustomInteger customInteger) {
        if (!this.cppMemOwn) {
            return -98;
        }
        if (customInteger == null) {
            return -5;
        }
        if (ErrorCodes.IntegrerInitializationValueOf(customInteger).booleanValue()) {
            return -94;
        }
        return morphoDeviceNative.initUsbDevicesNameEnum(this.morphoDevicePointerCPP.longValue(), customInteger);
    }

    public String getUsbDevicePropertie(int i) {
        if (!this.cppMemOwn) {
            return BuildConfig.FLAVOR;
        }
        return morphoDeviceNative.getUsbDevicePropertie(this.morphoDevicePointerCPP.longValue(), i);
    }

    public boolean isCSEnabled() {
        if (!this.cppMemOwn) {
            return false;
        }
        return morphoDeviceNative.isCSEnabled(this.morphoDevicePointerCPP.longValue());
    }

    public boolean isDataEncryptionEnabled() {
        if (!this.cppMemOwn) {
            return false;
        }
        return morphoDeviceNative.isDataEncryptionEnabled(this.morphoDevicePointerCPP.longValue());
    }

    public int getPrivacyModeStatus(MorphoDevicePrivacyModeStatus[] morphoDevicePrivacyModeStatusArr) {
        if (!this.cppMemOwn) {
            return -98;
        }
        if (morphoDevicePrivacyModeStatusArr == null) {
            return -5;
        }
        return morphoDeviceNative.getPrivacyModeStatus(this.morphoDevicePointerCPP.longValue(), morphoDevicePrivacyModeStatusArr);
    }

    public int setPrivacyModeStatus(MorphoDevicePrivacyModeStatus morphoDevicePrivacyModeStatus, MorphoDevicePrivacyModeDBProcessingChoice morphoDevicePrivacyModeDBProcessingChoice) {
        if (!this.cppMemOwn) {
            return -98;
        }
        return morphoDeviceNative.setPrivacyModeStatus(this.morphoDevicePointerCPP.longValue(), morphoDevicePrivacyModeStatus.getValue(), morphoDevicePrivacyModeDBProcessingChoice.getValue());
    }

    public int loadKprivacy(byte[] bArr) {
        if (!this.cppMemOwn) {
            return -98;
        }
        if (bArr == null) {
            return -5;
        }
        return morphoDeviceNative.loadKprivacy(this.morphoDevicePointerCPP.longValue(), bArr);
    }

    public int loadKprivacySecurely(byte[] bArr, byte[] bArr2) {
        if (!this.cppMemOwn) {
            return -98;
        }
        if (bArr == null || bArr2 == null) {
            return -5;
        }
        return morphoDeviceNative.loadKprivacySecurely(this.morphoDevicePointerCPP.longValue(), bArr, bArr2);
    }

    public byte[] getPrivacySeed() {
        if (!this.cppMemOwn) {
            return null;
        }
        return morphoDeviceNative.getPrivacySeed(this.morphoDevicePointerCPP.longValue());
    }

    public int rebootSoft(int i, Observer observer) {
        if (!this.cppMemOwn) {
            return -98;
        }
        ResumeConnectionThread resumeConnectionThread = this._reboot;
        if (resumeConnectionThread != null && resumeConnectionThread.isAlive()) {
            return -96;
        }
        int rebootSoft = morphoDeviceNative.rebootSoft(this.morphoDevicePointerCPP.longValue());
        if (rebootSoft == 0) {
            this._reboot = new ResumeConnectionThread();
            this._reboot.setTimeOut(i);
            this._reboot.setDevice(this);
            this._reboot.setObserver(observer);
            this._reboot.setDeviceName(this.deviceSN);
            this._reboot.setDeviceOpenWithFD(this.deviceOpenedWithFD);
            this._reboot.setDeviceOpenWithPipe(this.deviceOpenedWithPipe);
            this._reboot.setPipeParameters(this.pipeCallBack, this.pipeIP, this.pipePort, this.pipeTimeout);
            this._reboot.setSecurityParameters(this.isTunnelingMode, this.isOfferedSecurityMode, this._msoSecu, this._hostCertificate);
            this._reboot.start();
        }
        return rebootSoft;
    }

    public int unlock(byte[] bArr, byte[] bArr2) {
        if (!this.cppMemOwn) {
            return -98;
        }
        if (bArr == null || bArr2 == null) {
            return -5;
        }
        return morphoDeviceNative.unlock(this.morphoDevicePointerCPP.longValue(), bArr, bArr2);
    }

    public int unlock(String str, String str2) {
        if (!this.cppMemOwn) {
            return -98;
        }
        if (str == null || str2 == null) {
            return -5;
        }
        return morphoDeviceNative.unlock(this.morphoDevicePointerCPP.longValue(), str.getBytes(), str2.getBytes());
    }

    private static  int verify_aroundBody14(MorphoDevice morphoDevice, int i, int i2, Coder coder, int i3, int i4, TemplateList templateList, int i5, Observer observer, ResultMatching resultMatching, JoinPoint joinPoint) {
        MorphoDevice morphoDevice2 = morphoDevice;
        if (!morphoDevice2.cppMemOwn) {
            return -98;
        }
        if (coder == null || templateList == null) {
            return -5;
        }
        MorphoDeviceNative morphoDeviceNative2 = morphoDeviceNative;
        long longValue = morphoDevice2.morphoDevicePointerCPP.longValue();
        return morphoDeviceNative2.verify(longValue, i, i2, coder.getCode(), i3, i4, templateList, i5, observer, resultMatching);
    }

    private static  int verifyMatch_aroundBody16(MorphoDevice morphoDevice, int i, TemplateList templateList, TemplateList templateList2, CustomInteger customInteger, JoinPoint joinPoint) {
        if (!morphoDevice.cppMemOwn) {
            return -98;
        }
        if (templateList == null || templateList2 == null) {
            return -5;
        }
        if (ErrorCodes.IntegrerInitializationValueOf(customInteger).booleanValue()) {
            return -94;
        }
        return morphoDeviceNative.verifyMatch(morphoDevice.morphoDevicePointerCPP.longValue(), i, templateList, templateList2, customInteger);
    }

    public byte[] getKCV(MorphoKCVID morphoKCVID, byte[] bArr) {
        if (this.cppMemOwn && morphoKCVID != null) {
            return morphoDeviceNative.getKCV(this.morphoDevicePointerCPP.longValue(), morphoKCVID.getCode(), bArr);
        }
        return null;
    }

    private static  int comSend_aroundBody18(MorphoDevice morphoDevice, int i, byte[] bArr, JoinPoint joinPoint) {
        if (!morphoDevice.cppMemOwn) {
            return -98;
        }
        if (bArr == null) {
            return -5;
        }
        return morphoDeviceNative.comSend(morphoDevice.morphoDevicePointerCPP.longValue(), i, bArr);
    }

    private static  byte[] comReceive_aroundBody20(MorphoDevice morphoDevice, int i, JoinPoint joinPoint) {
        if (!morphoDevice.cppMemOwn) {
            return null;
        }
        return morphoDeviceNative.comReceive(morphoDevice.morphoDevicePointerCPP.longValue(), i);
    }

    private static  int loadKs_aroundBody22(MorphoDevice morphoDevice, byte[] bArr, JoinPoint joinPoint) {
        if (!morphoDevice.cppMemOwn) {
            return -98;
        }
        if (bArr == null) {
            return -5;
        }
        return morphoDeviceNative.loadKs(morphoDevice.morphoDevicePointerCPP.longValue(), bArr);
    }

    public int loadKsSecurely(byte[] bArr) {
        if (!this.cppMemOwn) {
            return -98;
        }
        if (bArr == null) {
            return -5;
        }
        return morphoDeviceNative.loadKsSecurely(this.morphoDevicePointerCPP.longValue(), bArr);
    }

    public int loadKsSecurely(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        if (!this.cppMemOwn) {
            return -98;
        }
        if (bArr == null || bArr2 == null || bArr3 == null) {
            return -5;
        }
        return morphoDeviceNative.loadKsSecurely(this.morphoDevicePointerCPP.longValue(), bArr, bArr2, bArr3);
    }

    private static  int loadMocKey_aroundBody24(MorphoDevice morphoDevice, byte[] bArr, byte[] bArr2, byte[] bArr3, JoinPoint joinPoint) {
        if (!morphoDevice.cppMemOwn) {
            return -98;
        }
        if (bArr == null || bArr2 == null || bArr3 == null) {
            return -5;
        }
        return morphoDeviceNative.loadMocKey(morphoDevice.morphoDevicePointerCPP.longValue(), bArr, bArr2, bArr3);
    }

    private static  int loadMocKey_aroundBody26(MorphoDevice morphoDevice, byte[] bArr, JoinPoint joinPoint) {
        if (!morphoDevice.cppMemOwn) {
            return -98;
        }
        if (bArr == null) {
            return -5;
        }
        return morphoDeviceNative.loadMocKey(morphoDevice.morphoDevicePointerCPP.longValue(), bArr);
    }

    public int offeredSecuOpen(IMsoSecu iMsoSecu) {
        if (!this.cppMemOwn) {
            return -98;
        }
        createScreenReceiver();
        int OfferedSecuOpen = morphoDeviceNative.OfferedSecuOpen(this.morphoDevicePointerCPP.longValue(), iMsoSecu);
        if (OfferedSecuOpen == 0) {
            this.isTunnelingMode = false;
            this.isOfferedSecurityMode = true;
            this._msoSecu = iMsoSecu;
            this._hostCertificate = null;
        }
        return OfferedSecuOpen;
    }

    public int offeredSecuClose() {
        if (!this.cppMemOwn) {
            return -98;
        }
        USBManager.getInstance();
        USBManager.unRegister(this.context, this.screenReceiver);
        int OfferedSecuClose = morphoDeviceNative.OfferedSecuClose(this.morphoDevicePointerCPP.longValue());
        if (OfferedSecuClose == 0) {
            this.isTunnelingMode = false;
            this.isOfferedSecurityMode = false;
            this._msoSecu = null;
            this._hostCertificate = null;
        }
        return OfferedSecuClose;
    }

    public int tunnelingOpen(IMsoSecu iMsoSecu, byte[] bArr) {
        if (!this.cppMemOwn) {
            return -98;
        }
        createScreenReceiver();
        int TunnelingOpen = morphoDeviceNative.TunnelingOpen(this.morphoDevicePointerCPP.longValue(), iMsoSecu, bArr);
        if (TunnelingOpen == 0) {
            this.isTunnelingMode = true;
            this.isOfferedSecurityMode = false;
            this._msoSecu = iMsoSecu;
            this._hostCertificate = bArr;
        }
        return TunnelingOpen;
    }

    public int tunnelingClose() {
        if (!this.cppMemOwn) {
            return -98;
        }
        USBManager.getInstance();
        USBManager.unRegister(this.context, this.screenReceiver);
        int TunnelingClose = morphoDeviceNative.TunnelingClose(this.morphoDevicePointerCPP.longValue());
        if (TunnelingClose == 0) {
            this.isTunnelingMode = false;
            this.isOfferedSecurityMode = false;
            this._msoSecu = null;
            this._hostCertificate = null;
        }
        return TunnelingClose;
    }

    public byte[] getUserAreaData() {
        if (!this.cppMemOwn) {
            return null;
        }
        return morphoDeviceNative.getUserAreaData(this.morphoDevicePointerCPP.longValue());
    }

    public int setUserAreaData(byte[] bArr) {
        if (!this.cppMemOwn) {
            return -98;
        }
        return morphoDeviceNative.setUserAreaData(this.morphoDevicePointerCPP.longValue(), bArr);
    }

    public int removeUserAreaData() {
        if (!this.cppMemOwn) {
            return -98;
        }
        return morphoDeviceNative.removeUserAreaData(this.morphoDevicePointerCPP.longValue());
    }

    public class ScreenBroadcastReceiver extends BroadcastReceiver {
        /* access modifiers changed from: private */
        public MorphoDevice device = null;

        public ScreenBroadcastReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            intent.getAction();
            if (intent.getAction().equals("android.intent.action.SCREEN_OFF")) {
                USBManager.getInstance();
                if (USBManager.WakeLockEnabled) {
                    new Thread() {
                        public void run() {
                            if (ScreenBroadcastReceiver.this.device != null) {
                                ScreenBroadcastReceiver.this.device.cancelLiveAcquisition();
                            }
                        }
                    }.start();
                }
            }
        }

        public void setDevice(MorphoDevice morphoDevice) {
            this.device = morphoDevice;
        }
    }
}
