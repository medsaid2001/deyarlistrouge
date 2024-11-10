package com.morpho.morphosmart.sdk;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class MorphoImage {
    private byte[] compressedImage;
    private CompressionAlgorithm compressionAlgorithm;
    private byte[] image;
    private MorphoImageHeader morphoImageHeader = new MorphoImageHeader();

    public MorphoImageHeader getMorphoImageHeader() {
        return this.morphoImageHeader;
    }

    public void setMorphoImageHeader(MorphoImageHeader morphoImageHeader2) {
        this.morphoImageHeader = morphoImageHeader2;
    }

    public byte[] getImage() {
        return this.image;
    }

    public void setImage(byte[] bArr) {
        this.image = bArr;
    }

    public void setImage(Object obj) {
        this.image = (byte[]) obj;
    }

    public byte[] getCompressedImage() {
        return this.compressedImage;
    }

    public void setCompressedImage(byte[] bArr) {
        this.compressedImage = bArr;
    }

    public void setCompressedImage(Object obj) {
        this.compressedImage = (byte[]) obj;
    }

    public static MorphoImage getMorphoImageFromLive(byte[] bArr) {
        if (bArr == null || bArr.length < 12) {
            return null;
        }
        MorphoImage morphoImage = new MorphoImage();
        ByteBuffer wrap = ByteBuffer.wrap(bArr, 2, 2);
        wrap.order(ByteOrder.LITTLE_ENDIAN);
        short s = wrap.getShort();
        ByteBuffer wrap2 = ByteBuffer.wrap(bArr, 4, 2);
        wrap2.order(ByteOrder.LITTLE_ENDIAN);
        short s2 = wrap2.getShort();
        ByteBuffer wrap3 = ByteBuffer.wrap(bArr, 6, 2);
        wrap3.order(ByteOrder.LITTLE_ENDIAN);
        short s3 = wrap3.getShort();
        ByteBuffer wrap4 = ByteBuffer.wrap(bArr, 8, 2);
        wrap4.order(ByteOrder.LITTLE_ENDIAN);
        short s4 = wrap4.getShort();
        byte b = bArr[11];
        morphoImage.setCompressionAlgorithm(CompressionAlgorithm.GetCompressionAlgorithm(bArr[10]));
        morphoImage.morphoImageHeader.setNbRow(s);
        morphoImage.morphoImageHeader.setNbColumn(s2);
        morphoImage.morphoImageHeader.setResX(s3);
        morphoImage.morphoImageHeader.setResY(s4);
        morphoImage.morphoImageHeader.setNbBitsPerPixel(b);
        morphoImage.setImage(Arrays.copyOfRange(bArr, 12, bArr.length));
        return morphoImage;
    }

    public CompressionAlgorithm getCompressionAlgorithm() {
        return this.compressionAlgorithm;
    }

    public void setCompressionAlgorithm(CompressionAlgorithm compressionAlgorithm2) {
        this.compressionAlgorithm = compressionAlgorithm2;
    }

    private void setCompressionAlgorithm(int i) {
        this.compressionAlgorithm = CompressionAlgorithm.GetCompressionAlgorithm(i);
    }
}
