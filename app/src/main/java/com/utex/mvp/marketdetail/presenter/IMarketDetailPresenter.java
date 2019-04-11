package com.utex.mvp.marketdetail.presenter;

import com.alibaba.fastjson.JSONArray;
import com.utex.bean.TickerDo;
import com.utex.mvp.marketdetail.bean.CoinBaseVO;
import com.utex.mvp.marketdetail.bean.MiTabParamVo;

/**
 * Created by Demon on 2018/6/19.
 */
public interface IMarketDetailPresenter {
    void setTime();

    void setData();

    void calculationKLine(JSONArray kLine);

    void setIndex();

    void getCoinInfo(String coin);

    void getCoinInfoSuccess(CoinBaseVO body);

    void cannelOptional(String coinMarketCode);

    void addOptional(String coinMarketCode, Long id);

    void addOptionalSuccess();

    void cannelOptionalSuccess();

    void calculationKLineUpdate(JSONArray result);

    void clearKLineData();

    void kLineSubscribe(TickerDo currTicker, MiTabParamVo currTime);

    void volSubscribe(TickerDo currTicker);

    void depthSubscribe(String value, TickerDo currTicker);

    void coinSubscribe(TickerDo currTicker);

    void KLineQuery(TickerDo currTicker, MiTabParamVo currTime);

    void volUnSubscribe();

    void depthUnSubscribe();

    void todayUnSubscribe();

    void kLineUnSubscribe();
}
