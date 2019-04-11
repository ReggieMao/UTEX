package com.utex.mvp.trad.model;

import com.utex.data.ApiService;
import com.utex.mvp.trad.presenter.ITradPresenter;

/**
 * Created by Demon on 2018/6/4.
 */
public interface ITradModel {
    void getCurrMarketEntrust(ApiService apiService, String coinMarketCode, ITradPresenter iTradPresenter);

    void getAssetList(ApiService apiService, ITradPresenter iTradPresenter);

    void submitDirection0(ApiService apiService, String coin_market_code, String amount, int status, String price, ITradPresenter iTradPresenter);

    void submitDirection1(ApiService apiService, String coin_market_code, String amount, int status, ITradPresenter iTradPresenter);

    void cannelOrder(ApiService apiService, int uid, String market, String user_order_id, ITradPresenter iTradPresenter);

    void addOptional(ApiService apiService, String coinMarketCode, Long id, ITradPresenter iTradPresenter);

    void cannelOptional(ApiService apiService, String coinMarketCode, ITradPresenter iTradPresenter);
}
