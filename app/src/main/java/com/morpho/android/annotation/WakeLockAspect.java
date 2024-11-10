package com.morpho.android.annotation;

import android.content.Context;
import android.os.PowerManager;
import android.util.Log;

import org.aspectj.lang.NoAspectBoundException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import com.morpho.android.usb.USBManager;


@Aspect
public class WakeLockAspect {
    public static final String TAG = "WakeLockAspect";
    private static  Throwable ajcinitFailureCause;
    public static WakeLockAspect ajcperSingletonInstance = null;
    private PowerManager.WakeLock wakeLock = null;
    @Pointcut("@annotation(WakeLockabble)")
    public void annotationPointCutDefinition() {
    }
    
    static {
        try {
            ajcperSingletonInstance = new WakeLockAspect();
        } catch (Throwable th) {
            ajcinitFailureCause = th;
        }
    }
    @Pointcut("execution(* *(..))")
    public void atExecution() {
    }
    public static WakeLockAspect aspectOf() {
        WakeLockAspect wakeLockAspect = ajcperSingletonInstance;
        if (wakeLockAspect != null) {
            return wakeLockAspect;
        }
        throw new NoAspectBoundException();
    }
    @Around("@annotation(WakeLockabble) && execution(* *(..))")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            acquireWakeLock();
            String declaringTypeName = proceedingJoinPoint.getSignature().getDeclaringTypeName();
            String name = proceedingJoinPoint.getSignature().getName();
            Log.e(TAG, "Entering method [" + declaringTypeName + "." + name + "]");
            return proceedingJoinPoint.proceed();
        } finally {
            releaseWakeLock();
        }
    }

    public void acquireWakeLock() {
        try {
            USBManager.getInstance();
            if (!USBManager.WakeLockEnabled) {
                Log.e(TAG, "Wakelock not used");
                return;
            }
            PowerManager powerManager = (PowerManager) USBManager.context.getSystemService(Context.POWER_SERVICE);
            if (powerManager != null) {
                // Use a unique prefix followed by a colon for the tag name
                wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "myapp:capture_wakelock");
                wakeLock.acquire();
                Log.e(TAG, "Creates and Acquires a new wake lock.");
            } else {
                Log.e(TAG, "PowerManager is null. Cannot acquire WakeLock.");
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to acquire wakelock : " + e.getMessage());
        }
    }
    public void releaseWakeLock() {
        try {
            USBManager.getInstance();
            if (!USBManager.WakeLockEnabled) {
                Log.e(TAG, "Wakelock not used");
                return;
            }
            if (wakeLock != null && wakeLock.isHeld()) {
                wakeLock.release();
                Log.e(TAG, "Releases the wake lock.");
                wakeLock = null;
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to release wakelock : " + e.getMessage());
        }
    }
}
