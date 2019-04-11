package com.utex.mvp.splash.model;

import com.utex.data.ApiService;
import com.utex.mvp.splash.presenter.ISplashPresenter;

/**
 * Created by Demon on 2018/7/23.
 */
public interface ISplashModel {

    void getCoinList(ApiService apiService, final ISplashPresenter iSplashPresenter);

}
