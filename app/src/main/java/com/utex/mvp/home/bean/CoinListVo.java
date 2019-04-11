package com.utex.mvp.home.bean;

import com.utex.bean.TickerDo;

import java.util.List;

/**
 * Created by Demon on 2018/5/29.
 */
public class CoinListVo {

    private boolean success;

    private List<TickerDo> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<TickerDo> getData() {
        return data;
    }

    public void setData(List<TickerDo> data) {
        this.data = data;
    }

}
