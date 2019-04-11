package com.utex.mvp.home.bean;

/**
 * Created by Demon on 2018/5/30.
 */
public class TickerData {
    /**
     * open : 1
     * last : 2
     * high : 8121
     * low : 1
     * volume : 187
     * deal : 9163.59
     */

    private String open;
    private String last;
    private String high;
    private String low;
    private String volume;
    private String deal;
    private String close;
    private Long time;
    private float degree;

    public float getDegree() {
        return degree;
    }

    public void setDegree(float degree) {
        this.degree = degree;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getDeal() {
        return deal;
    }

    public void setDeal(String deal) {
        this.deal = deal;
    }

    @Override
    public String toString() {
        return "ResultBean{" +
                "open='" + open + '\'' +
                ", last='" + last + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", volume='" + volume + '\'' +
                ", deal='" + deal + '\'' +
                '}';
    }
}
