package com.example.mymess;

public class MessMenuProduct {
    private String mess;
    private int imageResId;

    public MessMenuProduct(String mess, int imageResId) {
        this.mess = mess;
        this.imageResId = imageResId;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
}
