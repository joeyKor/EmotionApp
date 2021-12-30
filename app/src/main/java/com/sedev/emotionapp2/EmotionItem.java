package com.sedev.emotionapp2;

public class
EmotionItem {

    private int mImageResource;
    private String mText1;
    private String mText2;
    private String mDate;

    public EmotionItem(int imageResource, String text1, String text2, String date) {
        mImageResource = imageResource;
        mText1 = text1;
        mText2 = text2;
        mDate = date;

    }

    public int getImageResource() {
        return mImageResource;
    }

    public String getText1() {
        return mText1;
    }

    public String getmDate() {
        return mDate;
    }
    public String getText2() {
        return mText2;
    }
}