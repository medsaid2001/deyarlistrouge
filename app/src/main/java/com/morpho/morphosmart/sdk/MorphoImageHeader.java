package com.morpho.morphosmart.sdk;

public class MorphoImageHeader {
    private int compressionRatio;
    private int nbBitsPerPixel;
    private int nbColumn;
    private int nbRow;
    private int resX;
    private int resY;

    public int getNbBitsPerPixel() {
        return this.nbBitsPerPixel;
    }

    public void setNbBitsPerPixel(int i) {
        this.nbBitsPerPixel = i;
    }

    public int getResX() {
        return this.resX;
    }

    public void setResX(int i) {
        this.resX = i;
    }

    public int getResY() {
        return this.resY;
    }

    public void setResY(int i) {
        this.resY = i;
    }

    public int getNbColumn() {
        return this.nbColumn;
    }

    public void setNbColumn(int i) {
        this.nbColumn = i;
    }

    public int getNbRow() {
        return this.nbRow;
    }

    public void setNbRow(int i) {
        this.nbRow = i;
    }

    public int getCompressionRatio() {
        return this.compressionRatio;
    }

    public void setCompressionRatio(int i) {
        this.compressionRatio = i;
    }
}
