package com.utex.mvp.marketdetail.presenter;

import com.alibaba.fastjson.JSONArray;
import com.utex.bean.TickerDo;
import com.utex.mvp.marketdetail.bean.MiTabParamVo;

/**
 * Created by Demon on 2018/6/19.
 */
public interface IMarketDetailFullPresenter {
    void setTime();

    void setData();

    void setIndex();

    void calculationKLine(JSONArray kLine);

    void calculationKLineUpdate(JSONArray result);

    void clearKLineData();

    void coinSubscribe(TickerDo currTicker);

    void KLineQuery(TickerDo currTicker, MiTabParamVo currTime);

    void kLineSubscribe(TickerDo currTicker, MiTabParamVo currTime);
}
