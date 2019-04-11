package com.utex.mvp.home.bean;

import java.io.Serializable;

/**
 * Created by Demon on 2018/5/30.
 */
public class HourData implements Serializable {
    /**
     * error : null
     * result : {"open":"1","last":"2","high":"8121","low":"1","volume":"187","deal":"9163.59"}
     * id : 10
     */

    private Object error;
    private TickerData result;
    private int id;
    private String method;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public TickerData getResult() {
        return result;
    }

    public void setResult(TickerData result) {
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "HourData{" +
                "error=" + error +
                ", result=" + result +
                ", id=" + id +
                '}';
    }
}
