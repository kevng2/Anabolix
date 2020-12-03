package com.android.gang.anabolix;

public class AnabolixFeature {
    private String mName;
    private int mImage;

    public AnabolixFeature(String name, int image) {
        mName = name;
        mImage = image;
    }

    public String getName() {
        return mName;
    }

    public int getImage() {
        return mImage;
    }
}
