package com.utex.mvp.depth.bean;

import java.util.List;

/**
 * Created by Demon on 2018/5/22.
 */
public class Depth {

    List<List<String>> bids;


    List<List<String>> asks;

    public List<List<String>> getBids() {
        return bids;
    }

    public void setBids(List<List<String>> bids) {
        this.bids = bids;
    }

    public List<List<String>> getAsks() {
        return asks;
    }

    public void setAsks(List<List<String>> asks) {
        this.asks = asks;
    }
}
