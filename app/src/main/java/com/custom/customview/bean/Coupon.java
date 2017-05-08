package com.custom.customview.bean;

/**
 * Created by Jone on 17/5/3.
 */
public class Coupon {

    private String price ;

    private String kind;

    private boolean mFirstShow;


    public Coupon(String price, String kind,boolean firstShow) {
        this.price = price;
        this.kind = kind;
        this.mFirstShow =firstShow;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public boolean isFirstShow() {
        return mFirstShow;
    }

    public void setFirstShow(boolean firstShow) {
        mFirstShow = firstShow;
    }
}
