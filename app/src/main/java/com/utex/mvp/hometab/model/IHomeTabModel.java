package com.utex.mvp.hometab.model;

import com.utex.data.ApiService;
import com.utex.mvp.hometab.presenter.IHomeTabPresenter;

/**
 * Created by Demon on 2018/8/6.
 */
public interface IHomeTabModel {
    void checkAppVersion(ApiService apiService, IHomeTabPresenter iHomeTabPresenter);

    void getExchangeRateList(ApiService apiService, IHomeTabPresenter iHomeTabPresenter);
}
