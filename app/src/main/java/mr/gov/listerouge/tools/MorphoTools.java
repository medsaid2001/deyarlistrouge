package mr.gov.listerouge.tools;

import android.util.Pair;

import com.facebook.shimmer.BuildConfig;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MorphoTools {
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
    private static final Map<Pair<Integer, Integer>, String> supportedDevices = new HashMap();

    static {
        supportedDevices.put(new Pair(1947, 35), "MSO100");
        supportedDevices.put(new Pair(1947, 36), "MSO300");
        supportedDevices.put(new Pair(1947, 38), "MSO350");
        supportedDevices.put(new Pair(1947, 71), "CBM");
        supportedDevices.put(new Pair(1947, 82), "MSO1350");
        supportedDevices.put(new Pair(8797, 1), "MSO FVP");
        supportedDevices.put(new Pair(8797, 2), "MSO FVP_C");
        supportedDevices.put(new Pair(8797, 3), "MSO FVP_CL");
        supportedDevices.put(new Pair(8797, 7), "MEPUSB");
        supportedDevices.put(new Pair(8797, 8), "CBM-E3");
        supportedDevices.put(new Pair(8797, 9), "CBM-V3");
        supportedDevices.put(new Pair(8797, 10), "MSO1300-E3");
        supportedDevices.put(new Pair(8797, 11), "MSO1300-V3");
        supportedDevices.put(new Pair(8797, 12), "MSO1350-E3");
        supportedDevices.put(new Pair(8797, 13), "MSO1350-V3");
        supportedDevices.put(new Pair(8797, 14), "MA SIGMA");
    }

    public static synchronized boolean isSupported(int i, int i2) {
        synchronized (MorphoTools.class) {
            for (Pair next : supportedDevices.keySet()) {
                if (((Integer) next.first).intValue() == i && ((Integer) next.second).intValue() == i2) {
                    return true;
                }
            }
            return false;
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(5:9|10|11|12|13) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x001e */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x002b A[SYNTHETIC, Splitter:B:21:0x002b] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0030 A[SYNTHETIC, Splitter:B:25:0x0030] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.io.ByteArrayOutputStream ReadFile(java.io.File r4) throws java.io.IOException {
        /*
            r0 = 4096(0x1000, float:5.74E-42)
            r1 = 0
            byte[] r0 = new byte[r0]     // Catch:{ all -> 0x0027 }
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream     // Catch:{ all -> 0x0027 }
            r2.<init>()     // Catch:{ all -> 0x0027 }
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ all -> 0x0025 }
            r3.<init>(r4)     // Catch:{ all -> 0x0025 }
        L_0x000f:
            int r4 = r3.read(r0)     // Catch:{ all -> 0x0022 }
            r1 = -1
            if (r4 == r1) goto L_0x001b
            r1 = 0
            r2.write(r0, r1, r4)     // Catch:{ all -> 0x0022 }
            goto L_0x000f
        L_0x001b:
            r2.close()     // Catch:{ IOException -> 0x001e }
        L_0x001e:
            r3.close()     // Catch:{ IOException -> 0x0021 }
        L_0x0021:
            return r2
        L_0x0022:
            r4 = move-exception
            r1 = r3
            goto L_0x0029
        L_0x0025:
            r4 = move-exception
            goto L_0x0029
        L_0x0027:
            r4 = move-exception
            r2 = r1
        L_0x0029:
            if (r2 == 0) goto L_0x002e
            r2.close()     // Catch:{ IOException -> 0x002e }
        L_0x002e:
            if (r1 == 0) goto L_0x0033
            r1.close()     // Catch:{ IOException -> 0x0033 }
        L_0x0033:
            goto L_0x0035
        L_0x0034:
            throw r4
        L_0x0035:
            goto L_0x0034
        */
        throw new UnsupportedOperationException("Method not decompiled: com.morpho.morphosample.tools.MorphoTools.ReadFile(java.io.File):java.io.ByteArrayOutputStream");
    }

    public static String checkfield(String str, boolean z) {
        return (!z && str.equalsIgnoreCase(BuildConfig.FLAVOR)) ? "<None>" : str;
    }

    public static byte[] checkfield(byte[] bArr, boolean z) {
        return (!z && bArr.length == 0) ? "<None>".getBytes() : bArr;
    }

    public static byte[] toPrimitives(Byte[] bArr) {
        byte[] bArr2 = new byte[bArr.length];
        for (int i = 0; i < bArr.length; i++) {
            bArr2[i] = bArr[i].byteValue();
        }
        return bArr2;
    }

    public static byte[] toByteArray(ArrayList<Byte> arrayList) {
        return toPrimitives((Byte[]) arrayList.toArray(new Byte[arrayList.size()]));
    }

    public static long fourBytesToLongValue(byte[] bArr) {
        ByteBuffer wrap = ByteBuffer.wrap(new byte[]{bArr[0], bArr[1], bArr[2], bArr[3], 0, 0, 0, 0});
        wrap.order(ByteOrder.BIG_ENDIAN);
        return ((long) wrap.getInt()) & 4294967295L;
    }

    public static byte[] longToFourByteBuffer(long j, boolean z) {
        byte[] bArr = new byte[4];
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        if (z) {
            wrap.order(ByteOrder.LITTLE_ENDIAN);
        }
        wrap.putInt((int) j);
        return bArr;
    }
}
