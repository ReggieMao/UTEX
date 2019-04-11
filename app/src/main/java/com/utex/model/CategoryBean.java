package com.utex.model;

/**
 * Created by admin on 2017/5/22.
 */

public class CategoryBean {
    private String marketName;
    private String coinTag;
    private double increace;
    private double rmb;
    private double dollar;
    private double height;
    private double open;
    private double low;

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getCoinTag() {
        return coinTag;
    }

    public void setCoinTag(String coinTag) {
        this.coinTag = coinTag;
    }

    public double getIncreace() {
        return increace;
    }

    public void setIncreace(double increace) {
        this.increace = increace;
    }

    public double getRmb() {
        return rmb;
    }

    public void setRmb(double rmb) {
        this.rmb = rmb;
    }

    public double getDollar() {
        return dollar;
    }

    public void setDollar(double dollar) {
        this.dollar = dollar;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }
}
