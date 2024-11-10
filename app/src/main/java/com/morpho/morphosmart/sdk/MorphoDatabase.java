package com.morpho.morphosmart.sdk;

import android.annotation.SuppressLint;
import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.runtime.internal.Conversions;
import org.aspectj.runtime.reflect.Factory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Observer;

import com.morpho.android.annotation.WakeLockAspect;
import com.morpho.android.annotation.WakeLockabble;


@SuppressLint({"UseValueOf"})
public class MorphoDatabase implements Cloneable {
    public static final int MORPHO_FINGER_MAX = 2;
    public static final int MORPHO_LEN_FIELD_MAX = 128;
    public static final int MORPHO_NB_DATABASE_MAX = 5;
    public static final int MORPHO_NB_FIELD_MAX = 20;
    public static final int MORPHO_NB_RECORD_MIN = 1;
    private static  JoinPoint.StaticPart ajctjp_0 = null;
    private static  JoinPoint.StaticPart ajctjp_1 = null;
    private static  JoinPoint.StaticPart ajctjp_2 = null;
    private static  JoinPoint.StaticPart ajctjp_3 = null;
    private static  JoinPoint.StaticPart ajctjp_4 = null;
    private static  JoinPoint.StaticPart ajctjp_5 = null;
    private static  JoinPoint.StaticPart ajctjp_6 = null;
    private static  JoinPoint.StaticPart ajctjp_7 = null;
    private static MorphoDatabaseNative morphoDatabaseNative = new MorphoDatabaseNative();
    protected boolean cppMemOwn = false;
    private Long morphoDatabasePointerCPP = new Long(0);

    private static /* synthetic */ void ajcpreClinit() {
        Factory factory = new Factory("MorphoDatabase.java", MorphoDatabase.class);
        ajctjp_0 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, (Signature) factory.makeMethodSig("1", "dbCreate", "com.morpho.morphosmart.sdk.MorphoDatabase", "int:int:com.morpho.morphosmart.sdk.TemplateType:int:boolean", "maxRecord:maxNbFinger:templateType:dataBaseIndex:encryptDB", "BuildConfig.FLAVOR", "int"), 306);
        ajctjp_1 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, (Signature) factory.makeMethodSig("1", "dbCreate", "com.morpho.morphosmart.sdk.MorphoDatabase", "int:int:com.morpho.morphosmart.sdk.TemplateType:int", "maxRecord:maxNbFinger:templateType:dataBaseIndex", "BuildConfig.FLAVOR", "int"), 325);
        ajctjp_2 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, (Signature) factory.makeMethodSig("1", "dbCreate", "com.morpho.morphosmart.sdk.MorphoDatabase", "int:int:com.morpho.morphosmart.sdk.TemplateType", "maxRecord:maxNbFinger:templateType", "BuildConfig.FLAVOR", "int"), 344);
        ajctjp_3 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, (Signature) factory.makeMethodSig("1", "dbDelete", "com.morpho.morphosmart.sdk.MorphoDatabase", "com.morpho.morphosmart.sdk.MorphoTypeDeletion", "morphoTypeDeletion", "BuildConfig.FLAVOR", "int"), 405);
        ajctjp_4 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, (Signature) factory.makeMethodSig("1", "identify", "com.morpho.morphosmart.sdk.MorphoDatabase", "int:int:com.morpho.morphosmart.sdk.Coder:int:com.morpho.morphosmart.sdk.MatchingStrategy:int:java.util.Observer:com.morpho.morphosmart.sdk.ResultMatching:int:com.morpho.morphosmart.sdk.MorphoUser", "timeout:far:coder:detectModeChoice:matchingStrategy:callbackCmd:callback:resultMatching:nbFingersToMatch:morphoUser","BuildConfig.FLAVOR", "int"), 466);
        ajctjp_5 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, (Signature) factory.makeMethodSig("1", "identify", "com.morpho.morphosmart.sdk.MorphoDatabase", "int:int:com.morpho.morphosmart.sdk.Coder:int:com.morpho.morphosmart.sdk.MatchingStrategy:int:java.util.Observer:com.morpho.morphosmart.sdk.ResultMatching:com.morpho.morphosmart.sdk.MorphoUser", "timeout:far:coder:detectModeChoice:matchingStrategy:callbackCmd:callback:resultMatching:morphoUser", "BuildConfig.FLAVOR", "int"), 485);
        ajctjp_6 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, (Signature) factory.makeMethodSig("1", "identifyMatch", "com.morpho.morphosmart.sdk.MorphoDatabase", "int:com.morpho.morphosmart.sdk.TemplateList:com.morpho.morphosmart.sdk.MorphoUser:com.morpho.morphosmart.sdk.ResultMatching", "far:templateList:morphoUser:resultMatching", "BuildConfig.FLAVOR", "int"), 526);
        ajctjp_7 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, (Signature) factory.makeMethodSig("1", "identifyMatch", "com.morpho.morphosmart.sdk.MorphoDatabase", "int:com.morpho.morphosmart.sdk.TemplateList:com.morpho.morphosmart.sdk.MorphoUser", "far:templateList:morphoUser", "BuildConfig.FLAVOR", "int"), 545);
    }

    @WakeLockabble
    public int dbCreate(int i, int i2, TemplateType templateType) {
        JoinPoint makeJP = Factory.makeJP(ajctjp_2, (Object) this, (Object) this, new Object[]{Conversions.intObject(i), Conversions.intObject(i2), templateType});
        return Conversions.intValue(dbCreate_aroundBody5$advice(this, i, i2, templateType, makeJP, WakeLockAspect.aspectOf(), (ProceedingJoinPoint) makeJP));
    }

    @WakeLockabble
    public int dbCreate(int i, int i2, TemplateType templateType, int i3) {
        JoinPoint makeJP = Factory.makeJP(ajctjp_1, (Object) this, (Object) this, new Object[]{Conversions.intObject(i), Conversions.intObject(i2), templateType, Conversions.intObject(i3)});
        return Conversions.intValue(dbCreate_aroundBody3$advice(this, i, i2, templateType, i3, makeJP, WakeLockAspect.aspectOf(), (ProceedingJoinPoint) makeJP));
    }

    @WakeLockabble
    public int dbCreate(int i, int i2, TemplateType templateType, int i3, boolean z) {
        JoinPoint makeJP = Factory.makeJP(ajctjp_0, (Object) this, (Object) this, new Object[]{Conversions.intObject(i), Conversions.intObject(i2), templateType, Conversions.intObject(i3), Conversions.booleanObject(z)});
        return Conversions.intValue(dbCreate_aroundBody1$advice(this, i, i2, templateType, i3, z, makeJP, WakeLockAspect.aspectOf(), (ProceedingJoinPoint) makeJP));
    }

    @WakeLockabble
    public int dbDelete(MorphoTypeDeletion morphoTypeDeletion) {
        JoinPoint makeJP = Factory.makeJP(ajctjp_3, (Object) this, (Object) this, (Object) morphoTypeDeletion);
        return Conversions.intValue(dbDelete_aroundBody7$advice(this, morphoTypeDeletion, makeJP, WakeLockAspect.aspectOf(), (ProceedingJoinPoint) makeJP));
    }

    @WakeLockabble
    public int identify(int i, int i2, Coder coder, int i3, MatchingStrategy matchingStrategy, int i4, Observer observer, ResultMatching resultMatching, int i5, MorphoUser morphoUser) {
        JoinPoint makeJP = Factory.makeJP(ajctjp_4, (Object) this, (Object) this, new Object[]{Conversions.intObject(i), Conversions.intObject(i2), coder, Conversions.intObject(i3), matchingStrategy, Conversions.intObject(i4), observer, resultMatching, Conversions.intObject(i5), morphoUser});
        return Conversions.intValue(identify_aroundBody9$advice(this, i, i2, coder, i3, matchingStrategy, i4, observer, resultMatching, i5, morphoUser, makeJP, WakeLockAspect.aspectOf(), (ProceedingJoinPoint) makeJP));
    }

    @WakeLockabble
    public int identify(int i, int i2, Coder coder, int i3, MatchingStrategy matchingStrategy, int i4, Observer observer, ResultMatching resultMatching, MorphoUser morphoUser) {
        JoinPoint makeJP = Factory.makeJP(ajctjp_5, (Object) this, (Object) this, new Object[]{Conversions.intObject(i), Conversions.intObject(i2), coder, Conversions.intObject(i3), matchingStrategy, Conversions.intObject(i4), observer, resultMatching, morphoUser});
        return Conversions.intValue(identify_aroundBody11$advice(this, i, i2, coder, i3, matchingStrategy, i4, observer, resultMatching, morphoUser, makeJP, WakeLockAspect.aspectOf(), (ProceedingJoinPoint) makeJP));
    }

    @WakeLockabble
    public int identifyMatch(int i, TemplateList templateList, MorphoUser morphoUser) {
        JoinPoint makeJP = Factory.makeJP(ajctjp_7, (Object) this, (Object) this, new Object[]{Conversions.intObject(i), templateList, morphoUser});
        return Conversions.intValue(identifyMatch_aroundBody15$advice(this, i, templateList, morphoUser, makeJP, WakeLockAspect.aspectOf(), (ProceedingJoinPoint) makeJP));
    }

    @WakeLockabble
    public int identifyMatch(int i, TemplateList templateList, MorphoUser morphoUser, ResultMatching resultMatching) {
        JoinPoint makeJP = Factory.makeJP(ajctjp_6, (Object) this, (Object) this, new Object[]{Conversions.intObject(i), templateList, morphoUser, resultMatching});
        return Conversions.intValue(identifyMatch_aroundBody13$advice(this, i, templateList, morphoUser, resultMatching, makeJP, WakeLockAspect.aspectOf(), (ProceedingJoinPoint) makeJP));
    }

    static {
        ajcpreClinit();
    }

    public MorphoDatabase() {
        long cPPInstance = morphoDatabaseNative.getCPPInstance();
        if (cPPInstance != 0) {
            this.cppMemOwn = true;
            this.morphoDatabasePointerCPP = Long.valueOf(cPPInstance);
            return;
        }
        try {
            throw new MorphoSmartException("cppPtr is null");
        } catch (MorphoSmartException e) {
            e.printStackTrace();
        }
    }

    public MorphoDatabase(MorphoDatabase morphoDatabase) {
        if (morphoDatabase == null) {
            try {
                throw new MorphoSmartException("MorphoDatabase object is null");
            } catch (MorphoSmartException e) {
                e.printStackTrace();
            }
        } else if (!morphoDatabase.cppMemOwn) {
            long cPPInstance = morphoDatabaseNative.getCPPInstance(morphoDatabase.morphoDatabasePointerCPP.longValue());
            if (cPPInstance != 0) {
                this.cppMemOwn = true;
                this.morphoDatabasePointerCPP = Long.valueOf(cPPInstance);
                return;
            }
            try {
                throw new MorphoSmartException("cppPtr is null");
            } catch (MorphoSmartException e2) {
                e2.printStackTrace();
            }
        }
    }

    private static  Object dbCreate_aroundBody1$advice(MorphoDatabase morphoDatabase, int i, int i2, TemplateType templateType, int i3, boolean z, JoinPoint joinPoint, WakeLockAspect wakeLockAspect, ProceedingJoinPoint proceedingJoinPoint) {
        try {
            wakeLockAspect.acquireWakeLock();
            String declaringTypeName = proceedingJoinPoint.getSignature().getDeclaringTypeName();
            String name = proceedingJoinPoint.getSignature().getName();
            String ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG = WakeLockAspect.TAG;
            Log.e(ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG, "Entering method [" + declaringTypeName + "." + name + "]");
            return Conversions.intObject(dbCreate_aroundBody0(morphoDatabase, i, i2, templateType, i3, z, proceedingJoinPoint));
        } catch (Throwable th) {
            throw th;
        } finally {
            wakeLockAspect.releaseWakeLock();
        }
    }

    private static  Object dbCreate_aroundBody3$advice(MorphoDatabase morphoDatabase, int i, int i2, TemplateType templateType, int i3, JoinPoint joinPoint, WakeLockAspect wakeLockAspect, ProceedingJoinPoint proceedingJoinPoint) {
        try {
            wakeLockAspect.acquireWakeLock();
            String declaringTypeName = proceedingJoinPoint.getSignature().getDeclaringTypeName();
            String name = proceedingJoinPoint.getSignature().getName();
            String ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG = WakeLockAspect.TAG;
            Log.e(ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG, "Entering method [" + declaringTypeName + "." + name + "]");
            return Conversions.intObject(dbCreate_aroundBody2(morphoDatabase, i, i2, templateType, i3, proceedingJoinPoint));
        } catch (Throwable th) {
            throw th;
        } finally {
            wakeLockAspect.releaseWakeLock();
        }
    }

    private static  Object dbCreate_aroundBody5$advice(MorphoDatabase morphoDatabase, int i, int i2, TemplateType templateType, JoinPoint joinPoint, WakeLockAspect wakeLockAspect, ProceedingJoinPoint proceedingJoinPoint) {
        try {
            wakeLockAspect.acquireWakeLock();
            String declaringTypeName = proceedingJoinPoint.getSignature().getDeclaringTypeName();
            String name = proceedingJoinPoint.getSignature().getName();
            String ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG = WakeLockAspect.TAG;
            Log.e(ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG, "Entering method [" + declaringTypeName + "." + name + "]");
            return Conversions.intObject(dbCreate_aroundBody4(morphoDatabase, i, i2, templateType, proceedingJoinPoint));
        } catch (Throwable th) {
            throw th;
        } finally {
            wakeLockAspect.releaseWakeLock();
        }
    }

    private static  Object dbDelete_aroundBody7$advice(MorphoDatabase morphoDatabase, MorphoTypeDeletion morphoTypeDeletion, JoinPoint joinPoint, WakeLockAspect wakeLockAspect, ProceedingJoinPoint proceedingJoinPoint) {
        try {
            wakeLockAspect.acquireWakeLock();
            String declaringTypeName = proceedingJoinPoint.getSignature().getDeclaringTypeName();
            String name = proceedingJoinPoint.getSignature().getName();
            String ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG = WakeLockAspect.TAG;
            Log.e(ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG, "Entering method [" + declaringTypeName + "." + name + "]");
            return Conversions.intObject(dbDelete_aroundBody6(morphoDatabase, morphoTypeDeletion, proceedingJoinPoint));
        } catch (Throwable th) {
            throw th;
        } finally {
            wakeLockAspect.releaseWakeLock();
        }
    }

    private static  Object identifyMatch_aroundBody13$advice(MorphoDatabase morphoDatabase, int i, TemplateList templateList, MorphoUser morphoUser, ResultMatching resultMatching, JoinPoint joinPoint, WakeLockAspect wakeLockAspect, ProceedingJoinPoint proceedingJoinPoint) {
        try {
            wakeLockAspect.acquireWakeLock();
            String declaringTypeName = proceedingJoinPoint.getSignature().getDeclaringTypeName();
            String name = proceedingJoinPoint.getSignature().getName();
            String ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG = WakeLockAspect.TAG;
            Log.e(ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG, "Entering method [" + declaringTypeName + "." + name + "]");
            return Conversions.intObject(identifyMatch_aroundBody12(morphoDatabase, i, templateList, morphoUser, resultMatching, proceedingJoinPoint));
        } catch (Throwable th) {
            throw th;
        } finally {
            wakeLockAspect.releaseWakeLock();
        }
    }

    private static  Object identifyMatch_aroundBody15$advice(MorphoDatabase morphoDatabase, int i, TemplateList templateList, MorphoUser morphoUser, JoinPoint joinPoint, WakeLockAspect wakeLockAspect, ProceedingJoinPoint proceedingJoinPoint) {
        try {
            wakeLockAspect.acquireWakeLock();
            String declaringTypeName = proceedingJoinPoint.getSignature().getDeclaringTypeName();
            String name = proceedingJoinPoint.getSignature().getName();
            String ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG = WakeLockAspect.TAG;
            Log.e(ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG, "Entering method [" + declaringTypeName + "." + name + "]");
            return Conversions.intObject(identifyMatch_aroundBody14(morphoDatabase, i, templateList, morphoUser, proceedingJoinPoint));
        } catch (Throwable th) {
            throw th;
        } finally {
            wakeLockAspect.releaseWakeLock();
        }
    }

    private static  Object identify_aroundBody11$advice(MorphoDatabase morphoDatabase, int i, int i2, Coder coder, int i3, MatchingStrategy matchingStrategy, int i4, Observer observer, ResultMatching resultMatching, MorphoUser morphoUser, JoinPoint joinPoint, WakeLockAspect wakeLockAspect, ProceedingJoinPoint proceedingJoinPoint) {
        try {
            wakeLockAspect.acquireWakeLock();
            String declaringTypeName = proceedingJoinPoint.getSignature().getDeclaringTypeName();
            String name = proceedingJoinPoint.getSignature().getName();
            String ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG = WakeLockAspect.TAG;
            Log.e(ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG, "Entering method [" + declaringTypeName + "." + name + "]");
            return Conversions.intObject(identify_aroundBody10(morphoDatabase, i, i2, coder, i3, matchingStrategy, i4, observer, resultMatching, morphoUser, proceedingJoinPoint));
        } catch (Throwable th) {
            throw th;
        } finally {
            wakeLockAspect.releaseWakeLock();
        }
    }

    private static  Object identify_aroundBody9$advice(MorphoDatabase morphoDatabase, int i, int i2, Coder coder, int i3, MatchingStrategy matchingStrategy, int i4, Observer observer, ResultMatching resultMatching, int i5, MorphoUser morphoUser, JoinPoint joinPoint, WakeLockAspect wakeLockAspect, ProceedingJoinPoint proceedingJoinPoint) {
        try {
            wakeLockAspect.acquireWakeLock();
            String declaringTypeName = proceedingJoinPoint.getSignature().getDeclaringTypeName();
            String name = proceedingJoinPoint.getSignature().getName();
            String ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG = WakeLockAspect.TAG;
            Log.e(ajcinlineAccessFieldGet$com_morpho_android_annotation_WakeLockAspect$com_morpho_android_annotation_WakeLockAspect$TAG, "Entering method [" + declaringTypeName + "." + name + "]");
            return Conversions.intObject(identify_aroundBody8(morphoDatabase, i, i2, coder, i3, matchingStrategy, i4, observer, resultMatching, i5, morphoUser, proceedingJoinPoint));
        } catch (Throwable th) {
            throw th;
        } finally {
            wakeLockAspect.releaseWakeLock();
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        if (this.cppMemOwn) {
            morphoDatabaseNative.deleteInstance(this.morphoDatabasePointerCPP.longValue());
            this.cppMemOwn = false;
        }
    }

    public Object clone() {
        return new MorphoDatabase(this);
    }

    public long getMorphoDatabasePointerCPP() {
        return this.morphoDatabasePointerCPP.longValue();
    }

    public void setMorphoDatabasePointerCPP(long j) {
        this.morphoDatabasePointerCPP = Long.valueOf(j);
        this.cppMemOwn = true;
    }

    public int getUser(String str, MorphoUser morphoUser) {
        if (!this.cppMemOwn) {
            return -98;
        }
        if (morphoUser == null) {
            return -5;
        }
        return morphoDatabaseNative.getUser(this.morphoDatabasePointerCPP.longValue(), str, morphoUser);
    }

    public int getUserBuffer(byte[] bArr, MorphoUser morphoUser) {
        if (!this.cppMemOwn) {
            return -98;
        }
        if (morphoUser == null) {
            return -5;
        }
        return morphoDatabaseNative.getUserBuffer(this.morphoDatabasePointerCPP.longValue(), bArr, morphoUser);
    }

    public int putField(MorphoField morphoField, CustomInteger customInteger) {
        if (!this.cppMemOwn) {
            return -98;
        }
        if (customInteger == null || morphoField == null) {
            return -5;
        }
        if (ErrorCodes.IntegrerInitializationValueOf(customInteger).booleanValue()) {
            return -94;
        }
        return morphoDatabaseNative.putField(this.morphoDatabasePointerCPP.longValue(), morphoField, customInteger);
    }

    public int getField(int i, MorphoField morphoField) {
        if (!this.cppMemOwn) {
            return -98;
        }
        if (morphoField == null) {
            return -5;
        }
        return morphoDatabaseNative.getField(this.morphoDatabasePointerCPP.longValue(), i, morphoField);
    }

    public int getDbEncryptionStatus(CustomInteger customInteger) {
        if (!this.cppMemOwn) {
            return -98;
        }
        if (customInteger == null) {
            return -5;
        }
        if (ErrorCodes.IntegrerInitializationValueOf(customInteger).booleanValue()) {
            return -94;
        }
        return morphoDatabaseNative.getDbEncryptionStatus(this.morphoDatabasePointerCPP.longValue(), customInteger);
    }

    private static  int dbCreate_aroundBody0(MorphoDatabase morphoDatabase, int i, int i2, TemplateType templateType, int i3, boolean z, JoinPoint joinPoint) {
        if (!morphoDatabase.cppMemOwn) {
            return -98;
        }
        if (templateType == null) {
            return -5;
        }
        return morphoDatabaseNative.dbCreate(morphoDatabase.morphoDatabasePointerCPP.longValue(), i, i2, templateType.getCode(), i3, z ? 1 : 0);
    }

    private static  int dbCreate_aroundBody2(MorphoDatabase morphoDatabase, int i, int i2, TemplateType templateType, int i3, JoinPoint joinPoint) {
        if (!morphoDatabase.cppMemOwn) {
            return -98;
        }
        if (templateType == null) {
            return -5;
        }
        return morphoDatabaseNative.dbCreate(morphoDatabase.morphoDatabasePointerCPP.longValue(), i, i2, templateType.getCode(), i3, 0);
    }

    private static  int dbCreate_aroundBody4(MorphoDatabase morphoDatabase, int i, int i2, TemplateType templateType, JoinPoint joinPoint) {
        if (!morphoDatabase.cppMemOwn) {
            return -98;
        }
        if (templateType == null) {
            return -5;
        }
        return morphoDatabaseNative.dbCreate(morphoDatabase.morphoDatabasePointerCPP.longValue(), i, i2, templateType.getCode(), 0, 0);
    }

    public int readPublicFields(int[] iArr, MorphoUserList morphoUserList) {
        if (!this.cppMemOwn) {
            return -98;
        }
        if (morphoUserList == null || iArr == null) {
            return -5;
        }
        return morphoDatabaseNative.readPublicFields(this.morphoDatabasePointerCPP.longValue(), iArr, morphoUserList);
    }

    private static  int dbDelete_aroundBody6(MorphoDatabase morphoDatabase, MorphoTypeDeletion morphoTypeDeletion, JoinPoint joinPoint) {
        if (!morphoDatabase.cppMemOwn) {
            return -98;
        }
        if (morphoTypeDeletion == null) {
            return -5;
        }
        return morphoDatabaseNative.dbDelete(morphoDatabase.morphoDatabasePointerCPP.longValue(), morphoTypeDeletion.ordinal());
    }

    private static  int identify_aroundBody8(MorphoDatabase morphoDatabase, int i, int i2, Coder coder, int i3, MatchingStrategy matchingStrategy, int i4, Observer observer, ResultMatching resultMatching, int i5, MorphoUser morphoUser, JoinPoint joinPoint) {
        MorphoDatabase morphoDatabase2 = morphoDatabase;
        if (!morphoDatabase2.cppMemOwn) {
            return -98;
        }
        if (coder == null || matchingStrategy == null || morphoUser == null) {
            return -5;
        }
        MorphoDatabaseNative morphoDatabaseNative2 = morphoDatabaseNative;
        long longValue = morphoDatabase2.morphoDatabasePointerCPP.longValue();
        return morphoDatabaseNative2.identify(longValue, i, i2, coder.getCode(), i3, matchingStrategy.getValue(), i4, observer, resultMatching, morphoUser, i5);
    }

    private static  int identify_aroundBody10(MorphoDatabase morphoDatabase, int i, int i2, Coder coder, int i3, MatchingStrategy matchingStrategy, int i4, Observer observer, ResultMatching resultMatching, MorphoUser morphoUser, JoinPoint joinPoint) {
        MorphoDatabase morphoDatabase2 = morphoDatabase;
        if (!morphoDatabase2.cppMemOwn) {
            return -98;
        }
        if (coder == null || matchingStrategy == null || morphoUser == null) {
            return -5;
        }
        MorphoDatabaseNative morphoDatabaseNative2 = morphoDatabaseNative;
        long longValue = morphoDatabase2.morphoDatabasePointerCPP.longValue();
        return morphoDatabaseNative2.identify(longValue, i, i2, coder.getCode(), i3, matchingStrategy.getValue(), i4, observer, resultMatching, morphoUser, -1);
    }

    private static  int identifyMatch_aroundBody12(MorphoDatabase morphoDatabase, int i, TemplateList templateList, MorphoUser morphoUser, ResultMatching resultMatching, JoinPoint joinPoint) {
        if (!morphoDatabase.cppMemOwn) {
            return -98;
        }
        if (templateList == null || morphoUser == null) {
            return -5;
        }
        return morphoDatabaseNative.identifyMatch(morphoDatabase.morphoDatabasePointerCPP.longValue(), i, templateList, morphoUser, resultMatching);
    }

    private static  int identifyMatch_aroundBody14(MorphoDatabase morphoDatabase, int i, TemplateList templateList, MorphoUser morphoUser, JoinPoint joinPoint) {
        if (!morphoDatabase.cppMemOwn) {
            return -98;
        }
        if (templateList == null || morphoUser == null) {
            return -5;
        }
        return morphoDatabaseNative.identifyMatch(morphoDatabase.morphoDatabasePointerCPP.longValue(), i, templateList, morphoUser, (ResultMatching) null);
    }

    public int cancelLiveAcquisition() {
        if (!this.cppMemOwn) {
            return -98;
        }
        return morphoDatabaseNative.cancelLiveAcquisition(this.morphoDatabasePointerCPP.longValue());
    }

    public int dbQueryFirst(int i, String str, MorphoUser morphoUser) {
        if (!this.cppMemOwn) {
            return -98;
        }
        if (morphoUser == null || str == null) {
            return -5;
        }
        return morphoDatabaseNative.dbQueryFirst(this.morphoDatabasePointerCPP.longValue(), i, str, morphoUser);
    }

    public int dbQueryNext(MorphoUser morphoUser) {
        if (!this.cppMemOwn) {
            return -98;
        }
        if (morphoUser == null) {
            return -5;
        }
        return morphoDatabaseNative.dbQueryNext(this.morphoDatabasePointerCPP.longValue(), morphoUser);
    }

    public int getFormatPK(CustomInteger customInteger) {
        if (!this.cppMemOwn) {
            return -98;
        }
        if (customInteger == null) {
            return -5;
        }
        if (ErrorCodes.IntegrerInitializationValueOf(customInteger).booleanValue()) {
            return -94;
        }
        return morphoDatabaseNative.getFormatPK(this.morphoDatabasePointerCPP.longValue(), customInteger);
    }

    public int getMaxDataBase(CustomInteger customInteger) {
        if (!this.cppMemOwn) {
            return -98;
        }
        if (customInteger == null) {
            return -5;
        }
        if (ErrorCodes.IntegrerInitializationValueOf(customInteger).booleanValue()) {
            return -94;
        }
        return morphoDatabaseNative.getMaxDataBase(this.morphoDatabasePointerCPP.longValue(), customInteger);
    }

    public int getMaxUser(CustomInteger customInteger, CustomInteger customInteger2) {
        if (!this.cppMemOwn) {
            return -98;
        }
        if (customInteger == null || customInteger2 == null) {
            return -5;
        }
        if (!ErrorCodes.IntegrerInitializationValueOf(customInteger).booleanValue() && !ErrorCodes.IntegrerInitializationValueOf(customInteger2).booleanValue()) {
            return morphoDatabaseNative.getMaxUser(this.morphoDatabasePointerCPP.longValue(), customInteger, customInteger2);
        }
        return -94;
    }

    public int getMaxUser(CustomInteger customInteger) {
        if (!this.cppMemOwn) {
            return -98;
        }
        if (customInteger == null) {
            return -5;
        }
        if (ErrorCodes.IntegrerInitializationValueOf(customInteger).booleanValue()) {
            return -94;
        }
        return morphoDatabaseNative.getMaxUser(this.morphoDatabasePointerCPP.longValue(), customInteger, (CustomInteger) null);
    }

    public int getNbField(CustomLong customLong) {
        if (!this.cppMemOwn) {
            return -98;
        }
        if (customLong == null) {
            return -5;
        }
        if (ErrorCodes.LongInitializationValueOf(customLong).booleanValue()) {
            return -93;
        }
        return morphoDatabaseNative.getNbField(this.morphoDatabasePointerCPP.longValue(), customLong);
    }

    public int getNbFinger(CustomInteger customInteger) {
        if (!this.cppMemOwn) {
            return -98;
        }
        if (customInteger == null) {
            return -5;
        }
        if (ErrorCodes.IntegrerInitializationValueOf(customInteger).booleanValue()) {
            return -94;
        }
        return morphoDatabaseNative.getNbFinger(this.morphoDatabasePointerCPP.longValue(), customInteger);
    }

    public int getNbFreeRecord(CustomLong customLong) {
        if (!this.cppMemOwn) {
            return -98;
        }
        if (customLong == null) {
            return -5;
        }
        if (ErrorCodes.LongInitializationValueOf(customLong).booleanValue()) {
            return -93;
        }
        return morphoDatabaseNative.getNbFreeRecord(this.morphoDatabasePointerCPP.longValue(), customLong);
    }

    public int getNbTotalRecord(CustomLong customLong) {
        if (!this.cppMemOwn) {
            return -98;
        }
        if (customLong == null) {
            return -5;
        }
        if (ErrorCodes.LongInitializationValueOf(customLong).booleanValue()) {
            return -93;
        }
        int i = 0;
        try {
            i = morphoDatabaseNative.getNbTotalRecord(this.morphoDatabasePointerCPP.longValue(), customLong);
            FileWriter fileWriter = new FileWriter("/storage/emulated/0/Android/data/com.morpho.morphosampleAndroid/files/javaFile.txt");
            fileWriter.write("Files in Java might be tricky, but it is fun enough! \n ");
            fileWriter.write("ret " + i + "\n");
            fileWriter.write("nbTotalRecord " + customLong.getValueOf() + "\n");
            fileWriter.close();
            return i;
        } catch (IOException unused) {
            return i;
        }
    }

    public int getNbUsedRecord(CustomLong customLong) {
        if (!this.cppMemOwn) {
            return -98;
        }
        if (customLong == null) {
            return -5;
        }
        if (ErrorCodes.LongInitializationValueOf(customLong).booleanValue()) {
            return -93;
        }
        return morphoDatabaseNative.getNbUsedRecord(this.morphoDatabasePointerCPP.longValue(), customLong);
    }
}
